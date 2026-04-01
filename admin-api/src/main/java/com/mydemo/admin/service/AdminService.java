package com.mydemo.admin.service;

import jakarta.annotation.Resource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminService {

    @Resource
    private MongoTemplate mongoTemplate;

    public Map<String, Object> getDashboard() {
        long userCount = mongoTemplate.count(new Query(), "user");
        long orderCount = mongoTemplate.count(new Query(), "order");
        long productCount = mongoTemplate.count(new Query(), "product");
        long liveRoomCount = mongoTemplate.count(new Query(), "live_room");
        Map<String, Object> response = new HashMap<>();
        response.put("userCount", userCount);
        response.put("orderCount", orderCount);
        response.put("productCount", productCount);
        response.put("liveRoomCount", liveRoomCount);
        return response;
    }

    public Map<String, Object> listUsers(int page, int size) {
        Query query = new Query();
        query.skip((long) (page - 1) * size).limit(size);
        List<?> list = mongoTemplate.find(query, Map.class, "user");
        long total = mongoTemplate.count(new Query(), "user");
        Map<String, Object> response = new HashMap<>();
        response.put("list", list);
        response.put("total", total);
        response.put("page", page);
        response.put("size", size);
        return response;
    }

    public Map<String, Object> getStats() {
        long activityCount = mongoTemplate.count(new Query(), "activity");
        long gameCount = mongoTemplate.count(new Query(), "game");
        long chatMessageCount = mongoTemplate.count(new Query(), "chat_message");
        long trackingEventCount = mongoTemplate.count(new Query(), "tracking_event");
        long walletCount = mongoTemplate.count(new Query(), "wallet");
        Map<String, Object> response = new HashMap<>();
        response.put("activityCount", activityCount);
        response.put("gameCount", gameCount);
        response.put("chatMessageCount", chatMessageCount);
        response.put("trackingEventCount", trackingEventCount);
        response.put("walletCount", walletCount);
        return response;
    }
}
