package com.mydemo.job.service;

import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JobService {

    private static final Logger log = LoggerFactory.getLogger(JobService.class);
    private static final String COLLECTION = "job_task";

    @Resource
    private MongoTemplate mongoTemplate;

    public Map<String, Object> listJobs(int page, int size) {
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

    public Map<String, Object> triggerJob(String jobId) {
        Query query = new Query(Criteria.where("_id").is(jobId));
        Update update = new Update()
                .set("lastTriggeredAt", LocalDateTime.now())
                .set("status", "running");
        mongoTemplate.updateFirst(query, update, COLLECTION);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "任务已触发");
        response.put("jobId", jobId);
        return response;
    }

    @Scheduled(cron = "0 0/5 * * * ?")
    public void scheduledTask() {
        log.info("定时任务执行: {}", LocalDateTime.now());
        Query query = new Query(Criteria.where("status").is("pending"));
        List<?> pendingJobs = mongoTemplate.find(query, Map.class, COLLECTION);
        for (Object job : pendingJobs) {
            log.info("处理任务: {}", job);
        }
    }
}
