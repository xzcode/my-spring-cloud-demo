package com.mydemo.user.service;

import com.mydemo.common.model.ErrorCode;
import com.mydemo.common.model.exception.BizException;
import com.mydemo.common.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final MongoTemplate mongoTemplate;

    private static final String COLLECTION = "user";

    public Map<String, Object> getUserById(String userId) {
        Query query = new Query(Criteria.where("_id").is(userId));
        Map result = mongoTemplate.findOne(query, Map.class, COLLECTION);
        if (result == null) {
            throw new BizException(ErrorCode.USER_NOT_FOUND);
        }
        result.remove("password");
        return result;
    }

    public Map<String, Object> findByUsername(String username) {
        Query query = new Query(Criteria.where("username").is(username));
        return mongoTemplate.findOne(query, Map.class, COLLECTION);
    }

    public Map<String, Object> createUser(String username, String password, String nickname) {
        // 检查用户名是否已存在
        if (findByUsername(username) != null) {
            throw new BizException(ErrorCode.USER_ALREADY_EXISTS);
        }

        Map<String, Object> user = new HashMap<>();
        user.put("_id", IdGenerator.nextIdStr());
        user.put("username", username);
        user.put("password", password);
        user.put("nickname", nickname != null ? nickname : username);
        user.put("status", 1);
        user.put("createTime", LocalDateTime.now());
        user.put("updateTime", LocalDateTime.now());

        mongoTemplate.insert(user, COLLECTION);
        user.remove("password");
        return user;
    }
}
