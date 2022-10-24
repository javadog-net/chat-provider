package net.javadog.chat.common.base.exception;

import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import net.javadog.chat.common.base.response.ResponseData;
import net.javadog.chat.common.enums.HttpCodeEnum;
import net.javadog.chat.common.exception.ServiceException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.UnexpectedTypeException;

/**
 * @Description: 全局异常
 * @Author: hdx
 * @Date: 2022/1/13 15:46
 * @Version: 1.0
 */
@Slf4j
@ControllerAdvice
public class ExceptionController {

    /**
     * 业务异常处理
     */
    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public ResponseData<String> chatException(ServiceException ex) {
        log.error(Throwables.getStackTraceAsString(ex));
        return ResponseData.error(ex.getErrorCode(), ex.getMessage());
    }

    /**
     * IllegalStateException异常处理
     */
    @ExceptionHandler(IllegalStateException.class)
    @ResponseBody
    public ResponseData<String> illegalStateException(IllegalStateException ex) {
        log.error(Throwables.getStackTraceAsString(ex));
        return ResponseData.error(HttpCodeEnum.SERVER_BUSY);
    }


    /**
     * 方法参数无效异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseData<String> methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        if (ex.hasErrors()) {
            ex.getAllErrors().forEach(error -> log.error(error.getDefaultMessage()));
        }
        return ResponseData.error(HttpCodeEnum.BODY_NOT_MATCH).setMessage(ex.getAllErrors().get(0).getDefaultMessage());
    }

    /**
     * 参数异常处理
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public ResponseData<String> illegalArgumentException(IllegalArgumentException ex) {
        log.error(Throwables.getStackTraceAsString(ex));
        return ResponseData.error(HttpCodeEnum.BODY_NOT_MATCH).setMessage(ex.getMessage());
    }

    /**
     * 405错误异常处理
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public ResponseData<String> httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        log.error(Throwables.getStackTraceAsString(ex));
        return ResponseData.error(HttpCodeEnum.METHOD_NOT_ALLOWED);
    }

    /**
     * 数据验证异常处理
     */
    @ExceptionHandler(UnexpectedTypeException.class)
    @ResponseBody
    public ResponseData<String> unexpectedTypeException(UnexpectedTypeException ex) {
        String errMsg = Throwables.getStackTraceAsString(ex);
        log.error(errMsg);
        return ResponseData.error(HttpCodeEnum.INTERNAL_SERVER_ERROR).setMessage(errMsg);
    }

    /**
     * 未处理的异常处理
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseData<String> exception(Exception ex) {
        String errMsg = Throwables.getStackTraceAsString(ex);
        log.error(errMsg);
        return ResponseData.error(HttpCodeEnum.INTERNAL_SERVER_ERROR).setMessage(ex.getCause().getMessage());
    }

}
