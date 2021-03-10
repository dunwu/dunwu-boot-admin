package io.github.dunwu.modules.system.controller;

import io.github.dunwu.data.core.Result;
import io.github.dunwu.data.validator.annotation.AddCheck;
import io.github.dunwu.data.validator.annotation.EditCheck;
import io.github.dunwu.modules.monitor.annotation.AppLog;
import io.github.dunwu.modules.system.entity.SysMenu;
import io.github.dunwu.modules.system.entity.query.SysMenuQuery;
import io.github.dunwu.modules.system.service.SysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

/**
 * 系统菜单信息 Controller 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-05-24
 */
@RestController
@RequestMapping("sys/menu")
@Api(tags = "系统：菜单管理")
@RequiredArgsConstructor
public class SysMenuController {

    private final SysMenuService service;

    @AppLog("添加一条 SysMenu 记录")
    @PreAuthorize("@exp.check('menu:add')")
    @ApiOperation("添加一条 SysMenu 记录")
    @PostMapping("add")
    public Result add(@Validated(AddCheck.class) @RequestBody SysMenu entity) {
        service.save(entity);
        return Result.ok();
    }

    @AppLog("更新一条 SysMenu 记录")
    @PreAuthorize("@exp.check('menu:edit')")
    @ApiOperation("更新一条 SysMenu 记录")
    @PostMapping("edit")
    public Result edit(@Validated(EditCheck.class) @RequestBody SysMenu entity) {
        service.updateById(entity);
        return Result.ok();
    }

    @AppLog("删除一条 SysMenu 记录")
    @PreAuthorize("@exp.check('menu:del')")
    @ApiOperation("删除一条 SysMenu 记录")
    @PostMapping("del/{id}")
    public Result deleteById(@PathVariable Serializable id) {
        service.removeById(id);
        return Result.ok();
    }

    @AppLog("根据 ID 集合批量删除 SysMenu 记录")
    @PreAuthorize("@exp.check('menu:del')")
    @ApiOperation("根据 ID 集合批量删除 SysMenu 记录")
    @PostMapping("del/batch")
    public Result deleteByIds(@RequestBody Collection<Serializable> ids) {
        service.removeByIds(ids);
        return Result.ok();
    }

    @PreAuthorize("@exp.check('menu:view')")
    @ApiOperation("根据 query 条件，查询匹配条件的 SysMenuDto 列表")
    @GetMapping("list")
    public Result list(SysMenuQuery query) {
        return Result.ok(service.pojoListByQuery(query));
    }

    @PreAuthorize("@exp.check('menu:view')")
    @ApiOperation("根据 query 和 pageable 条件，分页查询 SysMenuDto 记录")
    @GetMapping("page")
    public Result page(SysMenuQuery query, Pageable pageable) {
        return Result.ok(service.pojoPageByQuery(query, pageable));
    }

    @PreAuthorize("@exp.check('menu:view')")
    @ApiOperation("根据 query 条件，查询匹配条件的总记录数")
    @GetMapping("count")
    public Result count(SysMenuQuery query) {
        return Result.ok(service.countByQuery(query));
    }

    @PreAuthorize("@exp.check('menu:view')")
    @ApiOperation("根据 ID 查询 SysMenu 记录")
    @GetMapping("{id}")
    public Result getById(@PathVariable Serializable id) {
        return Result.ok(service.pojoById(id));
    }

    @PreAuthorize("@exp.check('menu:view')")
    @ApiOperation("根据 query 和 pageable 条件批量导出 SysMenuDto 列表数据")
    @GetMapping("export/page")
    public void exportPage(SysMenuQuery query, Pageable pageable, HttpServletResponse response) throws IOException {
        service.exportPage(query, pageable, response);
    }

    @PreAuthorize("@exp.check('menu:view')")
    @ApiOperation("根据 ID 集合批量导出 SysMenuDto 列表数据")
    @PostMapping("export/list")
    public void exportList(@RequestBody Collection<Serializable> ids, HttpServletResponse response)
        throws IOException {
        service.exportList(ids, response);
    }

    @PreAuthorize("@exp.check('menu:view')")
    @ApiOperation("根据 query 条件，返回 SysMenuDto 树形列表")
    @GetMapping("treeList")
    public Result treeList(SysMenuQuery query) {
        return Result.ok(service.treeList(query));
    }

    @PreAuthorize("@exp.check('menu:view')")
    @ApiOperation("根据ID获取同级与上级数据")
    @PostMapping("superiorTreeList")
    public Result superiorTreeList(@RequestBody Collection<Serializable> ids) {
        return Result.ok(service.treeListByIds(ids));
    }

    @PreAuthorize("@exp.check('menu:view')")
    @ApiOperation("根据ID获取所有孩子节点ID")
    @GetMapping("childrenIds")
    public Result childrenIds(Long id) {
        List<Long> ids = new ArrayList<>();
        ids.add(id);
        ids.addAll(service.childrenIds(id));
        return Result.ok(ids);
    }

    @ApiOperation("获取当前用户展示于前端的菜单列表")
    @GetMapping(value = "mine")
    public Result mineList() {
        return Result.ok(service.buildMenuListForCurrentUser());
    }

}
