package com.mydemo.appconfig.service;

import com.mydemo.appconfig.common.dto.AppConfigDTO;
import com.mydemo.appconfig.entity.AppConfigEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AppConfigService {

    private final MongoTemplate mongoTemplate;

    public AppConfigDTO create(String key, String value, String group, String description) {
        AppConfigEntity entity = new AppConfigEntity();
        entity.setConfigKey(key);
        entity.setConfigValue(value);
        entity.setConfigGroup(group);
        entity.setDescription(description);
        entity.setStatus(0);
        entity.setCreateTime(LocalDateTime.now());
        entity.setUpdateTime(LocalDateTime.now());
        mongoTemplate.save(entity);
        return toDTO(entity);
    }

    public AppConfigDTO getByKey(String key) {
        AppConfigEntity entity = mongoTemplate.findOne(
                Query.query(Criteria.where("configKey").is(key).and("status").is(0)),
                AppConfigEntity.class);
        return entity != null ? toDTO(entity) : null;
    }

    public List<AppConfigDTO> getByGroup(String group) {
        return mongoTemplate.find(
                        Query.query(Criteria.where("configGroup").is(group).and("status").is(0)),
                        AppConfigEntity.class)
                .stream().map(this::toDTO).toList();
    }

    private AppConfigDTO toDTO(AppConfigEntity entity) {
        AppConfigDTO dto = new AppConfigDTO();
        dto.setConfigId(entity.getId());
        dto.setConfigKey(entity.getConfigKey());
        dto.setConfigValue(entity.getConfigValue());
        dto.setDescription(entity.getDescription());
        dto.setConfigGroup(entity.getConfigGroup());
        dto.setStatus(entity.getStatus());
        return dto;
    }
}
