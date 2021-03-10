package io.github.dunwu.modules.generator.dao.impl;

import io.github.dunwu.data.core.annotation.Dao;
import io.github.dunwu.data.mybatis.BaseExtDaoImpl;
import io.github.dunwu.modules.generator.dao.CodeColumnConfigDao;
import io.github.dunwu.modules.generator.dao.mapper.CodeColumnConfigMapper;
import io.github.dunwu.modules.generator.entity.CodeColumnConfig;
import io.github.dunwu.modules.generator.entity.dto.CodeColumnConfigDto;
import io.github.dunwu.web.util.ServletUtil;

import java.io.IOException;
import java.util.*;
import javax.servlet.http.HttpServletResponse;

/**
 * 代码生成-字段级别配置 Dao 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-03-04
 */
@Dao
public class CodeColumnConfigDaoImpl extends BaseExtDaoImpl<CodeColumnConfigMapper, CodeColumnConfig>
    implements CodeColumnConfigDao {

    @Override
    public void exportDtoList(Collection<CodeColumnConfigDto> list, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (CodeColumnConfigDto item : list) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("ID", item.getId());
            map.put("Table ID", item.getTableId());
            map.put("Schema名称", item.getSchemaName());
            map.put("Table名称", item.getTableName());
            map.put("字段名称", item.getFieldName());
            map.put("字段展示名称（实体字段）", item.getPropertyName());
            map.put("字段注释", item.getComment());
            map.put("字段数据类型", item.getType());
            map.put("字段 Java 类型", item.getJavaType());
            map.put("键类型", item.getKeyType());
            map.put("不允许为空", item.getNotNull());
            map.put("出现在表单", item.getEnableForm());
            map.put("出现在列表", item.getEnableList());
            map.put("出现在查询", item.getEnableQuery());
            map.put("允许排序", item.getEnableSort());
            map.put("允许校验", item.getEnableValidate());
            map.put("表单类型", item.getFormType());
            map.put("列表类型", item.getListType());
            map.put("查询类型", item.getQueryType());
            map.put("排序类型", item.getSortType());
            map.put("校验类型", item.getValidateType());
            map.put("时间类型", item.getDateType());
            map.put("时间格式", item.getDatePattern());
            map.put("字典名称", item.getDictName());
            map.put("@TableField 填充属性", item.getFill());
            map.put("扩展属性", item.getExtra());
            map.put("创建者", item.getCreateBy());
            map.put("更新者", item.getUpdateBy());
            map.put("创建时间", item.getCreateTime());
            map.put("更新时间", item.getUpdateTime());
            mapList.add(map);
        }
        ServletUtil.downloadExcel(response, mapList);
    }

}
