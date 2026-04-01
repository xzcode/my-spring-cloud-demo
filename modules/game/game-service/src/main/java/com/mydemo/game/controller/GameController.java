package com.mydemo.game.controller;

import com.mydemo.common.model.PageResult;
import com.mydemo.common.model.Result;
import com.mydemo.game.common.dto.GameDTO;
import com.mydemo.game.service.GameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@Tag(name = "游戏接口")
@RestController
@RequestMapping("/api/game")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    @Operation(summary = "创建游戏")
    @PostMapping
    public Result<GameDTO> create(@RequestBody Map<String, String> body) {
        return Result.ok(gameService.create(body.get("name"), body.get("type"),
                new BigDecimal(body.getOrDefault("minBet", "1")),
                new BigDecimal(body.getOrDefault("maxBet", "1000"))));
    }

    @Operation(summary = "查询游戏")
    @GetMapping("/{gameId}")
    public Result<GameDTO> getById(@PathVariable String gameId) {
        GameDTO dto = gameService.getById(gameId);
        return dto != null ? Result.ok(dto) : Result.fail("游戏不存在");
    }

    @Operation(summary = "游戏列表")
    @GetMapping("/list")
    public Result<PageResult<GameDTO>> list(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return Result.ok(gameService.listGames(page, size));
    }

    @Operation(summary = "健康检查")
    @GetMapping("/health")
    public Result<String> health() {
        return Result.ok("game-service is running");
    }
}
