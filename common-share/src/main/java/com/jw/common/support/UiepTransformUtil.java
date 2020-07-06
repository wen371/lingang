package com.jw.common.support;

import java.math.BigDecimal;

/**
 * Created by huangfeng on 2018/1/30.
 */
public class UiepTransformUtil {

    public static String innerSexToOut(String sex){
        //01男 02女
        if("0".equals(sex)){
            return "01";
        } else if("1".equals(sex)){
            return "02";
        }
        return "";
    }


    public static double getBenfcRate(Integer benRate){
        double rate = 1;
        if(null != benRate && benRate > 0){
            rate = (double)benRate/100;
        }
        return rate;
    }

    public static BigDecimal getBenfcRate(BigDecimal benRate){
        BigDecimal rate = new BigDecimal("1");
        if(null != benRate && benRate.compareTo(BigDecimal.ZERO)>-1){
            rate = benRate.divide(new BigDecimal("100"),4,BigDecimal.ROUND_DOWN);
        }
        return rate;
    }

    public static String getIdentifyType(String identifyType){
        //身份证：01  营业执照：3  其他：13
        if("1".equals(identifyType)){
           return "01";
        } else if("2".equals(identifyType)){
            return "13";
        }
        return identifyType;
    }

    public static String getApplicantType(String applicantType) {
        if("0".equals(applicantType)){
            return "1";
        }else if("1".equals(applicantType)){
            return "2";
        }
        return applicantType;
    }

    public static String getCorrectionInsureType(String insureType){
        if("1".equals(insureType)){
            return "2";//团体
        }
        return "1";//个人
    }

}
