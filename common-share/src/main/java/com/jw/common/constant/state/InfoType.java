package com.jw.common.constant.state;


public enum InfoType {

    NR(0, "牛人信息"),
    YB(1, "约伴信息"),
    GF(2, "官方信息");

    int code;
    String message;

    InfoType(int code, String message) {
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
            for (InfoType s : InfoType.values()) {
                if (s.getCode() == status) {
                    return s.getMessage();
                }
            }
            return "";
        }
    }
}
