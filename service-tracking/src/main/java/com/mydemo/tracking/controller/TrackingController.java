package com.mydemo.tracking.controller;

import com.mydemo.tracking.service.TrackingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "数据埋点服务")
@RestController
@RequestMapping("/api/tracking")
public class TrackingController {

    @Resource
    private TrackingService trackingService;

    @Operation(summary = "上报埋点事件")
    @PostMapping("/report")
    public Map<String, Object> reportEvent(@RequestBody Map<String, Object> params) {
        return trackingService.reportEvent(params);
    }

    @Operation(summary = "查询事件列表")
    @GetMapping("/events")
    public Map<String, Object> listEvents(@RequestParam(defaultValue = "1") int page,
                                           @RequestParam(defaultValue = "10") int size) {
        return trackingService.listEvents(page, size);
    }
}
