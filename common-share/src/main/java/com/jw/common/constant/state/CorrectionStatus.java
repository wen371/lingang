package com.jw.common.constant.state;

/**
 * Created by huangfeng on 2018/5/9.
 */
public enum CorrectionStatus {

    STATUS_ZERO(0, "初始状态"),
    STATUS_ONE(1, "批改成功（待支付）"),
    STATUS_TWO(2, "批改成功（支付完成）");

    Integer code;
    String message;

    CorrectionStatus(Integer code, String message) {
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
            for (CorrectionStatus s : CorrectionStatus.values()) {
                if (s.getCode() == status) {
                    return s.getMessage();
                }
            }
            return "";
        }
    }
}
