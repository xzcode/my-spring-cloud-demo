package com.mydemo.chat.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(scanBasePackages = "com.mydemo")
@EnableDiscoveryClient
public class ChatConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChatConsumerApplication.class, args);
    }
}
