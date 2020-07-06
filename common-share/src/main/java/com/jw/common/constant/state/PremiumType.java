package com.jw.common.constant.state;

/**
 * Created by huangfeng on 2018/5/9.
 */
public enum PremiumType {

    STATUS_ZERO(0, "减少"),
    STATUS_ONE(1, "不变"),
    STATUS_TWO(2, "增加");

    Integer code;
    String message;

    PremiumType(Integer code, String message) {
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
            for (PremiumType s : PremiumType.values()) {
                if (s.getCode() == status) {
                    return s.getMessage();
                }
            }
            return "";
        }
    }
}
