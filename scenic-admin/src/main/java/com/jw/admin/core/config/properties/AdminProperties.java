package com.jw.admin.core.config.properties;

import com.jw.common.support.ToolUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import java.io.File;

import static com.jw.common.support.ToolUtil.getTempPath;


/**
 * 项目配置
 *
 * @author stylefeng
 * @Date 2017/5/23 22:31
 */
@Component
@ConfigurationProperties(prefix = AdminProperties.PREFIX)
public class AdminProperties {

    public static final String PREFIX = "admin";

    private Boolean kaptchaOpen = false;

    private Boolean swaggerOpen = false;

    @Value("${https.url}")
    private String httpsUrl;

    @Value("${fileurl.uploadurl}")
    private String fileUploadPath;

    @Value("${fileurl.ueditorUrl}")
    private String ueditorUrl;

    private Boolean haveCreatePath = false;

    private Boolean springSessionOpen = true;

    private Integer sessionInvalidateTime = 30 * 60;  //session 失效时间（默认为30分钟 单位：秒）

    private Integer sessionValidationInterval = 15 * 60;  //session 验证失效时间（默认为15分钟 单位：秒）

    public String getFileUploadPath() {
        //如果没有写文件上传路径,保存到临时目录
        if (ToolUtil.isEmpty(fileUploadPath)) {
            return getTempPath();
        } else {
            //判断目录存不存在,不存在得加上
            if (haveCreatePath == false) {
                File file = new File(fileUploadPath);
                file.mkdirs();
                haveCreatePath = true;
            }
            return fileUploadPath;
        }
    }

    public String getUeditorUrl() {
        return ueditorUrl;
    }

    public void setUeditorUrl(String ueditorUrl) {
        this.ueditorUrl = ueditorUrl;
    }

    public Boolean getHaveCreatePath() {
        return haveCreatePath;
    }

    public void setHaveCreatePath(Boolean haveCreatePath) {
        this.haveCreatePath = haveCreatePath;
    }

    public void setFileUploadPath(String fileUploadPath) {
        this.fileUploadPath = fileUploadPath;
    }

    public Boolean getKaptchaOpen() {
        return kaptchaOpen;
    }

    public void setKaptchaOpen(Boolean kaptchaOpen) {
        this.kaptchaOpen = kaptchaOpen;
    }

    public Boolean getSwaggerOpen() {
        return swaggerOpen;
    }

    public void setSwaggerOpen(Boolean swaggerOpen) {
        this.swaggerOpen = swaggerOpen;
    }

    public Boolean getSpringSessionOpen() {
        return springSessionOpen;
    }

    public void setSpringSessionOpen(Boolean springSessionOpen) {
        this.springSessionOpen = springSessionOpen;
    }

    public Integer getSessionInvalidateTime() {
        return sessionInvalidateTime;
    }

    public void setSessionInvalidateTime(Integer sessionInvalidateTime) {
        this.sessionInvalidateTime = sessionInvalidateTime;
    }

    public Integer getSessionValidationInterval() {
        return sessionValidationInterval;
    }

    public void setSessionValidationInterval(Integer sessionValidationInterval) {
        this.sessionValidationInterval = sessionValidationInterval;
    }

    public String getHttpsUrl() {
        return httpsUrl;
    }

    public void setHttpsUrl(String httpsUrl) {
        this.httpsUrl = httpsUrl;
    }
}
