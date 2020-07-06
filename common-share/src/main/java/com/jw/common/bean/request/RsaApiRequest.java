package com.jw.common.bean.request;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

@Slf4j
@Component
public class RsaApiRequest {
    @Value("${METRO.PRIVATE_KEY}")
    private  String PRIVATE_KEY;// = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCMYww8TvXwnPR8YOSx4cqwznO+Nr9b4rwlXZVGtgROS4qQLcBG1z/9YgeM01gsHP66j7iuGB3OvrzuJ1uoHQcZ0wA3++PaS6vEkuM7U1YwJiIzv0RUB2Xi87q69oCggsdjMQGS5AD2a3s8p4ifQulGgg5Thlht/zaVKm9bu6ivinlKWrC3B36LInHnJ1y0RxRZHSAQumdPPO0hUoWtR3T+/ofFgOW7bEaxg6BQBg/HaYwcIG+BVwsj2yO6Q9jirBpQoM518ntvVX4WiUK6P26N4T3IT+dxbw2SUZ5nJ35LPjWDzRUk+YcmnMlos6Zkz9OMnYemfkG2mpQNX8BYBeitAgMBAAECggEAfJzK1pmMS2k2x1TGi3MOFQUcziIQmAJZ2Z0JDTwx9EGvnC1QOFGGtmaWpxVOddHC9lzupHI5uzOpxoffHwmtLOoTjp4jcbirnpB37UgbACBJSb+kaiYq9Vo9TQnTwSZ00AJHEFkc1Z8wQ/W+Gwgswu5cwx1ET1cz3xJMyJN5t16VdPtwpHUgZx2thgMHsu/dDh5kKkIlNVhjK0sXoZ/uv9P3TGyeQFWPk0aKbMiYqL7ugJYZT0dqPrm9WdA1FJfr7lFY5EF6vjmU+Qu3NYHMLFtBP/ijvx+6/OuFAw5/1rA+HK6ECS2+/1x1NhNnCTLEoQI6xOI14Jiq0tKak2wbDQKBgQDDnqu7yJx1oaPw5EdqexR8TdcQwixQX/Rkl97SKd2CKdF3qrvcszdK/Zvcs8kAuYAZ6IjVDpiVJatdTSFmYRLOnsvwu+5PJX+tMMsIcGevV3Wd8SE27Xdp0wuuzQ+JNWEmenQvxyDDPC+cUnXPezSXf0To1+xJaMlbKdDLByCZBwKBgQC3uAcakPdwTyCQgwGu1y76AwL7y7wr25MUEN0deU2UiUmrzGcoQ4h+vNw0ckYOZIpzxBFexyVFeZX2cIZnjcnk84O/aYSF2iLUyg0RpJWg2Oij5+EF/H2fMaqSGTIfywVg5wcwpU9kr1CQmwmsasMuISC3Gj1ggPoew665lMuHqwKBgBlrUN2B3/LZIjh3MWCAxdSxOLUQxjlbfqJjqaydjA8R6AoowWiYUSMJeWtgYUoa0K6NLNqinukij/X7w89N5ee7crzBbuIR+VlD8d4S3SZHsxzm7G36xX2W0vnP46jsqscZOmfK9FLqSzWA8ZYYaGkXPJkVFbskRqX19Q+syWuZAoGAYcXgkfTgmrahwAbYmHIQoCGGLvcIV9bteVpYMt1hulEuEe4gYD9AnyJnlsnSGsrLHD0WeXhGj6jbdGICRY4iCmTGktHXI10p+FDnj6Ilt4p4lPJMwWDUgipuen5KPL1RSXbqTqtLi2v51luMVGQlJ6GdxZSDYZ6Cfmjpzf4wKy0CgYEAm43aIqW4hbn4EkcvFRtzZO/5Cr9oR5CGXRQ2sXqO0TQMAjnc1B2ZiGqkjaW3AfBd0d4qGEuBSRryZx1pSB5iKi1Cb4Ucdo7ZrDJpjztAB8HH9UiQpnUhW9E9QjmEqkQmBq1kyOiMzAOtwuQHptiK0SMwclcCFYMBITedAt5Ti9Y=";

    @Value("${METRO.APP_ID}")
    private  String APP_ID;// = "2019051149874243";

    @Value("${METRO.SIGN_TYPE}")
    private  String SIGN_TYPE_1;// ="RSA";

    @Value("${METRO.URL}")
    private  String URL;// = "https://outlet.metrofastpass.com/gateway.do";

    /**
     * 取消优惠券
     * @throws Exception
     */
    public String cancel(String couponCode,String metroUid,String startTime) throws Exception {
        JSONObject json = new JSONObject();
        //优惠券编号
        json.put("business_id",couponCode);
        //大都会用户id
        json.put("metro_uid",metroUid);
        json.put("start_time",startTime);
        String apiMethod = "bangdao.duchang.facade.prize.api.rsa.cancelPrize";
        return rsaRequest(json,apiMethod);
    }

    /**
     * 发送优惠券
     * @throws Exception
     */
    public String send(String metroUid,String prizeId,String stateTime,String endTime) throws Exception {
        JSONObject json = new JSONObject();
        json.put("metro_uid",metroUid);
        json.put("prize_id",prizeId);
        json.put("start_time",stateTime);
        String apiMethod = "bangdao.duchang.facade.prize.api.rsa.getPrizeApi";
        return rsaRequest(json,apiMethod);
    }

    public static void main(String[] args){
        try {
            String response = new RsaApiRequest().qrCode("20190807100001068081127001700647");
            JSONObject object = JSONObject.parseObject(response);
            log.info(JSONObject.parseObject(object.getString("content")).getString("rtn_data"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public String rsaRequest(JSONObject json, String apiMethod) throws Exception {
        Map<String,Object> map=new HashMap<>();
        map.put("app_id",APP_ID);
        map.put("sign_type",SIGN_TYPE_1);
        map.put("charset","UTF-8");
        map.put("method",apiMethod);
        map.put("biz_content",json);

        log.info("getSignContent -- map : "+ JSON.toJSONString(map));

        String param = getSignContent(map);
        String str = RSAUtil.sign(param.getBytes("UTF-8"),PRIVATE_KEY);

        map.put("sign",str);
        return HttpUtils.doPostByRas(URL,map);
    }

    public String qrCode(String url) throws Exception{
        JSONObject json = new JSONObject();
        json.put("code_url",url);
        String apiMethod = "bangdao.duchang.facade.prize.api.rsa.getCode";
        return rsaRequest(json,apiMethod);
    }

    public static String getSignContent(Map<String,Object> map) {
        if (map == null) {
            return null;
        }
        StringBuffer content = new StringBuffer();
        List<String> keys = new ArrayList<String>(map.keySet());
        Collections.sort(keys);
        int index = 0;
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = map.get(key).toString();
            if (!StringUtils.isEmpty(key) && !StringUtils.isEmpty(value)) {
                content.append((index == 0 ? "" : "&") + key + "=" + value);
                index++;
            }
        }
        return content.toString();
    }

}
