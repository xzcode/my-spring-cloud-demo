package com.mydemo.live.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(scanBasePackages = "com.mydemo")
@EnableDiscoveryClient
public class LiveConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(LiveConsumerApplication.class, args);
    }
}
