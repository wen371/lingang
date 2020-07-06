package com.jw.common.constant.state;

/**
 * Created by huangfeng on 2018/5/13.
 */
public enum CorrectionType {

    TYPE_DEL(0, "批减"),
    TYPE_ADD(1, "批增");

    Integer code;
    String message;

    CorrectionType(Integer code, String message) {
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
            for (CorrectionType s : CorrectionType.values()) {
                if (s.getCode() == status) {
                    return s.getMessage();
                }
            }
            return "";
        }
    }
}
