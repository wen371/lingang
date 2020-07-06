package com.jw.common.support;

import java.util.Calendar;

/**
 * Created by huangfeng on 2018/1/22.
 */
public class IdCardUtil {

    /**
     * 根据身份证号计算性别（倒数第二位，奇数为男，偶数为女）
     * @param value 身份证号
     * @return	1-男，2-女
     */
    public static Integer getSexByIdCard(String value) {
        value = value.trim();
        String sexValue = value.substring(value.length() - 2, value.length()-1);
        int sex = Integer.parseInt(sexValue) % 2;
        return sex == 1 ? 1 : 2;

    }

    /**
     * 根据身份证获取年龄
     * （仅适用于18位身份证号）
     * @param idCard
     * @return
     */
    public static int getAgeByIdCard(String idCard) {
        int iAge = 0;
        String year = idCard.substring(6,10);
        Calendar cal = Calendar.getInstance();
        int iCurrYear = cal.get(Calendar.YEAR);
        iAge = iCurrYear - Integer.valueOf(year);
        return iAge;
    }

    /**
     * 根据身份证获取出生日期
     * （仅适用于18位身份证号）
     * @param idCard
     * @return
     */
    public static String getBirthDayByIdCard(String idCard) {
        String birthDay = "";
        String year = idCard.substring(6, 10);
        String month = idCard.substring(10, 12);
        String day = idCard.substring(12, 14);
        birthDay = year+"-"+month+"-"+day;
        return birthDay;
    }

    public static int getRealAgeByIdCard(String idCard,String startDate){
        Calendar cal = Calendar.getInstance();
        if(ToolUtil.isNotEmpty(startDate)){
            if(startDate.length()==10){
                cal.setTime(DateUtil.parseDate(startDate));
            } else{
                cal.setTime(DateUtil.parseTime(startDate));
            }
        }

        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH);
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);

        String birthDay = getBirthDayByIdCard(idCard);
        cal.setTime(DateUtil.parseDate(birthDay));
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;
        if (monthNow == monthBirth) {
            if (dayOfMonthNow < dayOfMonthBirth){
                age--;
            }
        }else if (monthNow < monthBirth){
            age--;
        }
        return age;
    }

    public static int getRealAgeDayByIdCard(String idCard,String startDate){
        Calendar startCal = Calendar.getInstance();
        if(ToolUtil.isNotEmpty(startDate)){
            if(startDate.length()>10){
                startDate = startDate.substring(0,10);
            }
            startCal.setTime(DateUtil.parseDate(startDate));
        }
        long startL = startCal.getTimeInMillis();

        Calendar ageCal = Calendar.getInstance();
        String birthDay = getBirthDayByIdCard(idCard);
        ageCal.setTime(DateUtil.parseDate("2018-03-16"));
        long ageL = ageCal.getTimeInMillis();

        int age = (int)(startL - ageL)/(1000*60*60*24);
        return age;
    }
}
