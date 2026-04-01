package com.mydemo.wallet.controller;

import com.mydemo.common.model.Result;
import com.mydemo.wallet.common.dto.WalletDTO;
import com.mydemo.wallet.service.WalletService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@Tag(name = "钱包接口")
@RestController
@RequestMapping("/api/wallet")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;

    @Operation(summary = "查询钱包（跨服务调用验证用户）")
    @GetMapping("/{userId}")
    public Result<WalletDTO> getWallet(@PathVariable String userId) {
        return Result.ok(walletService.getOrCreateWallet(userId));
    }

    @Operation(summary = "充值")
    @PostMapping("/recharge")
    public Result<WalletDTO> recharge(@RequestBody Map<String, String> body) {
        return Result.ok(walletService.recharge(body.get("userId"), new BigDecimal(body.get("amount"))));
    }

    @Operation(summary = "健康检查")
    @GetMapping("/health")
    public Result<String> health() {
        return Result.ok("wallet-service is running");
    }
}
