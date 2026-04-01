package com.mydemo.common.kafka.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Kafka 通用消息体
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KafkaMessage<T> implements Serializable {

    /** 消息ID */
    @Builder.Default
    private String msgId = UUID.randomUUID().toString();

    /** 消息类型 */
    private String type;

    /** 消息时间 */
    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();

    /** 消息体 */
    private T data;

    public static <T> KafkaMessage<T> of(String type, T data) {
        return KafkaMessage.<T>builder()
                .type(type)
                .data(data)
                .build();
    }
}
