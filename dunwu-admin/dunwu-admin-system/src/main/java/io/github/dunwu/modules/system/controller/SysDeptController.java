package io.github.dunwu.modules.system.controller;

import io.github.dunwu.data.core.Result;
import io.github.dunwu.data.validator.annotation.AddCheck;
import io.github.dunwu.data.validator.annotation.EditCheck;
import io.github.dunwu.modules.monitor.annotation.AppLog;
import io.github.dunwu.modules.system.entity.dto.SysDeptDto;
import io.github.dunwu.modules.system.entity.query.SysDeptQuery;
import io.github.dunwu.modules.system.service.SysDeptService;
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
 * 系统部门信息 Controller 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-05-24
 */
@RestController
@RequestMapping("sys/dept")
@Api(tags = "系统：部门管理")
@RequiredArgsConstructor
public class SysDeptController {

    private final SysDeptService service;

    @AppLog("添加一条 SysDept 记录")
    @PreAuthorize("@exp.check('dept:add')")
    @ApiOperation("添加一条 SysDept 记录")
    @PostMapping("add")
    public Result add(@Validated(AddCheck.class) @RequestBody SysDeptDto entity) {
        service.save(entity);
        return Result.ok();
    }

    @AppLog("更新一条 SysDept 记录")
    @PreAuthorize("@exp.check('dept:edit')")
    @ApiOperation("更新一条 SysDept 记录")
    @PostMapping("edit")
    public Result edit(@Validated(EditCheck.class) @RequestBody SysDeptDto entity) {
        service.updateById(entity);
        return Result.ok();
    }

    @AppLog("删除一条 SysDept 记录")
    @PreAuthorize("@exp.check('dept:del')")
    @ApiOperation("删除一条 SysDept 记录")
    @PostMapping("del/{id}")
    public Result deleteById(@PathVariable Serializable id) {
        service.removeById(id);
        return Result.ok();
    }

    @AppLog("根据 ID 集合批量删除 SysDept 记录")
    @PreAuthorize("@exp.check('dept:del')")
    @ApiOperation("根据 ID 集合批量删除 SysDept 记录")
    @PostMapping("del/batch")
    public Result deleteByIds(@RequestBody Collection<Serializable> ids) {
        service.removeByIds(ids);
        return Result.ok();
    }

    @PreAuthorize("@exp.check('dept:view')")
    @ApiOperation("根据 query 条件，查询匹配条件的 SysDeptDto 列表")
    @GetMapping("list")
    public Result list(SysDeptQuery query) {
        return Result.ok(service.pojoListByQuery(query));
    }

    @PreAuthorize("@exp.check('dept:view')")
    @ApiOperation("根据 query 和 pageable 条件，分页查询 SysDeptDto 记录")
    @GetMapping("page")
    public Result page(SysDeptQuery query, Pageable pageable) {
        return Result.ok(service.pojoPageByQuery(query, pageable));
    }

    @PreAuthorize("@exp.check('dept:view')")
    @ApiOperation("根据 query 条件，查询匹配条件的总记录数")
    @GetMapping("count")
    public Result count(SysDeptQuery query) {
        return Result.ok(service.countByQuery(query));
    }

    @PreAuthorize("@exp.check('dept:view')")
    @ApiOperation("根据 ID 查询 SysDeptDto 记录")
    @GetMapping("{id}")
    public Result getById(@PathVariable Serializable id) {
        return Result.ok(service.pojoById(id));
    }

    @PreAuthorize("@exp.check('dept:view')")
    @ApiOperation("根据 query 和 pageable 条件批量导出 SysDeptDto 列表数据")
    @GetMapping("export/page")
    public void exportPage(SysDeptQuery query, Pageable pageable, HttpServletResponse response) throws IOException {
        service.exportPage(query, pageable, response);
    }

    @PreAuthorize("@exp.check('dept:view')")
    @ApiOperation("根据 ID 集合批量导出 SysDeptDto 列表数据")
    @PostMapping("export/list")
    public void exportList(@RequestBody Collection<Serializable> ids, HttpServletResponse response) throws IOException {
        service.exportList(ids, response);
    }

    @PreAuthorize("@exp.check('dept:view')")
    @ApiOperation("根据 query 条件，返回 SysDeptDto 树形列表")
    @GetMapping("treeList")
    public Result treeList(SysDeptQuery query) {
        return Result.ok(service.treeList(query));
    }

    @PreAuthorize("@exp.check('dept:view')")
    @ApiOperation("根据ID获取同级与上级数据")
    @PostMapping("superiorTreeList")
    public Result superiorTreeList(@RequestBody Collection<Serializable> ids) {
        return Result.ok(service.treeListByIds(ids));
    }

    // @Log("更新一条 SysDept 记录的关联关系")
    // @PreAuthorize("@exp.check('dept:edit')")
    // @ApiOperation("更新一条 SysDept 记录的关联关系")
    // @PutMapping("relation")
    // public ResponseEntity<Object> updateRelations(@Validated @RequestBody SysDeptRelationDto dto) {
    //     return new ResponseEntity<>(service.updateRelations(dto), HttpStatus.ACCEPTED);
    // }
}
