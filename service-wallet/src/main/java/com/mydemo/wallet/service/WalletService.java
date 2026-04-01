package com.mydemo.wallet.service;

import com.mydemo.common.web.context.UserContext;
import jakarta.annotation.Resource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class WalletService {

    private static final String COLLECTION = "wallet";

    @Resource
    private MongoTemplate mongoTemplate;

    public Map<String, Object> getBalance() {
        String userId = UserContext.getCurrentUserId();
        Query query = new Query(Criteria.where("userId").is(userId));
        Map result = mongoTemplate.findOne(query, Map.class, COLLECTION);
        Map<String, Object> response = new HashMap<>();
        response.put("data", result);
        return response;
    }

    public Map<String, Object> recharge(Map<String, Object> params) {
        String userId = UserContext.getCurrentUserId();
        BigDecimal amount = new BigDecimal(params.get("amount").toString());
        Query query = new Query(Criteria.where("userId").is(userId));
        Update update = new Update().inc("balance", amount.doubleValue())
                .set("updatedAt", LocalDateTime.now());
        mongoTemplate.upsert(query, update, COLLECTION);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "充值成功");
        response.put("amount", amount);
        return response;
    }

    public Map<String, Object> withdraw(Map<String, Object> params) {
        String userId = UserContext.getCurrentUserId();
        BigDecimal amount = new BigDecimal(params.get("amount").toString());
        Query query = new Query(Criteria.where("userId").is(userId));
        Update update = new Update().inc("balance", -amount.doubleValue())
                .set("updatedAt", LocalDateTime.now());
        mongoTemplate.updateFirst(query, update, COLLECTION);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "提现成功");
        response.put("amount", amount);
        return response;
    }
}
