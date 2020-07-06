package com.jw.common.constant.state;

public enum  SexName {

    ALL(0, "不区分男女"),
    MAN(1, "男"),
    WOMAN(2, "女");

    int code;
    String message;

    SexName(int code, String message) {
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
            for (SexName s : SexName.values()) {
                if (s.getCode() == status) {
                    return s.getMessage();
                }
            }
            return "";
        }
    }
}
