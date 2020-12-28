package com.demo.station.config;

import com.demo.station.utils.ResultCode;
import lombok.extern.slf4j.Slf4j;

/**
 * @author : lipb
 * @date : 2020-12-28 18:21
 */
@Slf4j
public class BusinessException extends RuntimeException {


    protected final String message;
    protected final int code;

    public BusinessException(String message, int code) {
        this.message = message;
        this.code = code;
    }


    public BusinessException(String message) {
        this.message = message;
        log.error("异常抛出信息为："+super.fillInStackTrace());
        StackTraceElement[] st = super.getStackTrace();
        for (StackTraceElement stackTraceElement : st) {
            String exclass = stackTraceElement.getClassName();
            String method = stackTraceElement.getMethodName();
            log.error("[类:" + exclass + "]调用"
                    + method + "时在第" + stackTraceElement.getLineNumber()
                    + "行代码处发生异常!异常类型:" + super.getClass().getName());
        }
        this.code = ResultCode.PARAM_VALID_ERROR.getCode();
    }

    public BusinessException(String message, int code, Throwable e) {
        super(message, e);
        this.message = message;
        this.code = code;
        log.error("异常抛出信息为："+message);
    }


    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}