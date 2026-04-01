package com.mydemo.integration.consumer.listener;

import com.mydemo.common.kafka.model.KafkaMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class IntegrationListener {

    @KafkaListener(topics = "topic-integration", groupId = "integration-consumer-group")
    public void onIntegrationEvent(KafkaMessage<?> message) {
        log.info("收到通知事件: type={}, msgId={}", message.getType(), message.getMsgId());
        // TODO: 根据消息类型发送短信/邮件/推送等
    }
}
