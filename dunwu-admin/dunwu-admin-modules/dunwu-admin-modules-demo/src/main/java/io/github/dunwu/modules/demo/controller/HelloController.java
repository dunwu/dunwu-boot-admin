package io.github.dunwu.modules.demo.controller;

import io.github.dunwu.data.core.Result;
import io.github.dunwu.data.validator.annotation.AddCheck;
import io.github.dunwu.data.validator.annotation.EditCheck;
import io.github.dunwu.modules.demo.entity.Hello;
import io.github.dunwu.modules.demo.entity.query.HelloQuery;
import io.github.dunwu.modules.demo.service.HelloService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import javax.servlet.http.HttpServletResponse;

/**
 * 测试 Controller 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-03-10
 */
@RestController
@RequestMapping("/demo/hello")
@Api(tags = "测试 Controller 类")
@RequiredArgsConstructor
public class HelloController {

    private final HelloService service;

    @ApiOperation("添加一条 Hello 记录")
    @PostMapping("add")
    public Result add(@Validated(AddCheck.class) @RequestBody Hello entity) {
        service.save(entity);
        return Result.ok();
    }

    @ApiOperation("批量添加 Hello 记录")
    @PostMapping("add/batch")
    public Result addBatch(@Validated(AddCheck.class) @RequestBody Collection<Hello> list) {
        service.saveBatch(list);
        return Result.ok();
    }

    @ApiOperation("根据 ID 更新一条 Hello 记录")
    @PostMapping("edit")
    public Result edit(@Validated(EditCheck.class) @RequestBody Hello entity) {
        service.updateById(entity);
        return Result.ok();
    }

    @ApiOperation("根据 ID 批量更新 Hello 记录")
    @PostMapping("edit/batch")
    public Result editBatch(@Validated(EditCheck.class) @RequestBody Collection<Hello> list) {
        service.updateBatchById(list);
        return Result.ok();
    }

    @ApiOperation("根据 ID 删除一条 Hello 记录")
    @PostMapping("del/{id}")
    public Result deleteById(@PathVariable Serializable id) {
        service.removeById(id);
        return Result.ok();
    }

    @ApiOperation("根据 ID 列表批量删除 Hello 记录")
    @PostMapping("del/batch")
    public Result deleteByIds(@RequestBody Collection<Serializable> ids) {
        service.removeByIds(ids);
        return Result.ok();
    }

    @ApiOperation("根据 HelloQuery 查询 HelloDto 列表")
    @GetMapping("list")
    public Result list(HelloQuery query) {
        return Result.ok(service.pojoListByQuery(query));
    }

    @ApiOperation("根据 HelloQuery 和 Pageable 分页查询 HelloDto 列表")
    @GetMapping("page")
    public Result page(HelloQuery query, Pageable pageable) {
        return Result.ok(service.pojoPageByQuery(query, pageable));
    }

    @GetMapping("{id}")
    @ApiOperation("根据 id 查询 HelloDto")
    public Result getById(@PathVariable Serializable id) {
        return Result.ok(service.pojoById(id));
    }

    @ApiOperation("根据 HelloQuery 查询匹配条件的记录数")
    @GetMapping("count")
    public Result count(HelloQuery query) {
        return Result.ok(service.countByQuery(query));
    }

    @ApiOperation("根据 id 列表查询 HelloDto 列表，并导出 excel 表单")
    @PostMapping("export/list")
    public void exportList(@RequestBody Collection<Serializable> ids, HttpServletResponse response)
        throws IOException {
        service.exportList(ids, response);
    }

    @ApiOperation("根据 HelloQuery 和 Pageable 分页查询 HelloDto 列表，并导出 excel 表单")
    @GetMapping("export/page")
    public void exportPage(HelloQuery query, Pageable pageable, HttpServletResponse response)
        throws IOException {
        service.exportPage(query, pageable, response);
    }

}
