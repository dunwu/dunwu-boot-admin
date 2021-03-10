package io.github.dunwu;

import cn.hutool.core.util.StrUtil;
import com.google.common.net.HttpHeaders;
import io.github.dunwu.data.core.DataException;
import io.github.dunwu.data.core.Result;
import io.github.dunwu.data.core.constant.ResultStatus;
import io.github.dunwu.util.ThrowableUtil;
import io.github.dunwu.web.constant.WebConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.ui.Model;
import org.springframework.util.MimeTypeUtils;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;

/**
 * 系统全局异常统一处理接口
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @see <a href= "https://blog.csdn.net/hao_kkkkk/article/details/80538955">Springboot项目全局异常统一处理</a>
 * @since 2019-09-11
 */
@ControllerAdvice
public class RequestGlobalHandler {

    public static final String DEFAULT_APP = "dunwu";

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * 把值绑定到Model中，使全局@RequestMapping可以获取到该值
     */
    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("app", DEFAULT_APP);
    }

    /**
     * 统一处理请求参数校验异常(普通传参)
     *
     * @param e ConstraintViolationException
     * @return {@link Result}
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ ConstraintViolationException.class })
    public Result handleConstraintViolationException(final ConstraintViolationException e) {
        log.error("ConstraintViolationException", e);
        StringBuilder sb = new StringBuilder();
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        for (ConstraintViolation<?> violation : violations) {
            Path path = violation.getPropertyPath();
            String[] pathArr = StrUtil.split(path.toString(), ".");
            sb.append(pathArr[1]).append(violation.getMessage()).append(",");
        }
        sb = new StringBuilder(sb.substring(0, sb.length() - 1));
        return Result.fail(ResultStatus.HTTP_BAD_REQUEST.getCode(), sb.toString());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ HttpClientErrorException.class })
    public Result handleBadRequestException(final HttpClientErrorException e) {
        log.error("HttpClientErrorException", e);
        return Result.fail(ResultStatus.HTTP_BAD_REQUEST.getCode(), e.getLocalizedMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ DataException.class })
    public Result handleDataException(final DataException e) {
        log.error("DataException", e);
        return Result.fail(ResultStatus.DATA_ERROR.getCode(), e.getLocalizedMessage());
    }

    /**
     * 处理参数校验异常
     *
     * @param e MethodArgumentNotValidException
     * @return {@link Result}
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ MethodArgumentNotValidException.class })
    private Result handleMethodArgumentNotValidException(final MethodArgumentNotValidException e) {
        log.error("MethodArgumentNotValidException", e);
        StringBuilder sb = new StringBuilder();
        sb.append("参数错误：\n");
        for (ObjectError error : e.getBindingResult().getAllErrors()) {
            sb.append(((FieldError) error).getField() + " ");
            sb.append(error.getDefaultMessage());
            sb.append("\n");
        }
        return Result.fail(ResultStatus.SYSTEM_ERROR_PARAM.getCode(), sb.toString());
    }

    // ------------------------------------------------------------------------------
    // 认证、授权异常
    // ------------------------------------------------------------------------------

    /**
     * 处理认证异常
     *
     * @param e AuthenticationException
     * @return {@link Result}
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthenticationException.class)
    public Result handleAuthenticationException(final AuthenticationException e) {
        log.error("Exception: {}, message: {}", e.getClass().getCanonicalName(), e.getLocalizedMessage());
        return Result.fail(ResultStatus.HTTP_UNAUTHORIZED.getCode(), e.getLocalizedMessage());
    }

    /**
     * 处理未授权异常（登录状态下，无权限会触发）
     *
     * @param e AccessDeniedException
     * @return {@link Result}
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AccessDeniedException.class)
    public Result handleAccessDeniedException(final AccessDeniedException e) {
        log.error("Exception: {}, message: {}", e.getClass().getCanonicalName(), e.getLocalizedMessage());
        return Result.fail(ResultStatus.HTTP_UNAUTHORIZED.getCode(), e.getLocalizedMessage());
    }

    /**
     * 处理所有不可知的异常
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Throwable.class)
    public Result handleException(Throwable e) {
        log.error(ThrowableUtil.getStackTrace(e));
        return Result.fail(ResultStatus.HTTP_SERVER_ERROR.getCode(), e.getMessage());
    }

    /**
     * BadCredentialsException
     */
    @ExceptionHandler(BadCredentialsException.class)
    public Result badCredentialsException(BadCredentialsException e) {
        String message = "坏的凭证".equals(e.getMessage()) ? "用户名或密码不正确" : e.getMessage();
        log.error(message);
        return Result.fail(ResultStatus.HTTP_UNAUTHORIZED.getCode(), message);
    }

    private WebConstant.ResponseType getResponseMode(HttpServletRequest request) {
        String contentType = request.getHeader(HttpHeaders.CONTENT_TYPE);
        String accept = request.getHeader(HttpHeaders.ACCEPT);
        String xRequestedWith = request.getHeader(HttpHeaders.X_REQUESTED_WITH);

        if (StrUtil.isNotBlank(contentType)
            && contentType.contains(MimeTypeUtils.APPLICATION_JSON_VALUE)) {
            return WebConstant.ResponseType.HTTP_REPONSE;
        }

        if (StrUtil.isNotBlank(accept)
            && accept.contains(MimeTypeUtils.APPLICATION_JSON_VALUE)) {
            return WebConstant.ResponseType.HTTP_REPONSE;
        }

        if (StrUtil.isNotBlank(xRequestedWith)
            && HttpHeaders.X_REQUESTED_WITH.equalsIgnoreCase(xRequestedWith)) {
            return WebConstant.ResponseType.HTTP_REPONSE;
        }

        return WebConstant.ResponseType.MODEL_AND_VIEW;
    }

}
