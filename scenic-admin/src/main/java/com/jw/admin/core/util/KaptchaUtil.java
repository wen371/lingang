package com.jw.admin.core.util;


import com.jw.admin.core.config.properties.AdminProperties;
import com.jw.common.support.SpringContextHolder;

/**
 * 验证码工具类
 */
public class KaptchaUtil {

    /**
     * 获取验证码开关
     *
     * @author stylefeng
     * @Date 2017/5/23 22:34
     */
    public static Boolean getKaptchaOnOff() {
        return SpringContextHolder.getBean(AdminProperties.class).getKaptchaOpen();
    }
}