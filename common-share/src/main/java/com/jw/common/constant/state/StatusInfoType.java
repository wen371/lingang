package com.jw.common.constant.state;


public enum StatusInfoType {
//（1：审核通过，2：审核不通过，3：待审核，4：撤销，5：用户删除）
    Pass(1, "审核通过"),
    NoPass(2, "审核不通过"),
    DaiPass(3, "待审核"),
    CeXiao(4, "撤销"),
    Delete(5, "用户删除");

    int code;
    String message;

    StatusInfoType(int code, String message) {
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
            for (StatusInfoType s : StatusInfoType.values()) {
                if (s.getCode() == status) {
                    return s.getMessage();
                }
            }
            return "";
        }
    }
}
