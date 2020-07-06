package com.jw.admin.core.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Data
@Component
@Configuration
public class AlipayConfig {
    // 商户appid
    @Value("${zfb.APPID}")
    private  String APPID ;
    // 私钥 pkcs8格式的
    @Value("${zfb.RSA_PRIVATE_KEY}")
    private  String RSA_PRIVATE_KEY ;
    // 服务器异步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    @Value("${zfb.notify_url}")
    private  String notify_url ;
    // 页面跳转同步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问 商户可以自定义同步跳转地址
    @Value("${zfb.return_url}")
    private  String return_url ;
    // 请求网关地址
    @Value("${zfb.gateway_url}")
    private  String URL ;
    // 编码
    @Value("${zfb.CHARSET}")
    private  String CHARSET ;
    // 返回格式
    private  String FORMAT = "json";
    // 支付宝公钥
    @Value("${zfb.ALIPAY_PUBLIC_KEY}")
    private  String ALIPAY_PUBLIC_KEY ;
    // 日志记录目录
    public static  String log_path = "/log";
    // RSA2
    @Value("${zfb.SIGNTYPE}")
    private  String SIGNTYPE ;
    //卖家商户id
    @Value("${zfb.seller_id}")
    private  String seller_id ;
    //非生产环境支付0.01元
    @Value("${zfb.payFlag}")
    private boolean payFlag;
}
