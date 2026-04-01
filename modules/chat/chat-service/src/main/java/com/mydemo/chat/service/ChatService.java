package com.mydemo.chat.service;

import com.mydemo.chat.common.dto.ChatMessageDTO;
import com.mydemo.chat.entity.ChatMessageEntity;
import com.mydemo.common.kafka.helper.KafkaProducerHelper;
import com.mydemo.common.kafka.model.KafkaMessage;
import com.mydemo.common.kafka.constant.KafkaTopicConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {

    private final MongoTemplate mongoTemplate;
    private final KafkaProducerHelper kafkaProducerHelper;

    public ChatMessageDTO sendMessage(String roomId, String senderId, String senderName, String content, String type) {
        ChatMessageEntity entity = new ChatMessageEntity();
        entity.setRoomId(roomId);
        entity.setSenderId(senderId);
        entity.setSenderName(senderName);
        entity.setContent(content);
        entity.setType(type != null ? type : "TEXT");
        entity.setStatus("NORMAL");
        entity.setCreateTime(LocalDateTime.now());
        entity.setUpdateTime(LocalDateTime.now());
        mongoTemplate.save(entity);

        // 发送 Kafka 消息用于异步处理
        KafkaMessage<ChatMessageDTO> kafkaMsg = KafkaMessage.of("CHAT_MESSAGE", toDTO(entity));
        kafkaProducerHelper.send(KafkaTopicConstants.TOPIC_CHAT_MESSAGE, entity.getRoomId(), kafkaMsg);

        return toDTO(entity);
    }

    public List<ChatMessageDTO> getMessages(String roomId, int limit) {
        Query query = Query.query(Criteria.where("roomId").is(roomId).and("status").is("NORMAL"))
                .with(Sort.by(Sort.Direction.DESC, "createTime"))
                .limit(limit);
        return mongoTemplate.find(query, ChatMessageEntity.class)
                .stream().map(this::toDTO).toList();
    }

    private ChatMessageDTO toDTO(ChatMessageEntity entity) {
        ChatMessageDTO dto = new ChatMessageDTO();
        dto.setMessageId(entity.getId());
        dto.setRoomId(entity.getRoomId());
        dto.setSenderId(entity.getSenderId());
        dto.setSenderName(entity.getSenderName());
        dto.setContent(entity.getContent());
        dto.setType(entity.getType());
        dto.setCreateTime(entity.getCreateTime());
        return dto;
    }
}
