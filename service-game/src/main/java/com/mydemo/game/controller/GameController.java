package com.mydemo.game.controller;

import com.mydemo.game.service.GameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "游戏服务")
@RestController
@RequestMapping("/api/game")
public class GameController {

    @Resource
    private GameService gameService;

    @Operation(summary = "创建游戏")
    @PostMapping
    public Map<String, Object> createGame(@RequestBody Map<String, Object> params) {
        return gameService.createGame(params);
    }

    @Operation(summary = "获取游戏")
    @GetMapping("/{gameId}")
    public Map<String, Object> getGame(@PathVariable String gameId) {
        return gameService.getGame(gameId);
    }

    @Operation(summary = "游戏列表")
    @GetMapping("/list")
    public Map<String, Object> listGames(@RequestParam(defaultValue = "1") int page,
                                          @RequestParam(defaultValue = "10") int size) {
        return gameService.listGames(page, size);
    }
}
