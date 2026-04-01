package com.mydemo.common.kafka.helper;

import com.mydemo.common.kafka.model.KafkaMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * Kafka 生产者工具类
 */
@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnBean(KafkaTemplate.class)
public class KafkaProducerHelper {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    /**
     * 发送消息
     */
    public void send(String topic, Object data) {
        send(topic, null, data);
    }

    /**
     * 带 key 发送消息
     */
    public void send(String topic, String key, Object data) {
        kafkaTemplate.send(topic, key, data)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        log.error("Kafka 发送失败, topic={}, key={}", topic, key, ex);
                    } else {
                        log.debug("Kafka 发送成功, topic={}, partition={}, offset={}",
                                topic,
                                result.getRecordMetadata().partition(),
                                result.getRecordMetadata().offset());
                    }
                });
    }

    /**
     * 发送统一消息体
     */
    public <T> void sendMessage(String topic, String type, T data) {
        KafkaMessage<T> message = KafkaMessage.of(type, data);
        send(topic, message.getMsgId(), message);
    }
}
