package io.github.dunwu.modules.generator.service;

/**
 * @author peng.zhang
 * @date 2021/2/26
 */
public interface TableService {

    String getCurrentSchema();

    /**
     * 查询数据库元数据
     *
     * @param schemaName schema 名
     * @param tableName  table 名
     * @param startEnd   分页参数
     * @return /
     */
    Object getTables(String schemaName, String tableName, int[] startEnd);

}
