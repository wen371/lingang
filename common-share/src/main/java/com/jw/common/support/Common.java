package com.jw.common.support;

import java.io.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.httpclient.HttpHost;

public class Common {
	/**
	 * 字符串转日期
	 * @param str 日期字符串
	 * @return
	 * @throws ParseException
	 */
	public static Date stringToDate(String str) throws ParseException {
		//转换提核时间，从string到date
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.parse(str);
	}
	/**
	 * 对象转换Json字符串
	 * @param object
	 * @return json字符串
	 */
	public static String toJson(Object object){
		ObjectMapper mapper = new ObjectMapper();
        Writer strWriter = new StringWriter(); 
        try {
			mapper.writeValue(strWriter, object);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
        String userDataJSON = strWriter.toString(); 
		return userDataJSON;
	}
	
	/**
	 * 根据当前时间获取某天零点的Date值
	 * @param dif 与当前日期的天数差 正为向后几天，负为向前几天
	 * @return 第二天零点的Date
	 */
	public static Date getTimeOf12(int dif) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.add(Calendar.DAY_OF_MONTH, dif);
        return  cal.getTime();
    }
	
	/**
	 * 根据传入时间获取某天零点的Date值
	 * @param time 传入的时间
	 * @return 第二天零点的Date
	 */
	public static Date getTimeOf12(Date time) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.add(Calendar.DAY_OF_MONTH, 1);
        return  cal.getTime();
    }
	/**
	 * 获得代理服务器地址
	 * @return
	 * @throws Exception 
	 */
	public static HttpHost getHttpProxy() {
		Properties properties = new Properties();
		ClassLoader classLoader =  Common.class.getClassLoader(); 
		InputStream is = null;
		try{         
			// 取得资源文件输入流            
			is = classLoader.getResourceAsStream("resources.properties");
			properties.load(is);
			HttpHost httpHost = new HttpHost(properties.getProperty("http.proxy.host"),Integer.parseInt(properties.getProperty("http.proxy.port")));
			is.close();
			return httpHost;
		}              
		catch(FileNotFoundException e){                 
			e.printStackTrace();                 
		}              
		catch(IOException e){                 
			e.printStackTrace();                 
		}
		return null;
	}
	 
	/**
	 * 获得资源文件任意信息
	 * @return
	 * @throws Exception 
	 */
	public static String getProperties(String name) {
		Properties p = new Properties();
		ClassLoader classLoader =  Common.class.getClassLoader(); 
		InputStream is = null;
		try{         
			// 取得资源文件输入流            
			is = classLoader.getResourceAsStream("resources.properties");
			p.load(is);
			String str = p.getProperty(name);
			is.close();
			return str;
		}              
		catch(FileNotFoundException e){                 
			e.printStackTrace();                 
		}              
		catch(Exception e){                 
			e.printStackTrace();                 
		}
		return "";
	}
	/**
	 * 将double类型转换成String
	 * @param dou
	 * @return
	 */
	public static String doubleToString(double dou) {
		return Double.toString(dou);
	}
	/**
	 * 将BigDecimal类型保留2位小数
	 * @param bigde
	 * @return
	 */
	public static BigDecimal processDecimal(BigDecimal bigde){
		return bigde.divide(new BigDecimal(1) ,2 ,BigDecimal.ROUND_HALF_UP);
	}
	/**
	 * 将BigDecimal类型保留3位小数
	 * @param bigde
	 * @return
	 */
	public static BigDecimal processDecimal3(BigDecimal bigde){
		return bigde.divide(new BigDecimal(1) ,3 ,BigDecimal.ROUND_HALF_UP);
	}
	/**
	 * 获取第三方推送的URL
	 */
	public static String getUrl(String type){
		Properties properties = new Properties();
		ClassLoader classLoader =  Common.class.getClassLoader(); 
		InputStream is = null;
		String url="";
		try{
			// 取得资源文件输入流            
			is = classLoader.getResourceAsStream("resources.properties");
			properties.load(is);
			url=type.equals("commision")?properties.getProperty("send.commision.url") :properties.getProperty("send.red.url");
		}              
		catch(FileNotFoundException e){                 
			e.printStackTrace();                 
		}              
		catch(IOException e){                 
			e.printStackTrace();                 
		}
		return url;
	}
	
	/**
	 * 将日期转换为字符串
	 */
	
	public static String dateToString(Date date){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = sdf.format(date);
		return dateString;
	}
	/**
	 * 传入的BigDecimal类型，四舍五入，保留整数
	 * @param commision
	 * @return
	 */
	public static BigDecimal commisionRounding(BigDecimal commision){
		DecimalFormat myformat=new DecimalFormat("0.00");
		BigDecimal com =  new BigDecimal(myformat.format(commision)).setScale(0, BigDecimal.ROUND_HALF_UP);
		return com;
	}
	/**
	 * 比较传入的时间大小
	 * @param date1
	 * @param date2
	 * @return date1>date2---1 date1<date2---2  date1=date2---0
	 */
	public static int compareDate(Date date1,Date date2){
		 if (date1.getTime() > date2.getTime()) {
             return 1;
         } else if (date1.getTime() < date2.getTime()) {
             return 2;
         } else {
             return 0;
         }
	}
	/**
	 * 根据传入的当前时间获取当天的23点59分59秒时间返回
	 */
	public static Date commisionEffeDate(Date date){
		Date endTime=new Date();
		SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdfDate =   new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String nowDay=sdfDay.format(date);
//		System.out.println(nowDay);
		String nowDate=nowDay+" 23:59:59";
		try {
			endTime=sdfDate.parse(nowDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
//		System.out.println(endTime);
		return endTime;
	}
}
