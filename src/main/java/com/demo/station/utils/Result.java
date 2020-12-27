package com.demo.station.utils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.lang.Nullable;
import org.springframework.util.ObjectUtils;

import java.io.Serializable;
import java.util.Optional;

/**
 * @Author lipb
 **/
@ApiModel(
        description = "返回信息"
)
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(
            value = "状态码",
            required = true
    )
    private int code;
    @ApiModelProperty(
            value = "是否成功",
            required = true
    )
    private boolean success;
    @ApiModelProperty("承载数据")
    private T data;
    @ApiModelProperty(
            value = "返回消息",
            required = true
    )
    private String message;

    private Result(IResultCode resultCode) {
        this(resultCode, null, resultCode.getMessage());
    }

    private Result(IResultCode resultCode, String message) {
        this(resultCode, null, message);
    }

    private Result(IResultCode resultCode, T data) {
        this(resultCode, data, resultCode.getMessage());
    }

    private Result(IResultCode resultCode, T data, String message) {
        this(resultCode.getCode(), data, message);
    }

    private Result(int code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
        this.success = ResultCode.SUCCESS.code == code;
    }

    public static boolean isSuccess(@Nullable Result<?> result) {
        return (Boolean) Optional.ofNullable(result).map((x) -> {
            return ObjectUtils.nullSafeEquals(ResultCode.SUCCESS.code, x.code);
        }).orElse(Boolean.FALSE);
    }

    public static boolean isNotSuccess(@Nullable Result<?> result) {
        return !isSuccess(result);
    }

    public static <T> Result<T> data(T data) {
        return data(data, "操作成功");
    }

    public static <T> Result<T> data(T data, String message) {
        return data(200, data, message);
    }

    public static <T> Result<T> data(int code, T data, String message) {
        return new Result(code, data, data == null ? "暂无承载数据" : message);
    }

    public static <T> Result<T> success(String message) {
        return new Result(ResultCode.SUCCESS, message);
    }

    public static <T> Result<T> success(IResultCode resultCode) {
        return new Result(resultCode);
    }

    public static <T> Result<T> success(IResultCode resultCode, String message) {
        return new Result(resultCode, message);
    }

    public static <T> Result<T> fail(String message) {
        return new Result(ResultCode.FAILURE, message);
    }

    public static <T> Result<T> fail(int code, String message) {
        return new Result(code, (Object)null, message);
    }

    public static <T> Result<T> fail(IResultCode resultCode) {
        return new Result(resultCode);
    }

    public static <T> Result<T> fail(IResultCode resultCode, String message) {
        return new Result(resultCode, message);
    }

    public static <T> Result<T> status(boolean flag) {
        return flag ? success("操作成功") : fail("操作失败");
    }

    public int getCode() {
        return this.code;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public T getData() {
        return this.data;
    }

    public String getMessage() {
        return this.message;
    }

    public void setCode(final int code) {
        this.code = code;
    }

    public void setSuccess(final boolean success) {
        this.success = success;
    }

    public void setData(final T data) {
        this.data = data;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public String toString() {
        return "R(code=" + this.getCode() + ", success=" + this.isSuccess() + ", data=" + this.getData() + ", message=" + this.getMessage() + ")";
    }

    public Result() {
    }
}