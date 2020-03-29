package ${basePackage}.core;

import com.alibaba.fastjson.JSON;

/**
 * 统一API响应结果封装
 */
public class Result {
    private static final String DEFAULT_SUCCESS_MESSAGE = "success";
    private static final String DEFAULT_FAIL_MESSAGE = "fail";

    public static Result SUCCESS = new Result()
            .setCode(ResultCode.SUCCESS)
            .setMessage(DEFAULT_SUCCESS_MESSAGE);
    public static Result FAIL = new Result()
            .setCode(ResultCode.FAIL)
            .setMessage(DEFAULT_FAIL_MESSAGE);
    private int code;
    private String message;
    private Object data;


    public static Result genSuccessResult(Object data) {
        return new Result()
                .setCode(ResultCode.SUCCESS)
                .setMessage(DEFAULT_SUCCESS_MESSAGE)
                .setData(data);
    }

    public static Result genFailResult(String message) {
        return new Result()
                .setCode(ResultCode.FAIL)
                .setMessage(message);
    }


    public Result setCode(ResultCode resultCode) {
        this.code = resultCode.code();
        return this;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Result setMessage(String message) {
        this.message = message;
        return this;
    }

    public Object getData() {
        return data;
    }

    public Result setData(Object data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
