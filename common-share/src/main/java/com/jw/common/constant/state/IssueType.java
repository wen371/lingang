package com.jw.common.constant.state;

/**
 * Created by huangfeng on 2018/1/22.
 */
public enum IssueType {

    WEB(0,"WEB出单"),
    WECHA(1,"微信出单");

    int code;
    String message;

    IssueType(int code, String message) {
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
            for (IssueType s : IssueType.values()) {
                if (s.getCode() == status) {
                    return s.getMessage();
                }
            }
            return "";
        }
    }


}
