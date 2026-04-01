package com.mydemo.job.controller;

import com.mydemo.job.service.JobService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "Job服务")
@RestController
@RequestMapping("/api/job")
public class JobController {

    @Resource
    private JobService jobService;

    @Operation(summary = "任务列表")
    @GetMapping("/list")
    public Map<String, Object> listJobs(@RequestParam(defaultValue = "1") int page,
                                         @RequestParam(defaultValue = "10") int size) {
        return jobService.listJobs(page, size);
    }

    @Operation(summary = "手动触发任务")
    @PostMapping("/trigger/{jobId}")
    public Map<String, Object> triggerJob(@PathVariable String jobId) {
        return jobService.triggerJob(jobId);
    }
}
