package com.mydemo.live.consumer.listener;

import com.mydemo.common.kafka.model.KafkaMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class LiveListener {

    @KafkaListener(topics = "live-topic", groupId = "live-consumer-group")
    public void onMessage(KafkaMessage<?> message) {
        log.info("Received message: key={}, timestamp={}", message.getMsgId(), message.getTimestamp());
    }
}
