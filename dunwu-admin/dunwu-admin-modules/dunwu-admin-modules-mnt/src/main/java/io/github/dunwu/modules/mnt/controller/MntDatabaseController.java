package io.github.dunwu.modules.mnt.controller;

import io.github.dunwu.data.core.Result;
import io.github.dunwu.data.validator.annotation.AddCheck;
import io.github.dunwu.data.validator.annotation.EditCheck;
import io.github.dunwu.modules.mnt.entity.MntDatabase;
import io.github.dunwu.modules.mnt.entity.dto.MntDatabaseDto;
import io.github.dunwu.modules.mnt.entity.query.MntDatabaseQuery;
import io.github.dunwu.modules.mnt.service.MntDatabaseService;
import io.github.dunwu.modules.mnt.util.SqlUtils;
import io.github.dunwu.modules.monitor.annotation.AppLog;
import io.github.dunwu.util.FileUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Controller 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-03-07
 */
@RestController
@RequestMapping("/mnt/database")
@Api(tags = " Controller 类")
@RequiredArgsConstructor
public class MntDatabaseController {

    private final String fileSavePath = FileUtil.getTmpDirPath() + "/";
    private final MntDatabaseService service;

    @ApiOperation("添加一条 MntDatabase 记录")
    @PostMapping("add")
    public Result add(@Validated(AddCheck.class) @RequestBody MntDatabase entity) {
        service.save(entity);
        return Result.ok();
    }

    @ApiOperation("批量添加 MntDatabase 记录")
    @PostMapping("add/batch")
    public Result addBatch(@Validated(AddCheck.class) @RequestBody Collection<MntDatabase> list) {
        service.saveBatch(list);
        return Result.ok();
    }

    @ApiOperation("更新一条 MntDatabase 记录")
    @PostMapping("edit")
    public Result edit(@Validated(EditCheck.class) @RequestBody MntDatabase entity) {
        service.updateById(entity);
        return Result.ok();
    }

    @ApiOperation("批量更新 MntDatabase 记录")
    @PostMapping("edit/batch")
    public Result editBatch(@Validated(EditCheck.class) @RequestBody Collection<MntDatabase> list) {
        service.updateBatchById(list);
        return Result.ok();
    }

    @ApiOperation("删除一条 MntDatabase 记录")
    @PostMapping("del/{id}")
    public Result deleteById(@PathVariable Serializable id) {
        service.removeById(id);
        return Result.ok();
    }

    @ApiOperation("根据 ID 集合批量删除 MntDatabase 记录")
    @PostMapping("del/batch")
    public Result deleteByIds(@RequestBody Collection<Serializable> ids) {
        service.removeByIds(ids);
        return Result.ok();
    }

    @ApiOperation("根据 query 条件，查询匹配条件的 MntDatabaseDto 列表")
    @GetMapping("list")
    public Result list(MntDatabaseQuery query) {
        return Result.ok(service.pojoListByQuery(query));
    }

    @ApiOperation("根据 query 和 pageable 条件，分页查询 MntDatabaseDto 记录")
    @GetMapping("page")
    public Result page(MntDatabaseQuery query, Pageable pageable) {
        return Result.ok(service.pojoPageByQuery(query, pageable));
    }

    @ApiOperation("根据 query 条件，查询匹配条件的总记录数")
    @GetMapping("count")
    public Result count(MntDatabaseQuery query) {
        return Result.ok(service.countByQuery(query));
    }

    @GetMapping("{id}")
    @ApiOperation("根据 ID 查询 MntDatabaseDto 记录")
    public Result getById(@PathVariable Serializable id) {
        return Result.ok(service.pojoById(id));
    }

    @PostMapping("export/list")
    @ApiOperation("根据 ID 集合批量导出 MntDatabaseDto 列表数据")
    public void exportList(@RequestBody Collection<Serializable> ids, HttpServletResponse response)
        throws IOException {
        service.exportList(ids, response);
    }

    @GetMapping("export/page")
    @ApiOperation("根据 query 和 pageable 条件批量导出 MntDatabaseDto 列表数据")
    public void exportPage(MntDatabaseQuery query, Pageable pageable, HttpServletResponse response)
        throws IOException {
        service.exportPage(query, pageable, response);
    }

    @AppLog("测试数据库链接")
    @ApiOperation(value = "测试数据库链接")
    @PostMapping("/testConnect")
    @PreAuthorize("@exp.check('database:view')")
    public Result testConnect(@Validated @RequestBody MntDatabaseDto entity) {
        return Result.ok(service.testConnection(entity));
    }

    @AppLog("执行SQL脚本")
    @ApiOperation(value = "执行SQL脚本")
    @PostMapping(value = "/upload")
    @PreAuthorize("@exp.check('database:add')")
    public Result upload(@RequestBody MultipartFile file, HttpServletRequest request) throws Exception {
        String id = request.getParameter("id");
        MntDatabaseDto database = service.pojoById(id);
        String fileName;
        if (database != null) {
            fileName = file.getOriginalFilename();
            File executeFile = new File(fileSavePath + fileName);
            FileUtil.del(executeFile);
            file.transferTo(executeFile);
            String result = SqlUtils.executeFile(database.getJdbcUrl(), database.getUsername(),
                database.getPassword(), executeFile);
            return Result.ok(result);
        } else {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Database not exist");
        }
    }

}
