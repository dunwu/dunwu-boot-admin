package io.github.dunwu.modules.generator.entity.dto;

import lombok.Data;
import lombok.ToString;

/**
 * 数据表列信息
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-04-28
 */
@Data
@ToString
public class ColumnInfoDto {

    /** Schema的名称 */
    private String schemaName;
    /** 表的名称 */
    private String tableName;
    /** 列的名称 */
    private String columnName;
    /** 列的数据类型 */
    private String javaType;
    /** 列的Key类型 */
    private String columnKey;
    /** 列的默认值 */
    private String columnDefault;
    /** 列的备注 */
    private String note;
    /** 扩展信息 */
    private String extra;
    /** 是否不允许为空 */
    private String notNull;
    /** 是否出现在表单 */
    private boolean formEnabled;
    /** 是否出现在列表 */
    private boolean listEnabled;
    /** 是否出现在搜索 */
    private boolean searchEnabled;

}
