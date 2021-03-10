package io.github.dunwu.modules.system.dao;

import io.github.dunwu.data.mybatis.IExtDao;
import io.github.dunwu.modules.system.entity.SysDept;
import io.github.dunwu.modules.system.entity.dto.SysDeptDto;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletResponse;

/**
 * 系统部门信息 Dao 接口
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-05-24
 */
public interface SysDeptDao extends IExtDao<SysDept> {

    /**
     * 根据传入的 SysDeptDto 列表，导出 excel 表单
     *
     * @param list     {@link SysDeptDto} 列表
     * @param response {@link HttpServletResponse} 实体
     */
    void exportDtoList(Collection<SysDeptDto> list, HttpServletResponse response) throws IOException;

    List<SysDeptDto> listByPid(Serializable pid);

    Set<SysDeptDto> getDeleteDepts(List<SysDept> deptList, Set<SysDeptDto> deptDtos);

    List<SysDeptDto> buildTreeList(Collection<SysDeptDto> list);

}
