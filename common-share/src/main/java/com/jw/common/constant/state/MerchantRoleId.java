package com.jw.common.constant.state;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by huangfeng on 2018/1/8.
 */
public enum MerchantRoleId {

    Administrator(1,"系统管理员"),
    TouristAdministration(3,"旅游局管理员"),
    Merchant(2, "商户管理员");

    Integer code;
    String message;

    MerchantRoleId(Integer code, String message) {
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

    public static String valueOf(Integer account) {
        if (account == null) {
            return "";
        } else {
            for (MerchantRoleId s : MerchantRoleId.values()) {
                if (s.getCode() == account) {
                    return s.getMessage();
                }
            }
            return "";
        }
    }
    public static void main(String[]args){
        System.out.println(StringUtils.isNotEmpty("a"));
    }
}
