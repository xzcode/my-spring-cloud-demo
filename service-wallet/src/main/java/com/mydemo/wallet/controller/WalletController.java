package com.mydemo.wallet.controller;

import com.mydemo.wallet.service.WalletService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "钱包服务")
@RestController
@RequestMapping("/api/wallet")
public class WalletController {

    @Resource
    private WalletService walletService;

    @Operation(summary = "查询余额")
    @GetMapping("/balance")
    public Map<String, Object> getBalance() {
        return walletService.getBalance();
    }

    @Operation(summary = "充值")
    @PostMapping("/recharge")
    public Map<String, Object> recharge(@RequestBody Map<String, Object> params) {
        return walletService.recharge(params);
    }

    @Operation(summary = "提现")
    @PostMapping("/withdraw")
    public Map<String, Object> withdraw(@RequestBody Map<String, Object> params) {
        return walletService.withdraw(params);
    }
}
