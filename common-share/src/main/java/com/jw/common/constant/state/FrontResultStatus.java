package com.jw.common.constant.state;

/**
 * Created by huangfeng on 2018/4/15.
 */
public enum FrontResultStatus {

    SUCCESS("0","成功"),
    Fail("1","失败"),
    Error("-1","异常");


    String code;
    String message;

    FrontResultStatus(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static String valueOf1(String status) {
        if (status == null) {
            return "";
        } else {
            for (FrontResultStatus s : FrontResultStatus.values()) {
                if (s.getCode().equals(status)) {
                    return s.getMessage();
                }
            }
            return "";
        }
    }
}
