package io.github.dunwu.modules.monitor.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import io.github.dunwu.data.mybatis.ServiceImpl;
import io.github.dunwu.modules.monitor.annotation.AppLog;
import io.github.dunwu.modules.monitor.dao.SysLogDao;
import io.github.dunwu.modules.monitor.entity.SysLog;
import io.github.dunwu.modules.monitor.entity.dto.SysLogDto;
import io.github.dunwu.modules.monitor.entity.query.SysLogQuery;
import io.github.dunwu.modules.monitor.service.SysLogService;
import io.github.dunwu.util.net.IpUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import javax.servlet.http.HttpServletResponse;

/**
 * 系统日志 Service 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-03-10
 */
@Service
public class SysLogServiceImpl extends ServiceImpl implements SysLogService {

    private final SysLogDao dao;

    public SysLogServiceImpl(SysLogDao dao) {
        this.dao = dao;
    }

    @Override
    public boolean save(SysLog entity) {
        return dao.save(entity);
    }

    @Override
    public boolean saveBatch(Collection<SysLog> list) {
        return dao.saveBatch(list);
    }

    @Override
    public boolean updateById(SysLog entity) {
        return dao.updateById(entity);
    }

    @Override
    public boolean updateBatchById(Collection<SysLog> list) {
        return dao.updateBatchById(list);
    }

    @Override
    public boolean removeById(Serializable id) {
        return dao.removeById(id);
    }

    @Override
    public boolean removeByIds(Collection<? extends Serializable> ids) {
        return dao.removeByIds(ids);
    }

    @Override
    public List<SysLogDto> pojoListByQuery(SysLogQuery query) {
        return dao.pojoListByQuery(query, this::doToDto);
    }

    @Override
    public Page<SysLogDto> pojoPageByQuery(SysLogQuery query, Pageable pageable) {
        return dao.pojoPageByQuery(query, pageable, this::doToDto);
    }

    @Override
    public SysLogDto pojoById(Serializable id) {
        return dao.pojoById(id, this::doToDto);
    }

    @Override
    public SysLogDto pojoByQuery(SysLogQuery query) {
        return dao.pojoByQuery(query, this::doToDto);
    }

    @Override
    public Integer countByQuery(SysLogQuery query) {
        return dao.countByQuery(query);
    }

    @Override
    public void exportList(Collection<? extends Serializable> ids, HttpServletResponse response) throws IOException {
        List<SysLogDto> list = dao.pojoListByIds(ids, this::doToDto);
        dao.exportDtoList(list, response);
    }

    @Override
    public void exportPage(SysLogQuery query, Pageable pageable, HttpServletResponse response) throws IOException {
        Page<SysLogDto> page = dao.pojoPageByQuery(query, pageable, this::doToDto);
        dao.exportDtoList(page.getContent(), response);
    }

    @Override
    public SysLogDto doToDto(SysLog model) {
        if (model == null) {
            return null;
        }

        return BeanUtil.toBean(model, SysLogDto.class);
    }

    @Override
    public SysLog dtoToDo(SysLogDto dto) {
        if (dto == null) {
            return null;
        }

        return BeanUtil.toBean(dto, SysLog.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(String username, String browser, String ip, ProceedingJoinPoint joinPoint, SysLog log) {

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        AppLog aopLog = method.getAnnotation(AppLog.class);

        // 方法路径
        String methodName = joinPoint.getTarget().getClass().getName() + "." + signature.getName() + "()";

        // 描述
        if (log != null) {
            log.setDescription(aopLog.value());
        }
        assert log != null;
        log.setRequestIp(ip);

        String address = IpUtil.getRegionName(log.getRequestIp());
        log.setRequestLocation(address);
        log.setMethod(methodName);
        log.setUsername(username);
        log.setParams(getParameter(method, joinPoint.getArgs()));
        log.setRequestBrowser(browser);
        dao.save(log);
    }

    /**
     * 根据方法和传入的参数获取请求参数
     */
    private String getParameter(Method method, Object[] args) {
        List<Object> argList = new ArrayList<>();
        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            //将RequestBody注解修饰的参数作为请求参数
            RequestBody requestBody = parameters[i].getAnnotation(RequestBody.class);
            if (requestBody != null) {
                argList.add(args[i]);
            }
            //将RequestParam注解修饰的参数作为请求参数
            RequestParam requestParam = parameters[i].getAnnotation(RequestParam.class);
            if (requestParam != null) {
                Map<String, Object> map = new HashMap<>();
                String key = parameters[i].getName();
                if (!StrUtil.isEmpty(requestParam.value())) {
                    key = requestParam.value();
                }
                map.put(key, args[i]);
                argList.add(map);
            }
        }
        if (argList.size() == 0) {
            return "";
        }
        return argList.size() == 1 ? JSONUtil.toJsonStr(argList.get(0)) : JSONUtil.toJsonStr(argList);
    }

}
