package io.github.dunwu.modules.security.exception;

import io.github.dunwu.data.core.BaseResult;
import io.github.dunwu.data.core.constant.Status;

/**
 * 认证异常
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-04-11
 */
public class AuthException extends RuntimeException {

    private static final long serialVersionUID = -7027578114976830416L;

    private BaseResult result;

    public AuthException(String message) {
        super(message);
    }

    public AuthException(Throwable cause) {
        super(cause);
    }

    public AuthException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthException(BaseResult result) {
        super(result.getMessage());
        this.result = new BaseResult(result.getCode(), result.getMessage());
    }

    public AuthException(Status status) {
        super(status.getMessage());
        this.result = BaseResult.fail(status);
    }

    /**
     * 覆盖原方法，解决抓取堆性能开销
     */
    @Override
    public Throwable fillInStackTrace() {
        return this;
    }

    public BaseResult getResult() {
        return result;
    }

}
