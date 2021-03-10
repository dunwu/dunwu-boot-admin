package io.github.dunwu.modules.generator.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import io.github.dunwu.data.util.PageUtil;
import io.github.dunwu.modules.generator.entity.dto.TableInfoDto;
import io.github.dunwu.modules.generator.service.TableService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * @author peng.zhang
 * @date 2021/2/26
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TableServiceImpl implements TableService {

    @PersistenceContext
    private EntityManager em;

    private final JdbcTemplate jdbcTemplate;

    @Override
    public String getCurrentSchema() {
        return jdbcTemplate.queryForObject("SELECT database()", String.class);
    }

    @Override
    public Object getTables(String schemaName, String tableName, int[] startEnd) {
        // 使用预编译防止sql注入
        String sql = new StringBuilder("select table_name, create_time, engine, table_collation, table_comment")
            .append(" from information_schema.tables")
            .append(" where table_schema = ?")
            .append(" and table_name like ? order by create_time desc")
            .toString();

        Query query = em.createNativeQuery(sql);
        query.setFirstResult(startEnd[0]);
        query.setMaxResults(startEnd[1] - startEnd[0]);
        query.setParameter(1, schemaName);
        query.setParameter(2, StrUtil.isNotBlank(tableName) ? ("%" + tableName + "%") : "%%");
        List<?> result = query.getResultList();
        List<TableInfoDto> tableInfoDtos = new ArrayList<>();
        for (Object obj : result) {
            Object[] arr = (Object[]) obj;
            tableInfoDtos.add(
                new TableInfoDto(schemaName, arr[0], arr[1], arr[2], arr[3],
                    ObjectUtil.isNotEmpty(arr[4]) ? arr[4] : "-"));
        }
        Query query1 = em.createNativeQuery(
            "SELECT COUNT(*) FROM `information_schema`.`tables` WHERE `table_schema` = (SELECT database())");
        Object totalElements = query1.getSingleResult();
        return PageUtil.toMap(tableInfoDtos, totalElements);
    }

}
