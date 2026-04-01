package com.mydemo.activity.controller;

import com.mydemo.activity.service.ActivityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "活动服务")
@RestController
@RequestMapping("/api/activity")
public class ActivityController {

    @Resource
    private ActivityService activityService;

    @Operation(summary = "创建活动")
    @PostMapping
    public Map<String, Object> createActivity(@RequestBody Map<String, Object> params) {
        return activityService.createActivity(params);
    }

    @Operation(summary = "获取活动")
    @GetMapping("/{activityId}")
    public Map<String, Object> getActivity(@PathVariable String activityId) {
        return activityService.getActivity(activityId);
    }

    @Operation(summary = "活动列表")
    @GetMapping("/list")
    public Map<String, Object> listActivities(@RequestParam(defaultValue = "1") int page,
                                               @RequestParam(defaultValue = "10") int size) {
        return activityService.listActivities(page, size);
    }
}
