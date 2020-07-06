package com.jw.common.constant.state;

/**
 * Created by huangfeng on 2018/1/22.
 */
public enum ProductDeployStatus {

    ENABLE(0, "初始状态"),
    ENABLE1(1, "已上架"),
    ENABLE2(2, "已下架");

    int code;
    String message;

    ProductDeployStatus(int code, String message) {
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
            for (ProductDeployStatus s : ProductDeployStatus.values()) {
                if (s.getCode() == status) {
                    return s.getMessage();
                }
            }
            return "";
        }
    }
}
