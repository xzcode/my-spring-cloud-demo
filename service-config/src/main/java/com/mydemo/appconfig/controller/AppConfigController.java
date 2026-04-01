package com.mydemo.appconfig.controller;

import com.mydemo.appconfig.service.AppConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "应用配置服务")
@RestController
@RequestMapping("/api/config")
public class AppConfigController {

    @Resource
    private AppConfigService appConfigService;

    @Operation(summary = "获取最新App配置")
    @GetMapping("/app/latest")
    public Map<String, Object> getLatestConfig(@RequestParam(required = false) String platform) {
        return appConfigService.getLatestConfig(platform);
    }

    @Operation(summary = "版本更新检查")
    @GetMapping("/app/version/check")
    public Map<String, Object> checkVersion(@RequestParam String currentVersion,
                                             @RequestParam(required = false) String platform) {
        return appConfigService.checkVersion(currentVersion, platform);
    }
}
