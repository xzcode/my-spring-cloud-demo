package com.mydemo.tracking.service;

import com.mydemo.common.kafka.helper.KafkaProducerHelper;
import com.mydemo.common.web.context.UserContext;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TrackingService {

    private static final Logger log = LoggerFactory.getLogger(TrackingService.class);
    private static final String COLLECTION = "tracking_event";
    private static final String KAFKA_TOPIC = "tracking-events";

    @Resource
    private MongoTemplate mongoTemplate;

    @Resource
    private KafkaProducerHelper kafkaProducerHelper;

    public Map<String, Object> reportEvent(Map<String, Object> params) {
        String userId = UserContext.getCurrentUserId();
        params.put("userId", userId);
        params.put("reportedAt", LocalDateTime.now());
        mongoTemplate.insert(params, COLLECTION);
        kafkaProducerHelper.send(KAFKA_TOPIC, params);
        log.info("埋点事件已上报并发送至Kafka: {}", params.get("eventType"));
        Map<String, Object> response = new HashMap<>();
        response.put("message", "上报成功");
        return response;
    }

    public Map<String, Object> listEvents(int page, int size) {
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
