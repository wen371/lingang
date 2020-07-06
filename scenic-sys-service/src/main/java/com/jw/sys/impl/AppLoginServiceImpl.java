package com.jw.sys.impl;

import com.github.kevinsawicki.http.HttpRequest;
import com.jw.common.support.HashKit;
import com.jw.sys.api.IAppLoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.net.URLEncoder;

/**
 * Created with com.softvan.baos.admin.service.impl.
 * User: q
 * Date: 2019/4/9
 */
@Service("appLoginService")
@Slf4j
public class AppLoginServiceImpl implements IAppLoginService {
    public static String SMS_CODE_PREFIX = "SMS_CODE_";

//    @Value("${sms.url}")
//    private String smsUrl;
//    @Value("${sms.encode}")
//    private String smsEncode;
//    @Value("${sms.userName}")
//    private String smsUserName;
//    @Value("${sms.password}")
//    private String smsPassword;
//    @Value("${sms.apiKey}")
//    private String smsApiKey;

    @Resource
    private CodeCacheService codeCacheService;

    @Override
    public String sendSms(String phone) {
        if("success".equals(codeCacheService.getInterval("SMS_"+phone))){
            return "发送频率过高，请稍后再试！";
        }
        String smsUrl = "http://m.5c.com.cn/api/send/index.php";
        String smsEncode = "utf-8";
        String smsUserName = "lywlx";
        String smsPassword = "ASD12345";
        String smsApiKey = "8ab8fe2ba2716718e1387f3f9e6b9e15";

        String url = smsUrl;
        String passwordMD5 = HashKit.md5(smsPassword);
        // 生成验证码
        int random=(int)(Math.random()*1000000);

        // 生成内容
        String sendContent = "您正在进行手机号登陆操作，验证码为："+random+"【上海旅游网】";
        // 发送短信
        if(passwordMD5 != null){
            try {
                url = url.concat("?username=").concat(smsUserName).concat("&password_md5=").concat(passwordMD5)
                        .concat("&mobile=").concat(phone).concat("&apikey=").concat(smsApiKey).concat("&content=")
                        .concat(URLEncoder.encode(sendContent, "UTF-8")).concat("&encode=").concat(smsEncode);
                HttpRequest request = HttpRequest.post(url);
                request.header("Connection", "Keep-Alive");
                String result = request.body();
                if(result != null && result.contains("success")) {
                    // 发送成功，加入定时
                    codeCacheService.setInterval("SMS_" + phone, "success");
                    // 存储验证码
                    codeCacheService.set(SMS_CODE_PREFIX.concat(phone), String.valueOf(random));
                    return  "发送成功";
                }
            }catch (Exception e){
                log.error("短信发送失败", e);
            }
        }
        return "发送信息失败";
    }

    @Override
    public boolean isSendCode(String phone, String sendCode) {
        String cacheCode = codeCacheService.get(SMS_CODE_PREFIX.concat(phone));
        if(cacheCode != null && cacheCode.equals(sendCode)){
            return true;
        }
        return false;
    }

}
