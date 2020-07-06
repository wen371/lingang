package com.jw.common.constant.state;

/**
 * Created by huangfeng on 2018/1/6.
 */
public enum Status {

    ENABLE(1, "无效"),
    DISABLE(0, "有效");

    Integer code;
    String message;

    Status(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static String valueOf(Integer status) {
        if (status == null) {
            return "";
        } else {
            for (Status s : Status.values()) {
                if (s.getCode() == status) {
                    return s.getMessage();
                }
            }
            return "";
        }
    }

}
