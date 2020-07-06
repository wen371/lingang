package com.jw.common.constant.state;

/**
 * Created by huangfeng on 2018/3/18.
 */
public enum AgeUnit {

    YEAR(1, "周岁"),
    DAY(0, "天");

    Integer code;
    String message;

    AgeUnit(Integer code, String message) {
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

    public static String valueOf(Integer unit) {
        if (unit == null) {
            return "";
        } else {
            for (AgeUnit s : AgeUnit.values()) {
                if (s.getCode() == unit) {
                    return s.getMessage();
                }
            }
            return "";
        }
    }
}
