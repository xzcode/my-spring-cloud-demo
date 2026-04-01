package com.mydemo.tracking.controller;

import com.mydemo.common.model.Result;
import com.mydemo.tracking.common.dto.TrackingEventDTO;
import com.mydemo.tracking.entity.TrackingEventEntity;
import com.mydemo.tracking.service.TrackingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "埋点接口")
@RestController
@RequestMapping("/api/tracking")
@RequiredArgsConstructor
public class TrackingController {

    private final TrackingService trackingService;

    @Operation(summary = "上报埋点事件（含 Kafka 投递）")
    @PostMapping("/event")
    public Result<Void> track(@RequestBody TrackingEventDTO dto, HttpServletRequest request) {
        trackingService.track(dto, request.getRemoteAddr());
        return Result.ok();
    }

    @Operation(summary = "查询埋点事件列表")
    @GetMapping("/events")
    public Result<List<TrackingEventEntity>> listEvents(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size) {
        return Result.ok(trackingService.listEvents(page, size));
    }

    @Operation(summary = "健康检查")
    @GetMapping("/health")
    public Result<String> health() {
        return Result.ok("tracking-service is running");
    }
}
