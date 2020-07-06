/**
 * Copyright (c) 2015-2016, Chill Zhuang 庄骞 (smallchill@163.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jw.common.support;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

public class DateUtil {


	/**
	 * 获取YYYY格式
	 *
	 * @return
	 */
	public static String getYear() {
		return formatDate(new Date(), "yyyy");
	}

	/**
	 * 获取YYYY格式
	 *
	 * @return
	 */
	public static String getYear(Date date) {
		return formatDate(date, "yyyy");
	}

	/**
	 * 获取YYYY-MM-DD格式
	 *
	 * @return
	 */
	public static String getDay() {
		return formatDate(new Date(), "yyyy-MM-dd");
	}

	/**
	 * 获取YYYY-MM-DD格式
	 *
	 * @return
	 */
	public static String getDay(Date date) {
		return formatDate(date, "yyyy-MM-dd");
	}

	/**
	 * 获取YYYYMMDD格式
	 *
	 * @return
	 */
	public static String getDays() {
		return formatDate(new Date(), "yyyyMMdd");
	}

	/**
	 * 获取YYYYMMDD格式
	 *
	 * @return
	 */
	public static String getDays(Date date) {
		return formatDate(date, "yyyyMMdd");
	}

	/**
	 * 获取YYYY-MM-DD HH:mm:ss格式
	 *
	 * @return
	 */
	public static String getTime() {
		return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 获取YYYY-MM-DD HH:mm:ss.SSS格式
	 *
	 * @return
	 */
	public static String getMsTime() {
		return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss.SSS");
	}

	/**
	 * 获取YYYYMMDDHHmmss格式
	 *
	 * @return
	 */
	public static String getAllTime() {
		return formatDate(new Date(), "yyyyMMddHHmmss");
	}

	/**
	 * 获取YYYY-MM-DD HH:mm:ss格式
	 *
	 * @return
	 */
	public static String getTime(Date date) {
		return formatDate(date, "yyyy-MM-dd HH:mm:ss");
	}

	public static String formatDate(Date date, String pattern) {
		String formatDate = null;
		if (StringUtils.isNotBlank(pattern)) {
			formatDate = DateFormatUtils.format(date, pattern);
		} else {
			formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
		}
		return formatDate;
	}

	/**
	 * @Title: compareDate
	 * @Description:(日期比较，如果s>=e 返回true 否则返回false)
	 * @param s
	 * @param e
	 * @return boolean
	 * @throws
	 * @author luguosui
	 */
	public static boolean compareDate(String s, String e) {
		if(StringUtils.isBlank(s) || StringUtils.isBlank(e)){
			return false;
		}
		if(s.length()>10){
			s = s.substring(0,10);
		}
		if(e.length()>10){
			e = e.substring(0,10);
		}
		if (parseDate(s) == null || parseDate(e) == null) {
			return false;
		}
		return parseDate(s).getTime() >= parseDate(e).getTime();
	}

	/**
	 * 格式化日期
	 *
	 * @return
	 */
	public static Date parseDate(String date) {
		return parse(date,"yyyy-MM-dd");
	}

	/**
	 * 格式化日期
	 *
	 * @return
	 */
	public static Date parseTime(String date) {
		return parse(date,"yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 格式化日期
	 *
	 * @return
	 */
	public static Date parse(String date, String pattern) {
		try {
			return DateUtils.parseDate(date,pattern);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 格式化日期
	 *
	 * @return
	 */
	public static String format(Date date, String pattern) {
		return DateFormatUtils.format(date, pattern);
	}

	/**
	 * 把日期转换为Timestamp
	 *
	 * @param date
	 * @return
	 */
	public static Timestamp format(Date date) {
		return new Timestamp(date.getTime());
	}

	/**
	 * 校验日期是否合法
	 *
	 * @return
	 */
	public static boolean isValidDate(String s) {
		return parse(s, "yyyy-MM-dd HH:mm:ss") != null;
	}

	/**
	 * 校验日期是否合法
	 *
	 * @return
	 */
	public static boolean isValidDate(String s, String pattern) {
        return parse(s, pattern) != null;
	}

	public static int getDiffYear(String startTime, String endTime) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			int years = (int) (((fmt.parse(endTime).getTime() - fmt.parse(
					startTime).getTime()) / (1000 * 60 * 60 * 24)) / 365);
			return years;
		} catch (Exception e) {
			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			return 0;
		}
	}

	/**
	 * <li>功能描述：时间相减得到天数
	 *
	 * @param beginDateStr
	 * @param endDateStr
	 * @return long
	 * @author Administrator
	 */
	public static long getDaySub(String beginDateStr, String endDateStr) {
		long day = 0;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date beginDate = null;
		Date endDate = null;
		try {
			if(beginDateStr.length()>10){
				beginDate = format1.parse(beginDateStr);
			} else{
				beginDate = format.parse(beginDateStr);
			}
			if(endDateStr.length()>10){
				endDate = format1.parse(endDateStr);
			} else{
				endDate = format.parse(endDateStr);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		day = (endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000);
		return day;
	}


	/**
	 * <li>功能描述：时间相减得到月数
	 *
	 * @param beginDateStr
	 * @param endDateStr
	 * @return long
	 * @author Administrator
	 */
	public static long getMonthSub(String beginDateStr, String endDateStr) {
		long month = 0;
		SimpleDateFormat format = new SimpleDateFormat(
				"yyyy-MM-dd");
		Date beginDate = null;
		Date endDate = null;

		try {
			beginDate = format.parse(beginDateStr);
			endDate = format.parse(endDateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		long day = (endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000);
		month = day/30;
		return month;
	}

	/**
	 * <li>功能描述：时间相减得到年数
	 *
	 * @param beginDateStr
	 * @param endDateStr
	 * @return long
	 * @author Administrator
	 */
	public static long getYearSub(String beginDateStr, String endDateStr) {
		long year = 0;
		SimpleDateFormat format = new SimpleDateFormat(
				"yyyy-MM-dd");
		Date beginDate = null;
		Date endDate = null;

		try {
			beginDate = format.parse(beginDateStr);
			endDate = format.parse(endDateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		long day = (endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000);
		year = day/364;
		return year;
	}

	/**
	 * 得到n天之后的日期
	 *
	 * @param days
	 * @return
	 */
	public static String getAfterDayDate(String days) {
		int daysInt = Integer.parseInt(days);

		Calendar canlendar = Calendar.getInstance(); // java.util包
		canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
		Date date = canlendar.getTime();

		SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateStr = sdfd.format(date);

		return dateStr;
	}

	/**
	 * 得到n天之后是周几
	 *
	 * @param days
	 * @return
	 */
	public static String getAfterDayWeek(String days) {
		int daysInt = Integer.parseInt(days);

		Calendar canlendar = Calendar.getInstance(); // java.util包
		canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
		Date date = canlendar.getTime();

		SimpleDateFormat sdf = new SimpleDateFormat("E");
		String dateStr = sdf.format(date);

		return dateStr;
	}

	public static String getBeforeTime(Date date,int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);//把当前时间赋给日历
		calendar.add(Calendar.DAY_OF_MONTH, day);  //设置为前一天
		Date dBefore = calendar.getTime();   //得到前一天的时间
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		String defaultStartDate = sdf.format(dBefore);
		return defaultStartDate;
	}

	public static String getMonday() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		Date dBefore = calendar.getTime();   //得到前一天的时间
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		String defaultStartDate = sdf.format(dBefore);
		return defaultStartDate;
	}

	public static boolean checkStartDateIsToday(Date startDate){
		boolean b = false;
		String nowDay = DateUtil.getDay();
		if(null != startDate && DateUtil.getDay(startDate).equals(nowDay)){
			b = true;
		}
		return b;
	}

	public static boolean checkStartDateStrIsToday(String startDateStr){
		boolean b = false;
		String nowDay = DateUtil.getDay();
		if(ToolUtil.isEmpty(startDateStr) ){
			return b;
		}

		if(startDateStr.length()>10){
			startDateStr = startDateStr.substring(0,10);
		}
		Date startDate = DateUtil.parseDate(startDateStr);
		if(DateUtil.getDay(startDate).equals(nowDay)){
			b = true;
		}
		return b;
	}

	public static boolean checkStartDateStrIsMinToday(String startDateStr){
		if(ToolUtil.isEmpty(startDateStr)){
			return false;
		}
		if(startDateStr.length()>10){
			startDateStr = startDateStr.substring(0,10);
		}
		long startDateL = DateUtil.parseDate(startDateStr).getTime();
		if(startDateL <= new Date().getTime()){
			return true;
		}
		return false;
	}

	/**
	 *
	 */
	public static long getTimeSub(Date date1,Date date2){
		long l=date1.getTime()-date2.getTime();
		l=l/60/1000;
		return l;
	}

	/**
	 *
	 * @param birthDay
	 * @param startDate
	 * @return
	 */
	public static int getAgeFromBirthday(String birthDay, String startDate) {
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

		cal.setTime(DateUtil.parseDate(birthDay));
		int yearBirth = cal.get(Calendar.YEAR);
		int monthBirth = cal.get(Calendar.MONTH);
		int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

		int age = yearNow - yearBirth;
		if (monthNow <= monthBirth) {
			if (monthNow == monthBirth) {
				if (dayOfMonthNow < dayOfMonthBirth){
					age--;
				}
			}else{
				age--;
			}
		}
		return age;
	}

	//判断年份是否为闰年
	public static boolean isLeapYear(int year){
		if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
			return true;
		} else {
			return false;
		}
	}
}
