package com.mydemo.live.controller;

import com.mydemo.common.model.PageResult;
import com.mydemo.common.model.Result;
import com.mydemo.live.common.dto.LiveRoomDTO;
import com.mydemo.live.service.LiveService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "直播接口")
@RestController
@RequestMapping("/api/live")
@RequiredArgsConstructor
public class LiveController {

    private final LiveService liveService;

    @Operation(summary = "创建直播间")
    @PostMapping("/room")
    public Result<LiveRoomDTO> createRoom(@RequestBody Map<String, String> body) {
        return Result.ok(liveService.createRoom(body.get("title"), body.get("hostId")));
    }

    @Operation(summary = "查询直播间详情（含跨服务调用获取主播信息）")
    @GetMapping("/room/{roomId}")
    public Result<LiveRoomDTO> getRoom(@PathVariable String roomId) {
        LiveRoomDTO dto = liveService.getRoomById(roomId);
        return dto != null ? Result.ok(dto) : Result.fail("直播间不存在");
    }

    @Operation(summary = "直播间列表")
    @GetMapping("/rooms")
    public Result<PageResult<LiveRoomDTO>> listRooms(
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return Result.ok(liveService.listRooms(status, page, size));
    }

    @Operation(summary = "健康检查")
    @GetMapping("/health")
    public Result<String> health() {
        return Result.ok("live-service is running");
    }
}
