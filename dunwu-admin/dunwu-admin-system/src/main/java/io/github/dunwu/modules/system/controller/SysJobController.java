package io.github.dunwu.modules.system.controller;

import io.github.dunwu.data.core.Result;
import io.github.dunwu.data.validator.annotation.AddCheck;
import io.github.dunwu.data.validator.annotation.EditCheck;
import io.github.dunwu.modules.monitor.annotation.AppLog;
import io.github.dunwu.modules.system.entity.dto.SysJobDto;
import io.github.dunwu.modules.system.entity.query.SysJobQuery;
import io.github.dunwu.modules.system.service.SysJobService;
import io.github.dunwu.modules.system.service.SysRoleService;
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
 * 系统岗位信息 Controller 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-05-24
 */
@RestController
@RequestMapping("sys/job")
@Api(tags = "系统：岗位管理")
@RequiredArgsConstructor
public class SysJobController {

    private final SysJobService service;
    private final SysRoleService roleService;

    @AppLog("添加一条 SysJobDto 记录")
    @PreAuthorize("@exp.check('job:add')")
    @ApiOperation("添加一条 SysJobDto 记录")
    @PostMapping("add")
    public Result add(@Validated(AddCheck.class) @RequestBody SysJobDto entity) {
        service.save(entity);
        return Result.ok();
    }

    @AppLog("更新一条 SysJobDto 记录")
    @PreAuthorize("@exp.check('job:edit')")
    @ApiOperation("更新一条 SysJobDto 记录")
    @PostMapping("edit")
    public Result edit(@Validated(EditCheck.class) @RequestBody SysJobDto entity) {
        service.updateById(entity);
        return Result.ok();
    }

    @AppLog("根据 ID 删除一条 SysJobDto 记录")
    @PreAuthorize("@exp.check('job:del')")
    @ApiOperation("根据 ID 删除一条 SysJobDto 记录")
    @PostMapping("del/{id}")
    public Result deleteById(@PathVariable Serializable id) {
        service.removeById(id);
        return Result.ok();
    }

    @AppLog("根据 ID 集合批量删除 SysJobDto 记录")
    @PreAuthorize("@exp.check('job:del')")
    @ApiOperation("根据 ID 集合批量删除 SysJobDto 记录")
    @PostMapping("del/batch")
    public Result deleteByIds(@RequestBody Collection<Serializable> ids) {
        service.removeByIds(ids);
        return Result.ok();
    }

    @PreAuthorize("@exp.check('job:view')")
    @ApiOperation("根据 query 条件，查询匹配条件的 SysJobDto 列表")
    @GetMapping("list")
    public Result list(SysJobQuery query) {
        return Result.ok(service.pojoListByQuery(query));
    }

    @PreAuthorize("@exp.check('job:view')")
    @ApiOperation("根据 query 和 pageable 条件，分页查询 SysJobDto 记录")
    @GetMapping("page")
    public Result page(SysJobQuery query, Pageable pageable) {
        return Result.ok(service.pojoPageByQuery(query, pageable));
    }

    @PreAuthorize("@exp.check('job:view')")
    @ApiOperation("根据 query 条件，查询匹配条件的总记录数")
    @GetMapping("count")
    public Result count(SysJobQuery query) {
        return Result.ok(service.countByQuery(query));
    }

    @PreAuthorize("@exp.check('job:view')")
    @ApiOperation("根据 ID 查询 SysJobDto 记录")
    @GetMapping("{id}")
    public Result getById(@PathVariable Serializable id) {
        return Result.ok(service.pojoById(id));
    }

    @PreAuthorize("@exp.check('job:view')")
    @ApiOperation("根据 query 和 pageable 条件批量导出 SysJobDto 列表数据")
    @GetMapping("export/page")
    public void exportPage(SysJobQuery query, Pageable pageable, HttpServletResponse response) throws IOException {
        service.exportPage(query, pageable, response);
    }

    @PreAuthorize("@exp.check('job:view')")
    @ApiOperation("根据 ID 集合批量导出 SysJobDto 列表数据")
    @PostMapping("export/list")
    public void exportList(@RequestBody Collection<Serializable> ids, HttpServletResponse response) throws IOException {
        service.exportList(ids, response);
    }

    @GetMapping("roles/{jobId}")
    @PreAuthorize("@exp.check('job:view')")
    @ApiOperation("根据 Job ID 查询角色列表")
    public Result rolesByJobId(@PathVariable Long jobId) {
        return Result.ok(roleService.pojoListByJobId(jobId));
    }

    @PutMapping("roles/{jobId}")
    @AppLog("更新岗位/角色记录")
    @PreAuthorize("@exp.check('job:edit')")
    @ApiOperation("更新一条 SysJobDto 记录")
    public Result updateRolesByJobId(@PathVariable Long jobId, @RequestBody Collection<Long> roleIds) {
        roleService.updateRolesByJobId(jobId, roleIds);
        return Result.ok();
    }

}
