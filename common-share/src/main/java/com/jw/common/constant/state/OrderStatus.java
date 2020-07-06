package com.jw.common.constant.state;

/**
 * Created by huangfeng on 2018/4/15.
 */
public enum OrderStatus {

    STATUS_ZERO("0","待核销"),
    STATUS_ONE("1","已使用"),
    STATUS_TWO("2","部分使用"),
    STATUS_THREE("3","已退款");


    String code;
    String message;

    OrderStatus(String code, String message) {
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
            for (OrderStatus s : OrderStatus.values()) {
                if (s.getCode().equals(status)) {
                    return s.getMessage();
                }
            }
            return "";
        }
    }
}
