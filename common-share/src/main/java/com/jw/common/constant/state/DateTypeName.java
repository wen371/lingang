package com.jw.common.constant.state;

/**
 * Created by huangfeng on 2018/1/14.
 */
public enum DateTypeName {

    TYPE_DAY(0, "日"),
    TYPE_MONTH(1, "月"),
    TYPE_YEAR(2, "年");

    int code;
    String message;

    DateTypeName(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
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
            for (DateTypeName s : DateTypeName.values()) {
                if (s.getCode() == status) {
                    return s.getMessage();
                }
            }
            return "";
        }
    }

}
