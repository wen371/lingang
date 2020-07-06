package com.jw.common.support;

/**
 * Created by huangfeng on 2018/3/18.
 */
public class IssueUtil {


    public static boolean checkAge(Integer age,String ageUnit,Integer startAge,String startUnit,Integer endAge,String endUnit){
        if(startAge != 0){
            if("0".equals(startUnit) && "0".equals(endUnit) && "0".equals(ageUnit)){
                if(age<startAge){
                    return false;
                } else if(age>endAge){
                    return false;
                }
            } else if("0".equals(endUnit) && "1".equals(ageUnit)){
                return false;
            } else if("1".equals(startUnit) && "0".equals(ageUnit)){
                return false;
            } else if("1".equals(startUnit) && "1".equals(ageUnit)){
                if(age<startAge){
                    return false;
                } else if(age>endAge){
                    return false;
                }
            }
        } else{
            if("0".equals(endUnit) && "0".equals(ageUnit)){
                if(age>endAge){
                    return false;
                }
            } else if("0".equals(endUnit) && "1".equals(ageUnit)){
                return false;
            } else if("1".equals(endUnit) && "1".equals(ageUnit)){
                if(age>endAge){
                    return false;
                }
            }
        }
        return true;
    }
}
