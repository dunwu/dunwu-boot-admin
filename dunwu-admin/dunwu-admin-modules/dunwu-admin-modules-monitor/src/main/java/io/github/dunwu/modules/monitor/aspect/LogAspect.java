package io.github.dunwu.modules.monitor.aspect;

import io.github.dunwu.modules.monitor.annotation.AppLog;
import io.github.dunwu.modules.monitor.entity.LogRecord;
import io.github.dunwu.modules.monitor.service.LogService;
import io.github.dunwu.util.RequestHolder;
import io.github.dunwu.util.SecurityUtils;
import io.github.dunwu.util.ThrowableUtil;
import io.github.dunwu.web.util.ServletUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * {@link AppLog} 注解的处理器
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-04-09
 */
@Slf4j
@Aspect
@Component
public class LogAspect {

    private final LogService logService;
    private final ThreadLocal<Long> currentTime = new ThreadLocal<>();

    public LogAspect(LogService logService) {
        this.logService = logService;
    }

    /**
     * AOP 切点
     */
    @Pointcut("@annotation(io.github.dunwu.modules.monitor.annotation.AppLog)")
    public void pointcut() { }

    /**
     * 配置环绕通知，使用在方法 {@link #pointcut()} 上注册的切入点
     *
     * @param joinPoint join point for advice
     */
    @Around("pointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result;
        currentTime.set(System.currentTimeMillis());
        result = joinPoint.proceed();
        LogRecord log = new LogRecord("INFO", System.currentTimeMillis() - currentTime.get());
        currentTime.remove();
        HttpServletRequest request = RequestHolder.getHttpServletRequest();
        ServletUtil.RequestIdentityInfo requestIdentityInfo = ServletUtil.getRequestIdentityInfo(request);
        logService.save(SecurityUtils.getCurrentUsername(), requestIdentityInfo.getBrowser(),
            requestIdentityInfo.getIp(), joinPoint, log);
        return result;
    }

    /**
     * 配置异常通知
     *
     * @param joinPoint join point for advice
     * @param e         exception
     */
    @AfterThrowing(pointcut = "pointcut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        LogRecord log = new LogRecord("ERROR", System.currentTimeMillis() - currentTime.get());
        currentTime.remove();
        log.setExceptionDetail(ThrowableUtil.getStackTrace(e).getBytes());
        HttpServletRequest request = RequestHolder.getHttpServletRequest();
        ServletUtil.RequestIdentityInfo requestIdentityInfo = ServletUtil.getRequestIdentityInfo(request);
        logService.save(SecurityUtils.getCurrentUsername(), requestIdentityInfo.getBrowser(),
            requestIdentityInfo.getIp(), (ProceedingJoinPoint) joinPoint, log);
    }

}
