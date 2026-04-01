package com.mydemo.appconfig.controller;

import com.mydemo.appconfig.common.dto.AppConfigDTO;
import com.mydemo.appconfig.service.AppConfigService;
import com.mydemo.common.model.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "应用配置接口")
@RestController
@RequestMapping("/api/config")
@RequiredArgsConstructor
public class AppConfigController {

    private final AppConfigService appConfigService;

    @Operation(summary = "创建配置")
    @PostMapping("/app")
    public Result<AppConfigDTO> create(@RequestBody Map<String, String> body) {
        return Result.ok(appConfigService.create(
                body.get("key"), body.get("value"), body.get("group"), body.get("description")));
    }

    @Operation(summary = "根据key获取配置")
    @GetMapping("/app/{key}")
    public Result<AppConfigDTO> getByKey(@PathVariable String key) {
        AppConfigDTO dto = appConfigService.getByKey(key);
        return dto != null ? Result.ok(dto) : Result.fail("配置不存在");
    }

    @Operation(summary = "根据group获取配置列表")
    @GetMapping("/app/group/{group}")
    public Result<List<AppConfigDTO>> getByGroup(@PathVariable String group) {
        return Result.ok(appConfigService.getByGroup(group));
    }

    @Operation(summary = "健康检查")
    @GetMapping("/health")
    public Result<String> health() {
        return Result.ok("appconfig-service is running");
    }
}
