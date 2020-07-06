package com.jw.common.constant.state;

/**
 * Created by huangfeng on 2018/1/8.
 */
public enum Account {

    ENABLE(1, "企业"),
    DISABLE(0, "天安");

    Integer code;
    String message;

    Account(Integer code, String message) {
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

    public static String valueOf(Integer account) {
        if (account == null) {
            return "";
        } else {
            for (Account s : Account.values()) {
                if (s.getCode() == account) {
                    return s.getMessage();
                }
            }
            return "";
        }
    }
}
