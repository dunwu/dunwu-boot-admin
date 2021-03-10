package io.github.dunwu.modules.system.controller;

import io.github.dunwu.data.core.Result;
import io.github.dunwu.data.validator.annotation.AddCheck;
import io.github.dunwu.data.validator.annotation.EditCheck;
import io.github.dunwu.modules.monitor.annotation.AppLog;
import io.github.dunwu.modules.system.entity.dto.SysUserDto;
import io.github.dunwu.modules.system.entity.query.SysUserQuery;
import io.github.dunwu.modules.system.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import javax.servlet.http.HttpServletResponse;

/**
 * 系统用户信息 Controller 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-05-25
 */
@RestController
@RequestMapping("sys/user")
@Api(tags = "系统：用户管理")
@RequiredArgsConstructor
public class SysUserController {

    private final SysUserService service;

    @AppLog("添加一条 SysUser 记录")
    @PreAuthorize("@exp.check('user:add')")
    @ApiOperation("添加一条 SysUser 记录")
    @PostMapping("add")
    public Result add(@Validated(AddCheck.class) @RequestBody SysUserDto entity) {
        service.saveUserRelatedRecords(entity);
        return Result.ok();
    }

    @AppLog("更新一条 SysUser 记录")
    @PreAuthorize("@exp.check('user:edit')")
    @ApiOperation("更新一条 SysUser 记录")
    @PostMapping("edit")
    public Result edit(@Validated(EditCheck.class) @RequestBody SysUserDto entity) {
        service.updateUserRelatedRecords(entity);
        return Result.ok();
    }

    @AppLog("根据 ID 删除一条 SysUser 记录")
    @PreAuthorize("@exp.check('user:del')")
    @ApiOperation("删除一条 SysUser 记录")
    @PostMapping("del/{id}")
    public Result deleteById(@PathVariable Serializable id) {
        service.removeById(id);
        return Result.ok();
    }

    @AppLog("根据 ID 集合批量删除 SysUser 记录")
    @PreAuthorize("@exp.check('user:del')")
    @ApiOperation("根据 ID 集合批量删除 SysUser 记录")
    @PostMapping("del/batch")
    public Result deleteByIds(@RequestBody Collection<Serializable> ids) {
        service.removeByIds(ids);
        return Result.ok();
    }

    @PreAuthorize("@exp.check('user:view')")
    @ApiOperation("根据 query 条件，查询匹配条件的 SysUserDto 列表")
    @GetMapping("list")
    public Result list(SysUserQuery query) {
        return Result.ok(service.pojoListByQuery(query));
    }

    @PreAuthorize("@exp.check('user:view')")
    @ApiOperation("根据 query 和 pageable 条件，分页查询 SysUserDto 记录")
    @GetMapping("page")
    public Result page(SysUserQuery query, Pageable pageable) {
        return Result.ok(service.pojoPageByQuery(query, pageable));
    }

    @PreAuthorize("@exp.check('user:view')")
    @ApiOperation("根据 query 条件，查询匹配条件的总记录数")
    @GetMapping("count")
    public Result count(SysUserQuery query) {
        return Result.ok(service.countByQuery(query));
    }

    @PreAuthorize("@exp.check('user:view')")
    @ApiOperation("根据 ID 查询 SysUserDto 记录")
    @GetMapping("{id}")
    public Result getById(@PathVariable Serializable id) {
        return Result.ok(service.pojoById(id));
    }

    @PreAuthorize("@exp.check('user:view')")
    @ApiOperation("根据 query 和 pageable 条件批量导出 SysUserDto 列表数据")
    @GetMapping("export/page")
    public void exportPage(SysUserQuery query, Pageable pageable, HttpServletResponse response) throws IOException {
        service.exportPage(query, pageable, response);
    }

    @PreAuthorize("@exp.check('user:view')")
    @ApiOperation("根据 ID 集合批量导出 SysUserDto 列表数据")
    @PostMapping("export/list")
    public void exportList(@RequestBody Collection<Serializable> ids, HttpServletResponse response)
        throws IOException {
        service.exportList(ids, response);
    }

    // @ApiOperation("修改头像")
    // @PostMapping(value = "/updateAvatar")
    // public ResponseEntity<Object> updateAvatar(@RequestParam MultipartFile avatar) {
    //     return new ResponseEntity<>(userService.updateAvatar(avatar), HttpStatus.OK);
    // }
}
