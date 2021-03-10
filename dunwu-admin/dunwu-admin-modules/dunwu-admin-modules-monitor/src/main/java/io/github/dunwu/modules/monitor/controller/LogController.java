package io.github.dunwu.modules.monitor.controller;

import io.github.dunwu.data.core.Result;
import io.github.dunwu.modules.monitor.annotation.AppLog;
import io.github.dunwu.modules.monitor.dao.LogRecordDao;
import io.github.dunwu.modules.monitor.entity.LogRecord;
import io.github.dunwu.modules.monitor.entity.dto.LogDto;
import io.github.dunwu.modules.monitor.entity.query.LogQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;

/**
 * 系统日志记录 Controller
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-04-09
 */
@RestController
@RequestMapping("monitor/log")
@Api(tags = "系统：日志管理")
@RequiredArgsConstructor
public class LogController {

    private final LogRecordDao dao;

    @AppLog("清空数据")
    @ApiOperation("根据 ID 批量删除 SysLog 记录")
    @PreAuthorize("@exp.check()")
    @PostMapping("clear")
    public Result clear() {
        List<LogRecord> list = dao.list();
        Set<Long> ids = list.stream().map(LogRecord::getId).collect(Collectors.toSet());
        dao.removeByIds(ids);
        return Result.ok();
    }

    @AppLog("批量删除数据")
    @ApiOperation("根据 ID 批量删除 SysLog 记录")
    @PreAuthorize("@exp.check()")
    @PostMapping("del/batch")
    public Result deleteByIds(@RequestBody Set<String> ids) {
        dao.removeByIds(ids);
        return Result.ok();
    }

    @PreAuthorize("@exp.check('job:view')")
    @ApiOperation("根据 query 条件，查询匹配条件的 SysJobDto 列表")
    @GetMapping("list")
    public Result list(LogQuery query) {
        return Result.ok(dao.listByQuery(query));
    }

    @PreAuthorize("@exp.check()")
    @ApiOperation("根据 query 和 pageable 条件，分页查询 SysLog 记录")
    @GetMapping("page")
    public Result page(LogQuery query, Pageable pageable) {
        return Result.ok(dao.pageByQuery(query, pageable));
    }

    @ApiOperation("根据 entity 条件，查询 SysLog 总记录数")
    @PreAuthorize("@exp.check()")
    @GetMapping("count")
    public Result count(LogQuery query) {
        return Result.ok(dao.countByQuery(query));
    }

    @ApiOperation("根据 ID 查询 SysLog 记录")
    @PreAuthorize("@exp.check()")
    @GetMapping("{id}")
    public Result getById(@PathVariable String id) {
        return Result.ok(dao.getById(id));
    }

    @AppLog("导出 SysLog 数据")
    @ApiOperation("导出 SysLog 数据")
    @PreAuthorize("@exp.check()")
    @GetMapping("export/page")
    public void exportData(HttpServletResponse response, LogQuery query, Pageable pageable) throws IOException {
        Page<LogDto> pageResult = dao.pojoPageByQuery(query, pageable, LogDto.class);
        dao.exportData(pageResult.getContent(), response);
    }

}
