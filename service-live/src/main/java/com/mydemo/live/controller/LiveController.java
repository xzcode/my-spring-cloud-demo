package com.mydemo.live.controller;

import com.mydemo.live.service.LiveService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "直播服务")
@RestController
@RequestMapping("/api/live")
public class LiveController {

    @Resource
    private LiveService liveService;

    @Operation(summary = "创建直播间")
    @PostMapping("/room")
    public Map<String, Object> createRoom(@RequestBody Map<String, Object> params) {
        return liveService.createRoom(params);
    }

    @Operation(summary = "获取直播间")
    @GetMapping("/room/{roomId}")
    public Map<String, Object> getRoom(@PathVariable String roomId) {
        return liveService.getRoom(roomId);
    }

    @Operation(summary = "直播间列表")
    @GetMapping("/room/list")
    public Map<String, Object> listRooms(@RequestParam(defaultValue = "1") int page,
                                         @RequestParam(defaultValue = "10") int size) {
        return liveService.listRooms(page, size);
    }
}
