package com.mydemo.activity.service;

import com.mydemo.activity.common.dto.ActivityDTO;
import com.mydemo.activity.entity.ActivityEntity;
import com.mydemo.common.model.PageResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ActivityService {

    private final MongoTemplate mongoTemplate;

    public ActivityDTO create(String title, String type, String description) {
        ActivityEntity entity = new ActivityEntity();
        entity.setTitle(title);
        entity.setType(type);
        entity.setDescription(description);
        entity.setStatus("DRAFT");
        entity.setCreateTime(LocalDateTime.now());
        entity.setUpdateTime(LocalDateTime.now());
        mongoTemplate.save(entity);
        return toDTO(entity);
    }

    public ActivityDTO getById(String activityId) {
        ActivityEntity entity = mongoTemplate.findById(activityId, ActivityEntity.class);
        return entity != null ? toDTO(entity) : null;
    }

    public PageResult<ActivityDTO> listActivities(int page, int size) {
        Query query = new Query().with(Sort.by(Sort.Direction.DESC, "createTime"));
        long total = mongoTemplate.count(query, ActivityEntity.class);
        query.skip((long) (page - 1) * size).limit(size);
        List<ActivityDTO> list = mongoTemplate.find(query, ActivityEntity.class)
                .stream().map(this::toDTO).toList();
        return PageResult.of(list, total, page, size);
    }

    private ActivityDTO toDTO(ActivityEntity entity) {
        ActivityDTO dto = new ActivityDTO();
        dto.setActivityId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setType(entity.getType());
        dto.setDescription(entity.getDescription());
        dto.setStatus(entity.getStatus());
        dto.setStartTime(entity.getStartTime());
        dto.setEndTime(entity.getEndTime());
        return dto;
    }
}
