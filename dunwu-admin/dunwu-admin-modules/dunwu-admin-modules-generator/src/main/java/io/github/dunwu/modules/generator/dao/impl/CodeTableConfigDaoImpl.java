package io.github.dunwu.modules.generator.dao.impl;

import io.github.dunwu.data.core.annotation.Dao;
import io.github.dunwu.data.mybatis.BaseExtDaoImpl;
import io.github.dunwu.modules.generator.dao.CodeTableConfigDao;
import io.github.dunwu.modules.generator.dao.mapper.CodeTableConfigMapper;
import io.github.dunwu.modules.generator.entity.CodeTableConfig;
import io.github.dunwu.modules.generator.entity.dto.CodeTableConfigDto;
import io.github.dunwu.web.util.ServletUtil;

import java.io.IOException;
import java.util.*;
import javax.servlet.http.HttpServletResponse;

/**
 * 代码生成-表级别配置 Dao 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-03-04
 */
@Dao
public class CodeTableConfigDaoImpl extends BaseExtDaoImpl<CodeTableConfigMapper, CodeTableConfig>
    implements CodeTableConfigDao {

    @Override
    public void exportDtoList(Collection<CodeTableConfigDto> list, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (CodeTableConfigDto item : list) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("ID", item.getId());
            map.put("Schema名称", item.getSchemaName());
            map.put("Table名称", item.getTableName());
            map.put("Table注释", item.getComment());
            map.put("开启权限校验", item.getEnablePermission());
            map.put("开启文件覆盖模式", item.getEnableOverride());
            map.put("开启Swagger2", item.getEnableSwagger());
            map.put("作者", item.getAuthor());
            map.put("输出路径", item.getOutputDir());
            map.put("后端代码路径", item.getBackendPath());
            map.put("前端代码路径", item.getFrontendPath());
            map.put("包路径", item.getPackagePath());
            map.put("主键类型", item.getIdType());
            map.put("时间类型", item.getDateType());
            map.put("时间格式", item.getDatePattern());
            map.put("允许表单", item.getEnableForm());
            map.put("允许列表", item.getEnableList());
            map.put("允许查询", item.getEnableQuery());
            map.put("允许排序", item.getEnableSort());
            map.put("允许校验", item.getEnableValidate());
            map.put("模块名称", item.getModuleName());
            map.put("表前缀", item.getTablePrefix());
            map.put("REST接口根路径", item.getApiBaseUrl());
            map.put("创建者", item.getCreateBy());
            map.put("更新者", item.getUpdateBy());
            map.put("创建时间", item.getCreateTime());
            map.put("更新时间", item.getUpdateTime());
            mapList.add(map);
        }
        ServletUtil.downloadExcel(response, mapList);
    }

}
