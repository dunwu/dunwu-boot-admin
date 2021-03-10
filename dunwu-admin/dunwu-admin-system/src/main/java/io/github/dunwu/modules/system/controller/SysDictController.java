package io.github.dunwu.modules.system.controller;

import io.github.dunwu.data.core.Result;
import io.github.dunwu.data.validator.annotation.AddCheck;
import io.github.dunwu.data.validator.annotation.EditCheck;
import io.github.dunwu.modules.monitor.annotation.AppLog;
import io.github.dunwu.modules.system.entity.dto.SysDictDto;
import io.github.dunwu.modules.system.entity.query.SysDictQuery;
import io.github.dunwu.modules.system.service.SysDictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import javax.servlet.http.HttpServletResponse;

/**
 * 系统数据字典 Controller 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-05-24
 */
@RestController
@RequestMapping("sys/dict")
@Api(tags = "系统：字典管理")
@RequiredArgsConstructor
public class SysDictController {

    private final SysDictService service;

    @AppLog("添加一条 SysDictDto 记录")
    @PreAuthorize("@exp.check('dict:add')")
    @ApiOperation("添加一条 SysDictDto 记录")
    @PostMapping("add")
    public Result add(@Validated(AddCheck.class) @RequestBody SysDictDto entity) {
        service.save(entity);
        return Result.ok();
    }

    @AppLog("更新一条 SysDictDto 记录")
    @PreAuthorize("@exp.check('dict:edit')")
    @ApiOperation("更新一条 SysDictDto 记录")
    @PostMapping("edit")
    public Result edit(@Validated(EditCheck.class) @RequestBody SysDictDto entity) {
        service.updateById(entity);
        return Result.ok();
    }

    @AppLog("根据 ID 删除一条 SysDictDto 记录")
    @PreAuthorize("@exp.check('dict:del')")
    @ApiOperation("删除一条 SysDictDto 记录")
    @PostMapping("del/{id}")
    public Result deleteById(@PathVariable Serializable id) {
        service.removeById(id);
        return Result.ok();
    }

    @AppLog("根据 ID 集合批量删除 SysDictDto 记录")
    @PreAuthorize("@exp.check('dict:del')")
    @ApiOperation("根据 ID 集合批量删除 SysDictDto 记录")
    @PostMapping("del/batch")
    public Result deleteByIds(@RequestBody Collection<Serializable> ids) {
        service.removeByIds(ids);
        return Result.ok();
    }

    @PreAuthorize("@exp.check('dict:view')")
    @ApiOperation("根据 query 条件，查询匹配条件的 SysDictDto 列表")
    @GetMapping("list")
    public Result list(SysDictQuery query) {
        return Result.ok(service.pojoListByQuery(query));
    }

    @PreAuthorize("@exp.check('dict:view')")
    @ApiOperation("根据 query 和 pageable 条件，分页查询 SysDictDto 记录")
    @GetMapping("page")
    public Result page(SysDictQuery query,
        @PageableDefault(sort = { "name" }, direction = Sort.Direction.ASC) Pageable pageable) {
        return Result.ok(service.pojoPageByQuery(query, pageable));
    }

    @PreAuthorize("@exp.check('dict:view')")
    @ApiOperation("根据 query 条件，查询匹配条件的总记录数")
    @GetMapping("count")
    public Result count(SysDictQuery query) {
        return Result.ok(service.countByQuery(query));
    }

    @PreAuthorize("@exp.check('dict:view')")
    @ApiOperation("根据 ID 查询 SysDictDto 记录")
    @GetMapping("{id}")
    public Result getById(@PathVariable Serializable id) {
        return Result.ok(service.pojoById(id));
    }

    @PreAuthorize("@exp.check('dict:view')")
    @ApiOperation("根据 query 和 pageable 条件批量导出 SysDictDto 列表数据")
    @GetMapping("export/page")
    public void exportPage(SysDictQuery query, Pageable pageable, HttpServletResponse response) throws IOException {
        service.exportPage(query, pageable, response);
    }

    @PreAuthorize("@exp.check('dict:view')")
    @ApiOperation("根据 ID 集合批量导出 SysDictDto 列表数据")
    @PostMapping("export/list")
    public void exportList(@RequestBody Collection<Serializable> ids, HttpServletResponse response)
        throws IOException {
        service.exportByIds(ids, response);
    }

}
