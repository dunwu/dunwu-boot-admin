package io.github.dunwu.modules.monitor.controller;

import io.github.dunwu.data.core.Result;
import io.github.dunwu.modules.monitor.service.ServerInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Zheng Jie
 * @date 2020-05-02
 */
@RestController
@RequiredArgsConstructor
@Api(tags = "系统-服务监控管理")
@RequestMapping("monitor/server")
public class ServerInfoController {

    private final ServerInfoService serverService;

    @GetMapping
    @ApiOperation("查询服务监控")
    @PreAuthorize("@exp.check('monitor:view')")
    public Result query() {
        return Result.ok(serverService.getServers());
    }

}
