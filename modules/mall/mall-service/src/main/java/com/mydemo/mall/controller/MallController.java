package com.mydemo.mall.controller;

import com.mydemo.common.model.PageResult;
import com.mydemo.common.model.Result;
import com.mydemo.mall.common.dto.ProductDTO;
import com.mydemo.mall.service.MallService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@Tag(name = "商城接口")
@RestController
@RequestMapping("/api/mall")
@RequiredArgsConstructor
public class MallController {

    private final MallService mallService;

    @Operation(summary = "创建商品")
    @PostMapping("/product")
    public Result<ProductDTO> create(@RequestBody Map<String, String> body) {
        return Result.ok(mallService.createProduct(body.get("name"), body.get("description"),
                new BigDecimal(body.getOrDefault("price", "0")),
                Integer.parseInt(body.getOrDefault("stock", "0"))));
    }

    @Operation(summary = "查询商品")
    @GetMapping("/product/{productId}")
    public Result<ProductDTO> getById(@PathVariable String productId) {
        ProductDTO dto = mallService.getById(productId);
        return dto != null ? Result.ok(dto) : Result.fail("商品不存在");
    }

    @Operation(summary = "商品列表")
    @GetMapping("/products")
    public Result<PageResult<ProductDTO>> list(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return Result.ok(mallService.listProducts(page, size));
    }

    @Operation(summary = "健康检查")
    @GetMapping("/health")
    public Result<String> health() {
        return Result.ok("mall-service is running");
    }
}
