package com.mydemo.activity.controller;

import com.mydemo.activity.common.dto.ActivityDTO;
import com.mydemo.activity.service.ActivityService;
import com.mydemo.common.model.PageResult;
import com.mydemo.common.model.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "活动接口")
@RestController
@RequestMapping("/api/activity")
@RequiredArgsConstructor
public class ActivityController {

    private final ActivityService activityService;

    @Operation(summary = "创建活动")
    @PostMapping
    public Result<ActivityDTO> create(@RequestBody Map<String, String> body) {
        return Result.ok(activityService.create(body.get("title"), body.get("type"), body.get("description")));
    }

    @Operation(summary = "查询活动详情")
    @GetMapping("/{activityId}")
    public Result<ActivityDTO> getById(@PathVariable String activityId) {
        ActivityDTO dto = activityService.getById(activityId);
        return dto != null ? Result.ok(dto) : Result.fail("活动不存在");
    }

    @Operation(summary = "活动列表")
    @GetMapping("/list")
    public Result<PageResult<ActivityDTO>> list(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return Result.ok(activityService.listActivities(page, size));
    }

    @Operation(summary = "健康检查")
    @GetMapping("/health")
    public Result<String> health() {
        return Result.ok("activity-service is running");
    }
}
