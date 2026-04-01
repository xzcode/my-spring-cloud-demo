package com.mydemo.chat.controller;

import com.mydemo.chat.service.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "聊天服务")
@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @Resource
    private ChatService chatService;

    @Operation(summary = "发送消息")
    @PostMapping("/send")
    public Map<String, Object> sendMessage(@RequestBody Map<String, Object> params) {
        return chatService.sendMessage(params);
    }

    @Operation(summary = "聊天记录")
    @GetMapping("/history")
    public Map<String, Object> getHistory(@RequestParam String targetUserId,
                                           @RequestParam(defaultValue = "1") int page,
                                           @RequestParam(defaultValue = "20") int size) {
        return chatService.getHistory(targetUserId, page, size);
    }
}
