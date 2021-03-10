package io.github.dunwu.modules.security.rest;

import io.github.dunwu.data.core.Result;
import io.github.dunwu.modules.security.service.AuthService;
import io.github.dunwu.util.EncryptUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Set;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Zheng Jie
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("auth/online")
@Api(tags = "系统：在线用户管理")
public class OnlineController {

    private final AuthService authService;

    @ApiOperation("查询在线用户")
    @PreAuthorize("@exp.check()")
    @GetMapping("page")
    public Result query(String filter, Pageable pageable) {
        return Result.ok(authService.getAll(filter, pageable));
    }

    @ApiOperation("导出数据")
    @PreAuthorize("@exp.check()")
    @GetMapping("export/page")
    public void download(Pageable pageable, HttpServletResponse response, String filter) throws IOException {
        authService.download(authService.getAll(filter), response);
    }

    @ApiOperation("踢出用户")
    @PreAuthorize("@exp.check()")
    @PostMapping("del/batch")
    public Result delete(@RequestBody Set<String> keys) throws Exception {
        for (String key : keys) {
            // 解密Key
            key = EncryptUtils.desDecrypt(key);
            authService.kickOut(key);
        }
        return Result.ok();
    }

}
