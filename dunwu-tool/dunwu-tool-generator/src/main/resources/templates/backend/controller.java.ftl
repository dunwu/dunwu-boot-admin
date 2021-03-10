package ${package.Controller};

import io.github.dunwu.data.core.Result;
import io.github.dunwu.data.validator.annotation.AddCheck;
import io.github.dunwu.data.validator.annotation.EditCheck;
import ${package.Entity}.${entity};
import ${package.Query}.${table.queryName};
import ${package.Service}.${table.serviceName};
<#if enableSwagger>
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
</#if>
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
<#if !restControllerStyle>
import org.springframework.stereotype.Controller;
</#if>

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import javax.servlet.http.HttpServletResponse;

/**
 * ${table.comment!} Controller 类
 *
 * @author ${author}
 * @since ${date}
 */
<#if restControllerStyle>
@RestController
<#else>
@Controller
</#if>
@RequestMapping("<#if package.ModuleName??>/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
<#if enableSwagger>
@Api(tags = "${table.comment!} Controller 类")
</#if>
@RequiredArgsConstructor
<#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass}<${entity}> {
<#else>
public class ${table.controllerName} {
</#if>

    private final ${table.serviceName} service;
    <#if !enableSwagger>

    public ${table.controllerName}(${table.serviceName} service) {
        this.service = service;
    }
    </#if>

    <#if enableSwagger>
    @ApiOperation("添加一条 ${entity} 记录")
    <#else>
    /** 添加一条 {@link ${entity}} 记录 */
    </#if>
    @PostMapping("add")
    public Result add(@Validated(AddCheck.class) @RequestBody ${entity} entity) {
        service.save(entity);
        return Result.ok();
    }

    <#if enableSwagger>
    @ApiOperation("批量添加 ${entity} 记录")
    <#else>
    /** 批量添加 {@link ${entity}} 记录 */
    </#if>
    @PostMapping("add/batch")
    public Result addBatch(@Validated(AddCheck.class) @RequestBody Collection<${entity}> list) {
        service.saveBatch(list);
        return Result.ok();
    }

    <#if enableSwagger>
    @ApiOperation("根据 ID 更新一条 ${entity} 记录")
    <#else>
    /** 根据 ID 更新一条 {@link ${entity}} 记录 */
    </#if>
    @PostMapping("edit")
    public Result edit(@Validated(EditCheck.class) @RequestBody ${entity} entity) {
        service.updateById(entity);
        return Result.ok();
    }

    <#if enableSwagger>
    @ApiOperation("根据 ID 批量更新 ${entity} 记录")
    <#else>
    /** 根据 ID 批量更新 {@link ${entity}} 记录 */
    </#if>
    @PostMapping("edit/batch")
    public Result editBatch(@Validated(EditCheck.class) @RequestBody Collection<${entity}> list) {
        service.updateBatchById(list);
        return Result.ok();
    }

    <#if enableSwagger>
    @ApiOperation("根据 ID 删除一条 ${entity} 记录")
    <#else>
    /** 根据 ID 删除一条 {@link ${entity}} 记录 */
    </#if>
    @PostMapping("del/{id}")
    public Result deleteById(@PathVariable Serializable id) {
        service.removeById(id);
        return Result.ok();
    }

    <#if enableSwagger>
    @ApiOperation("根据 ID 列表批量删除 ${entity} 记录")
    <#else>
    /** 根据 ID 列表批量删除 {@link ${entity}} 记录 */
    </#if>
    @PostMapping("del/batch")
    public Result deleteByIds(@RequestBody Collection<Serializable> ids) {
        service.removeByIds(ids);
        return Result.ok();
    }

    <#if enableSwagger>
    @ApiOperation("根据 ${table.queryName} 查询 ${table.dtoName} 列表")
    @GetMapping("list")
    <#else>
    /** 根据 {@link ${table.queryName}} 查询 {@link ${table.dtoName}} 列表 */
    </#if>
    public Result list(${table.queryName} query) {
        return Result.ok(service.pojoListByQuery(query));
    }

    <#if enableSwagger>
    @ApiOperation("根据 ${table.queryName} 和 Pageable 分页查询 ${table.dtoName} 列表")
    <#else>
    /** 根据 {@link ${table.queryName}} 和 {@link Pageable} 分页查询 {@link ${table.dtoName}} 列表 */
    </#if>
    @GetMapping("page")
    public Result page(${table.queryName} query, Pageable pageable) {
        return Result.ok(service.pojoPageByQuery(query, pageable));
    }

    @GetMapping("{id}")
    <#if enableSwagger>
    @ApiOperation("根据 id 查询 ${table.dtoName}")
    <#else>
    /** 根据 id 查询 {@link ${table.dtoName}} */
    </#if>
    public Result getById(@PathVariable Serializable id) {
        return Result.ok(service.pojoById(id));
    }

    <#if enableSwagger>
    @ApiOperation("根据 ${table.queryName} 查询匹配条件的记录数")
    <#else>
    /** 根据 {@link ${table.queryName}} 查询匹配条件的记录数 */
    </#if>
    @GetMapping("count")
    public Result count(${table.queryName} query) {
        return Result.ok(service.countByQuery(query));
    }

    <#if enableSwagger>
    @ApiOperation("根据 id 列表查询 ${table.dtoName} 列表，并导出 excel 表单")
    <#else>
    /** 根据 id 列表查询 {@link ${table.dtoName}} 列表，并导出 excel 表单 */
    </#if>
    @PostMapping("export/list")
    public void exportList(@RequestBody Collection<Serializable> ids, HttpServletResponse response)
        throws IOException {
        service.exportList(ids, response);
    }

    <#if enableSwagger>
    @ApiOperation("根据 ${table.queryName} 和 Pageable 分页查询 ${table.dtoName} 列表，并导出 excel 表单")
    <#else>
    /** 根据 {@link ${table.queryName}} 和 {@link Pageable} 分页查询 {@link ${table.dtoName}} 列表，并导出 excel 表单 */
    </#if>
    @GetMapping("export/page")
    public void exportPage(${table.queryName} query, Pageable pageable, HttpServletResponse response)
        throws IOException {
        service.exportPage(query, pageable, response);
    }

}
