package com.mydemo.user.job.task;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserTask {

    /**
     * 示例定时任务：每分钟执行
     */
    @Scheduled(cron = "0 * * * * ?")
    public void execute() {
        log.debug("UserTask executed");
    }
}
