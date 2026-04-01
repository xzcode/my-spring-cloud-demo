package com.mydemo.appconfig.service;

import jakarta.annotation.Resource;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AppConfigService {

    private static final String COLLECTION = "app_config";

    @Resource
    private MongoTemplate mongoTemplate;

    public Map<String, Object> getLatestConfig(String platform) {
        Query query = new Query();
        if (platform != null && !platform.isEmpty()) {
            query.addCriteria(Criteria.where("platform").is(platform));
        }
        query.with(Sort.by(Sort.Direction.DESC, "createdAt")).limit(1);
        Map result = mongoTemplate.findOne(query, Map.class, COLLECTION);
        Map<String, Object> response = new HashMap<>();
        response.put("data", result);
        return response;
    }

    public Map<String, Object> checkVersion(String currentVersion, String platform) {
        Query query = new Query();
        if (platform != null && !platform.isEmpty()) {
            query.addCriteria(Criteria.where("platform").is(platform));
        }
        query.with(Sort.by(Sort.Direction.DESC, "createdAt")).limit(1);
        Map latestConfig = mongoTemplate.findOne(query, Map.class, COLLECTION);
        Map<String, Object> response = new HashMap<>();
        if (latestConfig != null) {
            String latestVersion = (String) latestConfig.get("version");
            boolean needUpdate = latestVersion != null && !latestVersion.equals(currentVersion);
            response.put("needUpdate", needUpdate);
            response.put("latestVersion", latestVersion);
            response.put("downloadUrl", latestConfig.get("downloadUrl"));
            response.put("forceUpdate", latestConfig.getOrDefault("forceUpdate", false));
            response.put("updateLog", latestConfig.get("updateLog"));
        } else {
            response.put("needUpdate", false);
        }
        return response;
    }
}
