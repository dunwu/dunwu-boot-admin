package io.github.dunwu.modules.generator.dao.impl;

import io.github.dunwu.data.core.annotation.Dao;
import io.github.dunwu.data.mybatis.BaseExtDaoImpl;
import io.github.dunwu.modules.generator.dao.CodeGlobalConfigDao;
import io.github.dunwu.modules.generator.dao.mapper.CodeGlobalConfigMapper;
import io.github.dunwu.modules.generator.entity.CodeGlobalConfig;
import io.github.dunwu.modules.generator.entity.dto.CodeGlobalConfigDto;
import io.github.dunwu.web.util.ServletUtil;

import java.io.IOException;
import java.util.*;
import javax.servlet.http.HttpServletResponse;

/**
 * 代码生成-全局配置 Dao 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-03-03
 */
@Dao
public class CodeGlobalConfigDaoImpl extends BaseExtDaoImpl<CodeGlobalConfigMapper, CodeGlobalConfig>
    implements CodeGlobalConfigDao {

    @Override
    public void exportDtoList(Collection<CodeGlobalConfigDto> list, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (CodeGlobalConfigDto item : list) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("ID", item.getId());
            map.put("开启权限校验", item.getEnablePermission());
            map.put("开启文件覆盖模式", item.getEnableOverride());
            map.put("开启Swagger", item.getEnableSwagger());
            map.put("作者", item.getAuthor());
            map.put("输出路径", item.getOutputDir());
            map.put("后端代码路径", item.getBackendPath());
            map.put("前端代码路径", item.getFrontendPath());
            map.put("包路径", item.getPackagePath());
            map.put("主键类型", item.getIdType());
            map.put("时间类型", item.getDateType());
            map.put("时间格式化", item.getDatePattern());
            map.put("创建者", item.getCreateBy());
            map.put("更新者", item.getUpdateBy());
            map.put("创建时间", item.getCreateTime());
            map.put("更新时间", item.getUpdateTime());
            mapList.add(map);
        }
        ServletUtil.downloadExcel(response, mapList);
    }

}
