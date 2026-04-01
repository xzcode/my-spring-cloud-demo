package com.mydemo.chat.service;

import com.mydemo.common.web.context.UserContext;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChatService {

    private static final String COLLECTION = "chat_message";

    @Resource
    private MongoTemplate mongoTemplate;

    public Map<String, Object> sendMessage(Map<String, Object> params) {
        String userId = UserContext.getCurrentUserId();
        params.put("senderId", userId);
        params.put("createdAt", LocalDateTime.now());
        params.put("status", "sent");
        mongoTemplate.insert(params, COLLECTION);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "发送成功");
        response.put("data", params);
        return response;
    }

    public Map<String, Object> getHistory(String targetUserId, int page, int size) {
        String userId = UserContext.getCurrentUserId();
        Criteria criteria = new Criteria().orOperator(
                Criteria.where("senderId").is(userId).and("receiverId").is(targetUserId),
                Criteria.where("senderId").is(targetUserId).and("receiverId").is(userId)
        );
        Query query = new Query(criteria)
                .with(Sort.by(Sort.Direction.DESC, "createdAt"))
                .skip((long) (page - 1) * size)
                .limit(size);
        List<?> list = mongoTemplate.find(query, Map.class, COLLECTION);
        long total = mongoTemplate.count(new Query(criteria), COLLECTION);
        Map<String, Object> response = new HashMap<>();
        response.put("list", list);
        response.put("total", total);
        response.put("page", page);
        response.put("size", size);
        return response;
    }
}
