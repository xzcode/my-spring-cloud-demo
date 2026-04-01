package com.mydemo.tracking.service;

import com.mydemo.common.kafka.helper.KafkaProducerHelper;
import com.mydemo.common.kafka.model.KafkaMessage;
import com.mydemo.common.kafka.constant.KafkaTopicConstants;
import com.mydemo.tracking.common.dto.TrackingEventDTO;
import com.mydemo.tracking.entity.TrackingEventEntity;
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
public class TrackingService {

    private final MongoTemplate mongoTemplate;
    private final KafkaProducerHelper kafkaProducerHelper;

    public void track(TrackingEventDTO dto, String ip) {
        TrackingEventEntity entity = new TrackingEventEntity();
        entity.setEventType(dto.getEventType());
        entity.setUserId(dto.getUserId());
        entity.setDeviceId(dto.getDeviceId());
        entity.setPlatform(dto.getPlatform());
        entity.setIp(ip);
        entity.setData(dto.getData());
        entity.setCreateTime(LocalDateTime.now());
        entity.setUpdateTime(LocalDateTime.now());
        mongoTemplate.save(entity);

        // 发送 Kafka 用于异步分析
        kafkaProducerHelper.send(KafkaTopicConstants.TOPIC_TRACKING, dto.getEventType(),
                KafkaMessage.of("TRACKING", dto));
    }

    public List<TrackingEventEntity> listEvents(int page, int size) {
        Query query = new Query().with(Sort.by(Sort.Direction.DESC, "createTime"))
                .skip((long) (page - 1) * size).limit(size);
        return mongoTemplate.find(query, TrackingEventEntity.class);
    }
}
