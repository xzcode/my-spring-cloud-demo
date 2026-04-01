package com.mydemo.chat.controller;

import com.mydemo.chat.common.dto.ChatMessageDTO;
import com.mydemo.chat.service.ChatService;
import com.mydemo.common.model.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "聊天接口")
@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @Operation(summary = "发送消息（含 Kafka 投递）")
    @PostMapping("/message")
    public Result<ChatMessageDTO> sendMessage(@RequestBody Map<String, String> body) {
        return Result.ok(chatService.sendMessage(
                body.get("roomId"), body.get("senderId"), body.get("senderName"),
                body.get("content"), body.get("type")));
    }

    @Operation(summary = "获取聊天记录")
    @GetMapping("/messages/{roomId}")
    public Result<List<ChatMessageDTO>> getMessages(
            @PathVariable String roomId,
            @RequestParam(defaultValue = "50") int limit) {
        return Result.ok(chatService.getMessages(roomId, limit));
    }

    @Operation(summary = "健康检查")
    @GetMapping("/health")
    public Result<String> health() {
        return Result.ok("chat-service is running");
    }
}
