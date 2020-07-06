package com.jw.admin.core.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 
 * 生成器类,用于生成各种号码
 *
 * <p>detailed comment
 * @author YLJ 2016年6月14日
 * @since 1.0
 */
public final class Generator
{
    private final static String stab = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static Random rnd = new Random();
    
    /**
     * 获取指定上限的随机正整型数
     * @param u 上限
     * @return
     */
    public static int getRInt(int u)
    {
        int rInt = rnd.nextInt();
        return (Math.abs(rInt%u));
    }
    
    /**
     * 获取指定长度的随机字符串
     * @param len
     * @return
     */
    public static String getRStr(int len)
    {
        StringBuffer sb = new StringBuffer();
        int slen = stab.length() - 1;
        for (int i = 0; i < len; i++)
        {
            int pos = getRInt(slen);
            sb.append(stab.charAt(pos));
        }
        return sb.toString();
    }
    /**
     * 获取系统当前时间
     * @return
     * @throws DataBaseException 
     */
    public static String getCurrentTimestamp()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(new Date());
    }
}
