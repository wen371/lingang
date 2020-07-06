package com.jw.admin.core.config.wechat;
import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties
@ConditionalOnClass(WxPayService.class)
@EnableConfigurationProperties(WechatMpProperties.class)
public class WechatPayConfiguration {
    @Autowired
    private WechatMpProperties wechatMpProperties;

    @Bean
    @ConditionalOnMissingBean
    public WxPayConfig config() {
        WxPayConfig payConfig = new WxPayConfig();
        payConfig.setAppId(wechatMpProperties.getAppId());
        payConfig.setMchId(wechatMpProperties.getMchId());
        payConfig.setMchKey(wechatMpProperties.getMchKey());
       // payConfig.setSubAppId(StringUtils.trimToNull(this.properties.getSubAppId()));
        //payConfig.setSubMchId(StringUtils.trimToNull(this.properties.getSubMchId()));
        payConfig.setKeyPath(wechatMpProperties.getKeyPath());

        return payConfig;
    }

    @Bean
    //@ConditionalOnMissingBean
    public WxPayService wxPayService(WxPayConfig payConfig) {
        WxPayService wxPayService = new WxPayServiceImpl();
        wxPayService.setConfig(payConfig);
        return wxPayService;
    }

}