package io.github.dunwu.modules.system.service.impl;

import io.github.dunwu.data.mybatis.ServiceImpl;
import io.github.dunwu.modules.system.dao.SysDeptDao;
import io.github.dunwu.modules.system.dao.SysJobDao;
import io.github.dunwu.modules.system.entity.SysJob;
import io.github.dunwu.modules.system.entity.dto.SysDeptDto;
import io.github.dunwu.modules.system.entity.dto.SysJobDto;
import io.github.dunwu.modules.system.entity.dto.SysRoleDto;
import io.github.dunwu.modules.system.service.SysJobService;
import io.github.dunwu.modules.system.service.SysRoleService;
import io.github.dunwu.tool.bean.BeanUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

/**
 * 系统岗位信息 Service 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-05-24
 */
@Service
@RequiredArgsConstructor
public class SysJobServiceImpl extends ServiceImpl implements SysJobService {

    private final SysJobDao jobDao;
    private final SysDeptDao deptDao;
    private final SysRoleService roleService;

    @Override
    public boolean save(SysJobDto entity) {
        return jobDao.save(dtoToDo(entity));
    }

    @Override
    public boolean updateById(SysJobDto entity) {
        return jobDao.updateById(dtoToDo(entity));
    }

    @Override
    public boolean removeById(Serializable id) {
        return jobDao.removeById(id);
    }

    @Override
    public boolean removeByIds(Collection<Serializable> ids) {
        return jobDao.removeByIds(ids);
    }

    @Override
    public Page<SysJobDto> pojoPageByQuery(Object query, Pageable pageable) {
        return jobDao.pojoPageByQuery(query, pageable, this::doToDto);
    }

    @Override
    public List<SysJobDto> pojoListByQuery(Object query) {
        return jobDao.pojoListByQuery(query, this::doToDto);
    }

    @Override
    public SysJobDto pojoById(Serializable id) {
        return jobDao.pojoById(id, this::doToDto);
    }

    @Override
    public SysJobDto pojoByQuery(Object query) {
        return jobDao.pojoByQuery(query, this::doToDto);
    }

    @Override
    public Integer countByQuery(Object query) {
        return jobDao.countByQuery(query);
    }

    @Override
    public void exportList(Collection<Serializable> ids, HttpServletResponse response) throws IOException {
        List<SysJobDto> list = jobDao.pojoListByIds(ids, this::doToDto);
        jobDao.exportDtoList(list, response);
    }

    @Override
    public void exportPage(Object query, Pageable pageable, HttpServletResponse response) throws IOException {
        Page<SysJobDto> page = jobDao.pojoPageByQuery(query, pageable, this::doToDto);
        jobDao.exportDtoList(page.getContent(), response);
    }

    private SysJobDto doToDto(SysJob job) {
        SysDeptDto sysDeptDto = deptDao.pojoById(job.getDeptId(), SysDeptDto.class);
        List<SysRoleDto> roles = roleService.pojoListByJobId(job.getId());
        SysJobDto sysJobDto = BeanUtil.toBean(job, SysJobDto.class);
        sysJobDto.setDept(sysDeptDto);
        sysJobDto.setRoles(roles);
        return sysJobDto;
    }

    private SysJob dtoToDo(SysJobDto dto) {
        return BeanUtil.toBean(dto, SysJob.class);
    }

}
