package io.github.dunwu.modules.generator.entity.dto;

import io.github.dunwu.modules.generator.entity.CodeColumnConfig;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Collection;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-02-28
 */
@Data
@Accessors(chain = true)
public class TableColumnInfoDto {

    /** Schema名称 */
    @NotNull
    private String schemaName;
    /** 表名称 */
    @NotNull
    private String tableName;
    /** 表字段列表 */
    @NotEmpty
    private Collection<CodeColumnConfig> columns;

}
