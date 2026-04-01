package com.mydemo.mall.service;

import com.mydemo.common.web.context.UserContext;
import jakarta.annotation.Resource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MallService {

    private static final String COLLECTION = "product";
    private static final String ORDER_COLLECTION = "order";

    @Resource
    private MongoTemplate mongoTemplate;

    public Map<String, Object> getProduct(String productId) {
        Query query = new Query(Criteria.where("_id").is(productId));
        Map result = mongoTemplate.findOne(query, Map.class, COLLECTION);
        Map<String, Object> response = new HashMap<>();
        response.put("data", result);
        return response;
    }

    public Map<String, Object> listProducts(int page, int size) {
        Query query = new Query();
        query.skip((long) (page - 1) * size).limit(size);
        List<?> list = mongoTemplate.find(query, Map.class, COLLECTION);
        long total = mongoTemplate.count(new Query(), COLLECTION);
        Map<String, Object> response = new HashMap<>();
        response.put("list", list);
        response.put("total", total);
        response.put("page", page);
        response.put("size", size);
        return response;
    }

    public Map<String, Object> createOrder(Map<String, Object> params) {
        String userId = UserContext.getCurrentUserId();
        params.put("userId", userId);
        params.put("createdAt", LocalDateTime.now());
        params.put("status", "pending");
        mongoTemplate.insert(params, ORDER_COLLECTION);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "下单成功");
        response.put("data", params);
        return response;
    }
}
