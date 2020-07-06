package com.jw.common.support;

import java.util.Random;

/**
 * Created by zhouguangyue on 18/2/2.
 */
public class NumberRandom {
    public static  String getNotRepeatRandomNum(int n){
        StringBuffer sb = new StringBuffer();
        String str = "0123456789";
        Random r = new Random();
        for(int i = 0; i < n; i++){
            int num = r.nextInt(str.length());
            sb.append(str.charAt(num));
            str = str.replace((str.charAt(num)+""), "");
        }
        return sb.toString();
    }
}
