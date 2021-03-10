package io.github.dunwu.modules.generator.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 表的数据信息
 *
 * @author Zheng Jie
 * @date 2019-01-02
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TableInfoDto {

    private String schemaName;

    /** 表名称 */
    private Object tableName;

    /** 创建日期 */
    private Object createTime;

    /** 数据库引擎 */
    private Object engine;

    /** 编码集 */
    private Object coding;

    /** 注释 */
    private Object comment;

}
