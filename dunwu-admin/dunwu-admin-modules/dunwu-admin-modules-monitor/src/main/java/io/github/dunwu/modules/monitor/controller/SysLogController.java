package io.github.dunwu.modules.monitor.controller;

import io.github.dunwu.data.core.Result;
import io.github.dunwu.data.validator.annotation.AddCheck;
import io.github.dunwu.data.validator.annotation.EditCheck;
import io.github.dunwu.modules.monitor.annotation.AppLog;
import io.github.dunwu.modules.monitor.entity.SysLog;
import io.github.dunwu.modules.monitor.entity.dto.SysLogDto;
import io.github.dunwu.modules.monitor.entity.query.SysLogQuery;
import io.github.dunwu.modules.monitor.service.SysLogService;
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
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;

/**
 * 系统日志 Controller 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-03-10
 */
@RestController
@RequestMapping("/monitor/log")
@Api(tags = "系统：日志管理")
@RequiredArgsConstructor
public class SysLogController {

    private final SysLogService service;

    @PreAuthorize("@exp.check()")
    @ApiOperation("添加一条 SysLog 记录")
    @PostMapping("add")
    public Result add(@Validated(AddCheck.class) @RequestBody SysLog entity) {
        service.save(entity);
        return Result.ok();
    }

    @PreAuthorize("@exp.check()")
    @ApiOperation("批量添加 SysLog 记录")
    @PostMapping("add/batch")
    public Result addBatch(@Validated(AddCheck.class) @RequestBody Collection<SysLog> list) {
        service.saveBatch(list);
        return Result.ok();
    }

    @PreAuthorize("@exp.check()")
    @ApiOperation("根据 ID 更新一条 SysLog 记录")
    @PostMapping("edit")
    public Result edit(@Validated(EditCheck.class) @RequestBody SysLog entity) {
        service.updateById(entity);
        return Result.ok();
    }

    @PreAuthorize("@exp.check()")
    @ApiOperation("根据 ID 批量更新 SysLog 记录")
    @PostMapping("edit/batch")
    public Result editBatch(@Validated(EditCheck.class) @RequestBody Collection<SysLog> list) {
        service.updateBatchById(list);
        return Result.ok();
    }

    @PreAuthorize("@exp.check()")
    @ApiOperation("根据 ID 删除一条 SysLog 记录")
    @PostMapping("del/{id}")
    public Result deleteById(@PathVariable Serializable id) {
        service.removeById(id);
        return Result.ok();
    }

    @AppLog("批量删除数据")
    @PreAuthorize("@exp.check()")
    @ApiOperation("根据 ID 列表批量删除 SysLog 记录")
    @PostMapping("del/batch")
    public Result deleteByIds(@RequestBody Collection<Serializable> ids) {
        service.removeByIds(ids);
        return Result.ok();
    }

    @PreAuthorize("@exp.check()")
    @ApiOperation("根据 SysLogQuery 查询 SysLogDto 列表")
    @GetMapping("list")
    public Result list(SysLogQuery query) {
        return Result.ok(service.pojoListByQuery(query));
    }

    @PreAuthorize("@exp.check()")
    @ApiOperation("根据 SysLogQuery 和 Pageable 分页查询 SysLogDto 列表")
    @GetMapping("page")
    public Result page(SysLogQuery query, Pageable pageable) {
        return Result.ok(service.pojoPageByQuery(query, pageable));
    }

    @PreAuthorize("@exp.check()")
    @GetMapping("{id}")
    @ApiOperation("根据 id 查询 SysLogDto")
    public Result getById(@PathVariable Serializable id) {
        return Result.ok(service.pojoById(id));
    }

    @PreAuthorize("@exp.check()")
    @ApiOperation("根据 SysLogQuery 查询匹配条件的记录数")
    @GetMapping("count")
    public Result count(SysLogQuery query) {
        return Result.ok(service.countByQuery(query));
    }

    @PreAuthorize("@exp.check()")
    @ApiOperation("根据 id 列表查询 SysLogDto 列表，并导出 excel 表单")
    @PostMapping("export/list")
    public void exportList(@RequestBody Collection<Serializable> ids, HttpServletResponse response)
        throws IOException {
        service.exportList(ids, response);
    }

    @AppLog("导出 SysLog 数据")
    @PreAuthorize("@exp.check()")
    @ApiOperation("根据 SysLogQuery 和 Pageable 分页查询 SysLogDto 列表，并导出 excel 表单")
    @GetMapping("export/page")
    public void exportPage(SysLogQuery query, Pageable pageable, HttpServletResponse response)
        throws IOException {
        service.exportPage(query, pageable, response);
    }

    @AppLog("清空数据")
    @PreAuthorize("@exp.check()")
    @ApiOperation("根据 ID 批量删除 SysLog 记录")
    @PostMapping("clear")
    public Result clear() {
        List<SysLogDto> list = service.pojoListByQuery(new SysLogQuery());
        Set<Long> ids = list.stream().map(SysLogDto::getId).collect(Collectors.toSet());
        service.removeByIds(ids);
        return Result.ok();
    }

}
