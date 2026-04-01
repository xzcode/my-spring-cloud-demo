package com.mydemo.live.service;

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
public class LiveService {

    private static final String COLLECTION = "live_room";

    @Resource
    private MongoTemplate mongoTemplate;

    public Map<String, Object> createRoom(Map<String, Object> params) {
        params.put("createdAt", LocalDateTime.now());
        params.put("status", "waiting");
        mongoTemplate.insert(params, COLLECTION);
        return params;
    }

    public Map<String, Object> getRoom(String roomId) {
        Query query = new Query(Criteria.where("_id").is(roomId));
        Map result = mongoTemplate.findOne(query, Map.class, COLLECTION);
        Map<String, Object> response = new HashMap<>();
        response.put("data", result);
        return response;
    }

    public Map<String, Object> listRooms(int page, int size) {
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
}
