/*
 * 公共用验证类
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2010.06.25  ZhangLipeng     プログラム・リリース
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2013 baseos System. - All Rights Reserved.
 *
 */
package com.jw.common.support;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * <p>
 * 公共用验证类
 * </p>
 */
@Slf4j
public final class Validator {
	/** 用户名 */
	private static final String USERNAME = "^[a-zA-Z\\d\u4e00-\u9fa5][a-zA-Z\\d\u4e00-\u9fa5_]{0,10}[a-zA-Z\\d\u4e00-\u9fa5]$";
	/** 职位名称 */
	private static final String JOBNAME = "^[a-z\u4e00-\u9fa5][a-z\\d\u4e00-\u9fa5_]{0,10}[a-z\\d\u4e00-\u9fa5]$";
	/** 验证密码:长度在6~16之间，只能包含字符、数字和下划线 */
	public static final String PASSWORD = "^[a-zA-Z\\d_]{6,16}$";
	/** 电子邮件 */
	public static final String EMAIL = "^([\\w-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([\\w-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
	/** 网址 */
	public static final String HREF = "^((https|http|ftp|rtsp|mms)?://)"
			+ "?(([0-9a-z_!~*'().&=+$%-]+: )?[0-9a-z_!~*'().&=+$%-]+@)?" // ftp的user@
			+ "(([0-9]{1,3}\\.){3}[0-9]{1,3}" // IP形式的URL- 199.194.52.184
			+ "|" // 允许IP和DOMAIN（域名）
			+ "([0-9a-z_!~*'()-]+\\.)*" // 域名- www.
			+ "([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]\\." // 二级域名
			+ "[a-z]{2,6})" // first level domain- .com or .museum
			+ "(:[0-9]{1,4})?" // 端口- :80
			+ "((/?)|" // a slash isn't required if there is no file name
			+ "(/[0-9a-z_!~*'().;?:@&=+$,%#-]+)+/?)$";
	/** HTTP网址 */
	public static final String HREF_PROTOCOL = "^((https|http|ftp|rtsp|mms)://)"
			+ "(([0-9]{1,3}\\.){3}[0-9]{1,3}" // IP形式的URL- 199.194.52.184
			+ "|" // 允许IP和DOMAIN（域名）
			+ "([0-9a-z_!~*'()-]+\\.)*" // 域名- www.
			+ "([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]\\." // 二级域名
			+ "[a-z]{2,6})" // first level domain- .com or .museum
			+ "(:[0-9]{1,4})?" // 端口- :80
			+ "((/?)|" // a slash isn't required if there is no file name
			+ "(/[0-9a-z_!~*'().;?:@&=+$,%#-]+)+/?)$";
	/** 电话号码 */
	public static final String TEL = "^(\\d{3,4}-)?\\d{6,8}$";
	/** 邮件编码 */
	public static final String POSTCODE = "^\\d{6}$";
	/** 手机号码 */
	public static final String MOBILE = "^[1][3-8]+\\d{9}$";
	/** 身份证 */
	public static final String IDCARD = "(^\\d{18}$)|(^\\d{15}$)";

	/** 严格验证时间格式的(匹配[2002-01-31], [1997-04-30], */
	/** [2004-01-01])不匹配([2002-01-32], [2003-02-29], [04-01-01]) */
	public static final String DATE_STRICT = "^((((19|20)(([02468][048])|([13579][26]))-02-29))"
			+ "|((20[0-9][0-9])|(19[0-9][0-9]))-((((0[1-9])|(1[0-2]))-((0[1-9])|(1\\d)|(2[0-8])))"
			+ "|((((0[13578])|(1[02]))-31)|(((01,3-9])|(1[0-2]))-(29|30)))))$";
	/** 没加时间验证的YYYY-MM-DD */
	public static final String DATE = "^((((1[6-9]|[2-9]\\d)\\d{2})-(0?[13578]|1[02])-(0?[1-9]"
			+ "|[12]\\d|3[01]))|(((1[6-9]|[2-9]\\d)\\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\\d|30))"
			+ "|(((1[6-9]|[2-9]\\d)\\d{2})-0?2-(0?[1-9]|1\\d|2[0-8]))|(((1[6-9]|[2-9]\\d)(0[48]|[2468][048]"
			+ "|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))$";
	/** 加了时间验证的YYYY-MM-DD 00:00:00 */
	public static final String DATE_TIME = "^((((1[6-9]|[2-9]\\d)\\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\\d|3[01]))"
			+ "|(((1[6-9]|[2-9]\\d)\\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\\d|30))|(((1[6-9]|[2-9]\\d)\\d{2})-0?2-(0?[1-9]|1\\d|2[0-8]))"
			+ "|(((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-)) "
			+ "(20|21|22|23|[0-1]?\\d):[0-5]?\\d:[0-5]?\\d$";
	/** 验证是否为汉字 */
	public static final String CHINESE = "^[\u4E00-\u9FA5]+$";
	/** 数字 */
	public static final String NUM = "^\\d+$";
	/** 手机认证码 */
	private static final String MOBILE_VERIFY_CODE = "^[\\d]{6}$";
	
	// 每位加权因子  
    private static final int power[] = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 };

	/**
	 * <p>
	 * 验证用户名
	 * </p>
	 * 
	 * @param src
	 *            字符串
	 * @param length
	 *            长度
	 * @return true:符合<br>
	 *         false:不符合
	 */
	public static boolean isUsername(String str, int length) {
		boolean value = false;
		if ((getWordCount(str) > (length * 2)) || str.length() > length) {
			return value;
		}

		return match(USERNAME, str);
	}

	/**
	 * <p>
	 * 验证职位名称
	 * </p>
	 * 
	 * @param src
	 *            字符串
	 * @param length
	 *            长度
	 * @return true:符合<br>
	 *         false:不符合
	 */
	public static boolean isJobName(String str, int length) {
		boolean value = false;
		if (getWordCount(str) > length) {
			return value;
		}

		return match(JOBNAME, str);
	}
	/**
	 * 检测字符串是否不为空(null,"","null")
	 * @param s
	 * @return 不为空则返回true，否则返回false
	 */
	public static boolean notEmpty(String s){
		return s!=null && !"".equals(s) && !"null".equals(s);
	}
	
	/**
	 * 检测字符串是否为空(null,"","null")
	 * @param s
	 * @return 为空则返回true，不否则返回false
	 */
	public static boolean isEmpty(String s){
		return s==null || "".equals(s.trim()) || "null".equals(s);
	}
	
	/**
	 * 字符串转换为字符串数组
	 * @param str 字符串
	 * @param splitRegex 分隔符
	 * @return
	 */
	public static String[] str2StrArray(String str,String splitRegex){
		if(isEmpty(str)){
			return null;
		}
		return str.split(splitRegex);
	}
	
	/**
	 * 用默认的分隔符(,)将字符串转换为字符串数组
	 * @param str	字符串
	 * @return
	 */
	public static String[] str2StrArray(String str){
		return str2StrArray(str,",\\s*");
	}
	
	/**
	 * 按照yyyy-MM-dd HH:mm:ss的格式，日期转字符串
	 * @param date
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String date2Str(Date date){
		return date2Str(date,"yyyy-MM-dd HH:mm:ss");
	}
	
	/**
	 * 按照yyyy-MM-dd HH:mm:ss的格式，字符串转日期
	 * @param date
	 * @return
	 */
	public static Date str2Date(String date){
		if(notEmpty(date)){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				return sdf.parse(date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				log.error("", e);
			}
			return new Date();
		}else{
			return null;
		}
	}
	
	/**
	 * 按照参数format的格式，日期转字符串
	 * @param date
	 * @param format
	 * @return
	 */
	public static String date2Str(Date date,String format){
		if(date!=null){
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.format(date);
		}else{
			return "";
		}
	}
	/**
	 * 验证对象是否为空
	 * 
	 * @return
	 */
	public static boolean isNullEmpty(Object obj) {
		if (obj instanceof String) {
			return obj == null || ((String) obj).length() == 0;
		} else if (obj instanceof Object[]) {
			Object[] temp = (Object[]) obj;
			for (int i = 0; i < temp.length; i++) {
				if (!isNullEmpty(temp[i])) {
					return false;
				}
			}
			return true;
		}
		return obj == null;
	}

//	/**
//	 * 验证邮箱
//	 * 
//	 * @param 待验证的字符串
//	 * @return 如果是符合的字符串,返回 <b>true </b>,否则为 <b>false </b>
//	 */
//	public static boolean isEmail(String str) {
//
//		// 长度验证
//		if (getWordCount(str) > MailConstant.MAIL_TO_LENGTH) {
//			return false;
//		}
//
//		return match(EMAIL, str);
//	}

	/**
	 * 验证IP地址
	 * 
	 * @param 待验证的字符串
	 * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
	 */
	public static boolean isIP(String str) {
		String num = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";
		String regex = "^" + num + "\\." + num + "\\." + num + "\\." + num
				+ "$";
		return match(regex, str);
	}

	/**
	 * 验证网址Url
	 * 
	 * @param 待验证的字符串
	 * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
	 */
	public static boolean isUrl(String str) {
		return match(HREF, str.toLowerCase());
	}

	/**
	 * 验证HTTP网址Url
	 * 
	 * @param 待验证的字符串
	 * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
	 */
	public static boolean isUrlProtocol(String str) {
		return match(HREF_PROTOCOL, str.toLowerCase());
	}

	/**
	 * 验证电话号码
	 * 
	 * @param 待验证的字符串
	 * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
	 */
	public static boolean isTel(String str) {
		return match(TEL, str);
	}

	/**
	 * 验证输入密码长度 (6-18位)
	 * 
	 * @param 待验证的字符串
	 * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
	 */
	public static boolean isPassword(String str) {
		return match(PASSWORD, str);
	}

	/**
	 * 验证输入邮政编号
	 * 
	 * @param 待验证的字符串
	 * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
	 */
	public static boolean isPostalcode(String str) {
		return match(POSTCODE, str);
	}

	/**
	 * 验证输入手机号码
	 * 
	 * @param 待验证的字符串
	 * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
	 */
	public static boolean isMobile(String str) {
		return match(MOBILE, str);
	}

	/**
	 * 验证输入身份证号
	 * 
	 * @param 待验证的字符串
	 * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
	 */
	public static boolean isIDcard(String str) {
		return match(IDCARD, str);
	}

	/**
	 * 验证输入整数和两位小数
	 * 
	 * @param 待验证的字符串
	 * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
	 */
	public static boolean isDecimal(String str) {
		String regex = "^[0-9]+(.[0-9]{2})?$";
		return match(regex, str);
	}

	/**
	 * 验证输入两位小数
	 * 
	 * @param 待验证的字符串
	 * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
	 */
	public static boolean isDecimal2(String str) {
		String regex = "^[0-9]+(.[0-9]{2})$";
		return match(regex, str);
	}

	/**
	 * 验证输入一年的12个月
	 * 
	 * @param 待验证的字符串
	 * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
	 */
	public static boolean isMonth(String str) {
		String regex = "^(0?[[1-9]|1[0-2])$";
		return match(regex, str);
	}

	/**
	 * 验证输入一个月的31天
	 * 
	 * @param 待验证的字符串
	 * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
	 */
	public static boolean isDay(String str) {
		String regex = "^((0?[1-9])|((1|2)[0-9])|30|31)$";
		return match(regex, str);
	}

	/**
	 * 验证日期时间
	 * 
	 * @param 待验证的字符串
	 * @return 如果是符合网址格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
	 */
	public static boolean isDate(String str) {
		return match(DATE_STRICT, str);
	}

	/**
	 * 验证数字输入
	 * 
	 * @param 待验证的字符串
	 * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
	 */
	public static boolean isNumber(String str) {
		String regex = "^[0-9]*$";
		return match(regex, str);
	}

	/**
	 * 验证非零的正整数
	 * 
	 * @param 待验证的字符串
	 * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
	 */
	public static boolean isIntNumber(String str) {
		String regex = "^\\+?[1-9][0-9]*$";
		return match(regex, str);
	}

	/**
	 * 验证汉字
	 * 
	 * @param 待验证的字符串
	 * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
	 */
	public static boolean isChinese(String str) {

		return match(CHINESE, str);
	}

	/**
	 * 验证数字
	 * 
	 * @param 待验证的字符串
	 * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
	 */
	public static boolean isNum(String str) {
		if(Validator.isNullEmpty(str)){
			return false;
		}
		return match(NUM, str);
	}

	/**
	 * @param regex
	 *            正则表达式字符串
	 * @param str
	 *            要匹配的字符串
	 * @return 如果str 符合 regex的正则表达式格式,返回true, 否则返回 false;
	 */
	private static boolean match(String regex, String str) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}

	/**
	 * 获取字符串的字节长度（中英文混合）
	 * 
	 * @param str
	 *            要匹配的字符串
	 * @return 字符串的字节数;
	 */
	public static int getWordCount(String s) {

		s = s.replaceAll("[^\\x00-\\xff]", "**");
		int length = s.length();
		return length;
	}

	/**
	 * 判断是否为空
	 * 
	 * @return
	 */
	public static boolean isEmpty(Object obj) {
		if (obj instanceof String) {
			return obj == null || ((String) obj).length() == 0;
		} else if (obj instanceof Object[]) {
			Object[] temp = (Object[]) obj;
			for (int i = 0; i < temp.length; i++) {
				if (!isEmpty(temp[i])) {
					return false;
				}
			}
			return true;
		}
		return obj == null;
	}

	/**
	 * <p>
	 * 验证字符串最大长度。
	 * </p>
	 * 
	 * @param src
	 *            字符串
	 * @param maxLength
	 *            最大长度
	 * @return true:半角<br>
	 *         false:全角
	 */
	public static boolean chkMaxLength(String src, int maxLength) {

		if (src == null) {
			return false;
		}
		int len = src.length();
		if (len > maxLength) {
			return false;
		}
		return true;
	}

	/**
	 * 数字验证
	 * 
	 * @param number
	 * @return
	 */
	public static boolean isNumber1(String number) {

		boolean isNumber = false;
		if (number != null) {

			int index = number.indexOf(",");
			if (index >= 0) {
				// 有逗号等分隔符的数字
				isNumber = number
						.matches("[0-9]+[0-9]*(,[0-9]{3})+(\\.[0-9]+)?");
			} else {
				isNumber = number.matches("[0-9]+[0-9]*(\\.[0-9]+)?");

			}

		}
		return isNumber;
	}
	
	/** 
     * 验证所有的身份证的合法性 
     *  
     * @param idcard 
     * @return 
     */ 
	public static boolean isValidatedAllIdcard(String idcard) { 
		/*if (idcard.length() == 15) {  
            idcard = this.convertIdcarBy15bit(idcard);  
        }*/
        return isValidate18Idcard(idcard);  
    }
	
	 /** 
     * <p> 
     * 判断18位身份证的合法性 
     * </p> 
     * 根据〖中华人民共和国国家标准GB11643-1999〗中有关公民身份号码的规定，公民身份号码是特征组合码，由十七位数字本体码和一位数字校验码组成。 
     * 排列顺序从左至右依次为：六位数字地址码，八位数字出生日期码，三位数字顺序码和一位数字校验码。 
     * <p> 
     * 顺序码: 表示在同一地址码所标识的区域范围内，对同年、同月、同 日出生的人编定的顺序号，顺序码的奇数分配给男性，偶数分配 给女性。 
     * </p> 
     * <p> 
     * 1.前1、2位数字表示：所在省份的代码； 2.第3、4位数字表示：所在城市的代码； 3.第5、6位数字表示：所在区县的代码； 
     * 4.第7~14位数字表示：出生年、月、日； 5.第15、16位数字表示：所在地的派出所的代码； 
     * 6.第17位数字表示性别：奇数表示男性，偶数表示女性； 
     * 7.第18位数字是校检码：也有的说是个人信息码，一般是随计算机的随机产生，用来检验身份证的正确性。校检码可以是0~9的数字，有时也用x表示。 
     * </p> 
     * <p> 
     * 第十八位数字(校验码)的计算方法为： 1.将前面的身份证号码17位数分别乘以不同的系数。从第一位到第十七位的系数分别为：7 9 10 5 8 4 
     * 2 1 6 3 7 9 10 5 8 4 2 
     * </p> 
     * <p> 
     * 2.将这17位数字和系数相乘的结果相加。 
     * </p> 
     * <p> 
     * 3.用加出来和除以11，看余数是多少？ 
     * </p> 
     * 4.余数只可能有0 1 2 3 4 5 6 7 8 9 10这11个数字。其分别对应的最后一位身份证的号码为1 0 X 9 8 7 6 5 4 3 
     * 2。 
     * <p> 
     * 5.通过上面得知如果余数是2，就会在身份证的第18位数字上出现罗马数字的Ⅹ。如果余数是10，身份证的最后一位号码就是2。 
     * </p> 
     *  
     * @param idcard 
     * @return 
     */  
    public static boolean isValidate18Idcard(String idcard) {  
        // 非18位为假  
        if (idcard.length() != 18) {  
            return false;  
        }  
        // 获取前17位  
        String idcard17 = idcard.substring(0, 17);  
        // 获取第18位  
        String idcard18Code = idcard.substring(17, 18);  
        char c[] = null;  
        String checkCode = "";  
        // 是否都为数字  
        if (isDigital(idcard17)) {  
            c = idcard17.toCharArray();  
        } else {  
            return false;  
        }  
  
        if (null != c) {  
            int bit[] = new int[idcard17.length()];  
  
            bit = converCharToInt(c);  
  
            int sum17 = 0;  
  
            sum17 = getPowerSum(bit);  
  
            // 将和值与11取模得到余数进行校验码判断  
            checkCode = getCheckCodeBySum(sum17);  
            if (null == checkCode) {  
                return false;  
            }  
            // 将身份证的第18位与算出来的校码进行匹配，不相等就为假  
            if (!idcard18Code.equalsIgnoreCase(checkCode)) {  
                return false;  
            }  
        }  
        return true;  
    }
    

	/** 
	 * 数字验证 
	 *  
	 * @param str 
	 * @return 
	 */  
	public static boolean isDigital(String str) {  
	    return str == null || "".equals(str) ? false : str.matches("^[0-9]*$");  
	}
	
	/** 
     * 将字符数组转为整型数组 
     *  
     * @param c 
     * @return 
     * @throws NumberFormatException 
     */  
    public static int[] converCharToInt(char[] c) throws NumberFormatException {  
        int[] a = new int[c.length];  
        int k = 0;  
        for (char temp : c) {  
            a[k++] = Integer.parseInt(String.valueOf(temp));  
        }  
        return a;  
    }  
    /** 
     * 将身份证的每位和对应位的加权因子相乘之后，再得到和值 
     *  
     * @param bit 
     * @return 
     */  
    public static int getPowerSum(int[] bit) {  
  
        int sum = 0;  
  
        if (power.length != bit.length) {  
            return sum;  
        }  
  
        for (int i = 0; i < bit.length; i++) {  
            for (int j = 0; j < power.length; j++) {  
                if (i == j) {  
                    sum = sum + bit[i] * power[j];  
                }  
            }  
        }  
        return sum;  
    }  
    /** 
     * 将和值与11取模得到余数进行校验码判断 
     *  
     * @param checkCode 
     * @param sum17 
     * @return 校验位 
     */  
    public static String getCheckCodeBySum(int sum17) {  
        String checkCode = null;  
        switch (sum17 % 11) {  
        case 10:  
            checkCode = "2";  
            break;  
        case 9:  
            checkCode = "3";  
            break;  
        case 8:  
            checkCode = "4";  
            break;  
        case 7:  
            checkCode = "5";  
            break;  
        case 6:  
            checkCode = "6";  
            break;  
        case 5:  
            checkCode = "7";  
            break;  
        case 4:  
            checkCode = "8";  
            break;  
        case 3:  
            checkCode = "9";  
            break;  
        case 2:  
            checkCode = "x";  
            break;  
        case 1:  
            checkCode = "0";  
            break;  
        case 0:  
            checkCode = "1";  
            break;  
        }  
        return checkCode;  
    } 
    
    /** 
     * 将15位的身份证转成18位身份证 
     *  
     * @param idcard 
     * @return 
     */  
    public static String convertIdcarBy15bit(String idcard) {  
        String idcard17 = null;  
        // 非15位身份证  
        if (idcard.length() != 15) {  
            return null;  
        }  
  
        if (isDigital(idcard)) {  
            // 获取出生年月日  
            String birthday = idcard.substring(6, 12);  
            Date birthdate = null;  
            try {  
                birthdate = new SimpleDateFormat("yyMMdd").parse(birthday);  
            } catch (ParseException e) {  
                e.printStackTrace();  
            }  
            Calendar cday = Calendar.getInstance();  
            cday.setTime(birthdate);  
            String year = String.valueOf(cday.get(Calendar.YEAR));  
  
            idcard17 = idcard.substring(0, 6) + year + idcard.substring(8);  
  
            char c[] = idcard17.toCharArray();  
            String checkCode = "";  
  
            if (null != c) {  
                int bit[] = new int[idcard17.length()];  
  
                // 将字符数组转为整型数组  
                bit = converCharToInt(c);  
                int sum17 = 0;  
                sum17 = getPowerSum(bit);  
  
                // 获取和值与11取模得到余数进行校验码  
                checkCode = getCheckCodeBySum(sum17);  
                // 获取不到校验位  
                if (null == checkCode) {  
                    return null;  
                }  
  
                // 将前17位与第18位校验码拼接  
                idcard17 += checkCode;  
            }  
        } else { // 身份证包含数字  
            return null;  
        }  
        return idcard17;  
    } 

	public static void main(String[] args) {
//		log.info(Validator.isUrlProtocol("https//123.123.20.30"));
		 String idcard18 = "121212121212121212";//18位  
		 System.out.println(isValidatedAllIdcard(idcard18));
		 
	}
}
