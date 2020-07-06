package com.jw.common.constant.state;

/**
 * 菜单的状态
 *
 * @author fengshuonan
 * @Date 2017年1月22日 下午12:14:59
 */
public enum CommisionSettingStatus {

    ENABLE(0, "已生效"),
    ENABLE1(1, "待生效"),
    ENABLE2(2, "待修改"),
    ENABLE3(3, "待删除"),
    ENABLE4(4, "已失效");

    int code;
    String message;

    CommisionSettingStatus(int code, String message) {
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
            for (CommisionSettingStatus s : CommisionSettingStatus.values()) {
                if (s.getCode() == status) {
                    return s.getMessage();
                }
            }
            return "";
        }
    }
}
