package com.jw.common.constant.state;

/**
 * 菜单的状态
 *
 * @author fengshuonan
 * @Date 2017年1月22日 下午12:14:59
 */
public enum ProductStatus {

    ENABLE(0, "编辑中"),
    ENABLE1(1, "已生效"),
    ENABLE2(2, "已失效"),
    ENABLE3(3, "已完成");
    int code;
    String message;

    ProductStatus(int code, String message) {
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
            for (ProductStatus s : ProductStatus.values()) {
                if (s.getCode() == status) {
                    return s.getMessage();
                }
            }
            return "";
        }
    }
}
