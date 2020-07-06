package com.jw.common.constant.state;

/**
 * 菜单的状态
 *
 * @author fengshuonan
 * @Date 2017年1月22日 下午12:14:59
 */
public enum ProductSaleType {

    ENABLE(0, "店长"),
    ENABLE1(1, "企业见费"),
    ENABLE2(2, "企业非见费");

    int code;
    String message;

    ProductSaleType(int code, String message) {
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
            for (ProductSaleType s : ProductSaleType.values()) {
                if (s.getCode() == status) {
                    return s.getMessage();
                }
            }
            return "";
        }
    }
}
