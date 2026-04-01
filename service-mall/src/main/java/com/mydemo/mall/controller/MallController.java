package com.mydemo.mall.controller;

import com.mydemo.mall.service.MallService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "商城服务")
@RestController
@RequestMapping("/api/mall")
public class MallController {

    @Resource
    private MallService mallService;

    @Operation(summary = "获取商品")
    @GetMapping("/product/{productId}")
    public Map<String, Object> getProduct(@PathVariable String productId) {
        return mallService.getProduct(productId);
    }

    @Operation(summary = "商品列表")
    @GetMapping("/product/list")
    public Map<String, Object> listProducts(@RequestParam(defaultValue = "1") int page,
                                             @RequestParam(defaultValue = "10") int size) {
        return mallService.listProducts(page, size);
    }

    @Operation(summary = "下单")
    @PostMapping("/order")
    public Map<String, Object> createOrder(@RequestBody Map<String, Object> params) {
        return mallService.createOrder(params);
    }
}
