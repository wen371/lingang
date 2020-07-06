package com.jw.common.support;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @description 生成订单工具类
 * @version 1.0
 * @date 2016-05-18
 * @author Having
 */
public class CreatOrderNoUtil {

    private static Object lockObj = "lockerOrder";
    private static int    sri     = 0;
    private static int    srr     = 0;
    private static int    srl     = 0;

    /**
     * @description 生成订单号
     * @param riskCode
     * @param orgCode
     * @return String
     */
    public static String creatOrderNo(String riskCode, String orgCode) {
        // 最终生成的订单号
        String orderNo = "";

        if (ToolUtil.isEmpty(riskCode) || riskCode.length()<4) {
            return null;
        }
        if(riskCode.length()>4){
            riskCode = riskCode.substring(0,4);
        }

        if (ToolUtil.isEmpty(orgCode) || orgCode.length()<4) {
            return null;
        }
        if(orgCode.length()>4){
            orgCode = orgCode.substring(0,4);
        }

        String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
        synchronized (lockObj) {
            String ms = Long.valueOf(System.currentTimeMillis()).toString();
            if (sri == 99) {
                sri = 0;
            } else {
                sri++;
            }
            orderNo = "ECD" + orgCode.trim().toUpperCase() + date.substring(2)
                    + ms.substring(ms.length() - 6) + padLeft(sri + "", 2, '0');
            return orderNo;
        }
    }

    /**
     * @description 生成流水号
     * @return String
     */
    public static String creatFCTradeNo() {
        // 最终生成的订单号
        String orderNo = "";

        String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
        synchronized (lockObj) {
            String ms = Long.valueOf(System.currentTimeMillis()).toString();
            if (sri == 99) {
                sri = 0;
            } else {
                sri++;
            }
            orderNo = "TDFC" + date.substring(2) + ms.substring(ms.length() - 8) + padLeft(sri + "", 2, '0');
            return orderNo;
        }
    }

    /**
     * @description 生成流水号
     * @return String
     */
    public static String creatYJTradeNo() {
        // 最终生成的订单号
        String orderNo = "";

        String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
        synchronized (lockObj) {
            String ms = Long.valueOf(System.currentTimeMillis()).toString();
            if (sri == 99) {
                sri = 0;
            } else {
                sri++;
            }
            orderNo = "TDYJ" + date.substring(2) + ms.substring(ms.length() - 8) + padLeft(sri + "", 2, '0');
            return orderNo;
        }
    }

    /**
     * @description 生成产品的code
     * @return String
     */
    public static String creatProductCode(String riskCode) {
        // 最终生成的订单号
        String orderNo = "";

        String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
        synchronized (lockObj) {
            String ms = Long.valueOf(System.currentTimeMillis()).toString();
            if (sri == 99) {
                sri = 0;
            } else {
                sri++;
            }
            orderNo = riskCode + date.substring(2) + ms.substring(ms.length() - 8) + padLeft(sri + "", 2, '0');
            return orderNo;
        }
    }

    /**
     * @description 按指定位数转化字符串，不足位数，按指定字符左面补齐
     * @return String
     */
    public static String padLeft(String str, int length, char c) {
        if (null == str || str.isEmpty()) {
            return null;
        }
        str = str.trim();
        if (str.length() > length) {
            return null;
        }
        if (str.length() == length) {
            return str;
        }
        char[] chars = new char[length - str.length()];
        str.getChars(0, str.length(), chars, 0);
        java.util.Arrays.fill(chars, 0, length - str.length(), c);

        return new String(chars) + str;
    }
    /**
     * @description 生成订单号
     * @return String
     */
    public static String createOrderNo() {
        // 最终生成的订单号
        String orderNo = "";
        String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
        //随机3位数
        String random = (int)(Math.random()*899)+100+"";
        synchronized (lockObj) {
            String ms = Long.valueOf(System.currentTimeMillis()).toString();
            if (sri == 99) {
                sri = 0;
            } else {
                sri++;
            }
            orderNo = "OD" + date.substring(2) + random
                    + ms.substring(ms.length() - 5) + padLeft(sri + "", 2, '0');
            return orderNo;
        }
    }
    /**
     * @description 生成流水号
     * @return String
     */
    public static String createTradeNo() {
        // 最终生成的订单号
        String orderNo = "";
        String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
        //随机3位数
        String random = (int)(Math.random()*899)+100+"";
        synchronized (lockObj) {
            String ms = Long.valueOf(System.currentTimeMillis()).toString();
            if (srr == 99) {
                srr = 0;
            } else {
                srr++;
            }
            orderNo = "TD" + date.substring(2) + random
                    + ms.substring(ms.length() - 5) + padLeft(srr + "", 2, '0');
            return orderNo;
        }
    }
    /**
     * @description 生成凭证码
     * @return String
     */
    public static String createCertificateNo() {
        int  maxNum = 10;
        int i;
        int count = 0;
        /*char[] str = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
                'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
                'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };*/
        char[] str = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
        StringBuffer pwd = new StringBuffer("");
        Random r = new Random();
        while(count < 9){
            i = Math.abs(r.nextInt(maxNum));
            if (i >= 0 && i < str.length) {
                pwd.append(str[i]);
                count ++;
            }
        }
        return pwd.toString();
    }
    /**
     * @description 生成核销码
     * @return String
     */
    public static String createCheckNo() {
        // 最终生成的订单号
        String orderNo = "";
        String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
        //随机3位数
        String random = (int)(Math.random()*899)+100+"";
        synchronized (lockObj) {
            String ms = Long.valueOf(System.currentTimeMillis()).toString();
            if (srl == 99) {
                srl = 0;
            } else {
                srl++;
            }
            orderNo = "HX" + date.substring(2) + random
                    + ms.substring(ms.length() - 4) + padLeft(srl + "", 2, '0');
            return orderNo;
        }
    }
    public static void main(String[] args) {
        /*try {

            for (int i = 0; i < 300; i++) {

                Thread thread1 = new Thread(new Runnable() {

                    public void run() {
                        System.out.println(creatOrderNo("CXCX", "0909"));
                    }

                }, "at" + i);

                thread1.start();

                Thread thread2 = new Thread(new Runnable() {

                    public void run() {
                        System.out.println(creatYJTradeNo());
                    }

                }, "bt" + i);

                thread2.start();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

}
