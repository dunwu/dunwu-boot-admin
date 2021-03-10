package io.github.dunwu.modules.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.db.DbRuntimeException;
import io.github.dunwu.data.mybatis.ServiceImpl;
import io.github.dunwu.modules.system.dao.SysDeptDao;
import io.github.dunwu.modules.system.dao.SysJobDao;
import io.github.dunwu.modules.system.dao.SysRoleDeptDao;
import io.github.dunwu.modules.system.dao.SysUserDao;
import io.github.dunwu.modules.system.entity.SysDept;
import io.github.dunwu.modules.system.entity.SysJob;
import io.github.dunwu.modules.system.entity.SysRoleDept;
import io.github.dunwu.modules.system.entity.SysUser;
import io.github.dunwu.modules.system.entity.dto.SysDeptDto;
import io.github.dunwu.modules.system.entity.dto.SysDeptRelationDto;
import io.github.dunwu.modules.system.entity.query.SysDeptQuery;
import io.github.dunwu.modules.system.service.SysDeptService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;

/**
 * 系统部门信息 Service 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-05-24
 */
@Service
@RequiredArgsConstructor
public class SysDeptServiceImpl extends ServiceImpl implements SysDeptService {

    private final SysDeptDao deptDao;
    private final SysJobDao jobDao;
    private final SysUserDao userDao;
    private final SysRoleDeptDao roleDeptDao;

    @Override
    public boolean save(SysDeptDto entity) {
        return deptDao.save(dtoToDo(entity));
    }

    @Override
    public boolean updateById(SysDeptDto entity) {
        if (entity.getPid() != null && entity.getId().equals(entity.getPid())) {
            throw new IllegalArgumentException("上级部门不能设为自身");
        }
        return deptDao.updateById(dtoToDo(entity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeById(Serializable id) {
        List<SysDeptDto> sysDeptDtos = deptDao.listByPid(id);
        Set<Serializable> ids = new HashSet<>();
        ids.add(id);
        if (CollUtil.isNotEmpty(sysDeptDtos)) {
            ids.addAll(sysDeptDtos.stream().map(SysDeptDto::getId).collect(Collectors.toSet()));
        }
        return deptDao.removeByIds(ids);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeByIds(Collection<Serializable> ids) {
        if (CollUtil.isEmpty(ids)) {
            return true;
        }

        for (Serializable id : ids) {
            if (!removeById(id)) {
                throw new DbRuntimeException("批量删除部门记录失败");
            }
        }
        return true;
    }

    @Override
    public Page<SysDeptDto> pojoPageByQuery(Object query, Pageable pageable) {
        return deptDao.pojoPageByQuery(query, pageable, this::doToDto);
    }

    @Override
    public List<SysDeptDto> pojoListByQuery(Object query) {
        return deptDao.pojoListByQuery(query, this::doToDto);
    }

    @Override
    public SysDeptDto pojoById(Serializable id) {
        SysDeptDto sysDeptDto = deptDao.pojoById(id, this::doToDto);
        if (sysDeptDto != null) {
            sysDeptDto.setLabel(sysDeptDto.getName());
        }
        return sysDeptDto;
    }

    @Override
    public SysDeptDto pojoByQuery(Object query) {
        return deptDao.pojoByQuery(query, this::doToDto);
    }

    @Override
    public Integer countByQuery(Object query) {
        return deptDao.countByQuery(query);
    }

    @Override
    public void exportList(Collection<Serializable> ids, HttpServletResponse response) throws IOException {
        List<SysDeptDto> list = deptDao.pojoListByIds(ids, this::doToDto);
        deptDao.exportDtoList(list, response);
    }

    @Override
    public void exportPage(Object query, Pageable pageable, HttpServletResponse response) throws IOException {
        Page<SysDeptDto> page = deptDao.pojoPageByQuery(query, pageable, this::doToDto);
        deptDao.exportDtoList(page.getContent(), response);
    }

    @Override
    public List<SysDeptDto> pojoListByPid(Serializable pid) {
        return deptDao.listByPid(pid);
    }

    @Override
    public List<SysDeptDto> pojoListByRoleId(Long roleId) {
        List<SysDeptDto> deptList = new ArrayList<>();
        List<SysRoleDept> list = roleDeptDao.list(new SysRoleDept().setRoleId(roleId));
        if (CollUtil.isEmpty(list)) {
            return deptList;
        }

        Set<Long> ids = list.stream().map(SysRoleDept::getId).collect(Collectors.toSet());
        if (CollUtil.isEmpty(ids)) {
            return deptList;
        }
        return deptDao.pojoListByIds(ids, this::doToDto);
    }

    @Override
    public List<SysDeptDto> treeList(Object query) {
        Collection<SysDeptDto> list = pojoListByQuery(query);
        return deptDao.buildTreeList(list);
    }

    @Override
    public List<SysDeptDto> treeListByIds(Collection<Serializable> ids) {
        if (CollectionUtil.isEmpty(ids)) {
            return Collections.emptyList();
        }

        List<SysDeptDto> list = new ArrayList<>();
        for (Serializable id : ids) {
            SysDeptDto entity = pojoById(id);
            if (entity == null) {
                continue;
            }

            if (entity.getPid() != null) {
                // 获取上级部门
                SysDeptDto parent = pojoById(entity.getPid());
                list.add(parent);

                // 获取所有同级部门
                SysDeptQuery query = new SysDeptQuery();
                query.setPid(entity.getPid());
                list.addAll(pojoListByQuery(query));
            }
        }

        if (CollectionUtil.isEmpty(list)) {
            return Collections.emptyList();
        }
        return deptDao.buildTreeList(list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateRelations(SysDeptRelationDto dto) {
        if (dto.getType().equalsIgnoreCase("jobs") && CollectionUtil.isNotEmpty(dto.getJobIds())) {
            List<SysJob> jobs = new ArrayList<>();
            dto.getJobIds().forEach(i -> {
                SysJob job = jobDao.getById(i);
                job.setDeptId(dto.getId());
                jobs.add(job);
            });
            return jobDao.updateBatchById(jobs);
        }

        if (dto.getType().equalsIgnoreCase("users") && CollectionUtil.isNotEmpty(dto.getUserIds())) {
            List<SysUser> users = new ArrayList<>();
            dto.getUserIds().forEach(i -> {
                SysUser user = userDao.getById(i);
                user.setDeptId(dto.getId());
                users.add(user);
            });
            return userDao.updateBatchById(users);
        }

        return true;
    }

    @Override
    public Set<Long> getChildrenDeptIds(Long id) {
        if (id == null) {
            return new HashSet<>();
        }

        SysDeptDto sysDeptDto = pojoById(id);
        if (sysDeptDto == null) {
            return new HashSet<>();
        }

        SysDeptQuery query = new SysDeptQuery();
        query.setPid(id);
        List<SysDeptDto> children = pojoListByQuery(query);
        if (CollectionUtil.isEmpty(children)) {
            return new HashSet<>();
        }
        return children.stream()
                       .filter(Objects::nonNull)
                       .map(SysDeptDto::getId)
                       .collect(Collectors.toSet());
    }

    private SysDeptDto doToDto(SysDept model) {
        if (model == null) {
            return null;
        }

        SysDeptDto dto = BeanUtil.toBean(model, SysDeptDto.class);
        dto.setLabel(dto.getName());
        return dto;
    }

    private SysDept dtoToDo(SysDeptDto dto) {
        if (dto == null) {
            return null;
        }

        return BeanUtil.toBean(dto, SysDept.class);
    }

}
