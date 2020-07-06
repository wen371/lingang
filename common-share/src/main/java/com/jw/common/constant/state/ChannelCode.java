package com.jw.common.constant.state;

/**
 * Created by huangfeng on 2018/1/22.
 */
public enum ChannelCode {

    CHANNEL_CS("01","直管团队"),
    CHANNEL_BY("02","车商"),
    CHANNEL_DX("03","重客与经纪"),
    CHANNEL_QT("04","银保"),
    CHANNEL_DZ("05","电子商务");

    String code;
    String message;

    ChannelCode(String code, String message) {
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
        if (status == null||"".equals(status)) {
            return "";
        } else {
            for (ChannelCode s : ChannelCode.values()) {
                if (s.getCode().equals(status)) {
                    return s.getMessage();
                }
            }
            return "";
        }
    }

}
