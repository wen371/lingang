package com.jw.common.support;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StrUtil {

	/**
	 * 
	 * @param str  原始字符串
	 * @param len  截取长度
	 * @param append_str   补足字符串  如...
	 * @return
	 */
	public static  String  subLeft(String str ,int len ,String append_str){
		 if (str == null) 
		 {
			return "";
		 }
//		int len1 = len;
		String result1 = "";
		str = str.replaceAll("　", "");
		str = str.trim();
		
		if(str.length()>len){
			result1=str.substring(0,len);
		}else{
			result1=str;
		}
		
//		int len2 = 0;
//		for (int i = 0; i < str.length(); i++) {
//			if (str.codePointAt(i) > 255) {
//				len2 += 2;
//			} else {
//				len2 += 1;
//			}
//		}
//		if (len2 > len1) {
//			for (int i = 0; i < len1; i++) {
//				if (str.codePointAt(i) > 255) {
//					len1 -= 1;
//				}
//			}
//			result1 = str.substring(0, len1);
//			if (append_str != null) {
//				result1 = result1 + append_str;
//			}
//		} else {
//			result1 = str.trim();
//		}
		return result1;// 返回文本字符串
	}
	/**
	 * 
	 * @param str  原始字符串
	 * @param s_num  开始
	 * @param e_num  结束
	 * @return
	 */
	public static  String  subStr(String str ,int s_num,int e_num){
		 if (str == null) {
			return "";
		 }
		if(str.length()>s_num&&str.length()>e_num && e_num>s_num){
			return str.substring(s_num,e_num);
		}else{
			return str;
		}
	}
    
	/**
	 * 清除html 获取纯文本
	 * 
	 * @param content
	 *            HTML文本代码
	 * @return 处理后的纯文本
	 */
	public static  String clearHtml(String strHtml) {
		if (strHtml == null) {
			return "";
		}
		String htmlStr =strHtml;
		String regEx_script="<script[^>]*?>[\\s\\S]*?<\\/script>"; //定义script的正则表达式 
        String regEx_style="<style[^>]*?>[\\s\\S]*?<\\/style>"; //定义style的正则表达式 
        String regEx_html="<[^>]+>"; //定义HTML标签的正则表达式 
        
        Pattern p_script=Pattern.compile(regEx_script,Pattern.CASE_INSENSITIVE); 
        Matcher m_script=p_script.matcher(htmlStr); 
        htmlStr=m_script.replaceAll(""); //过滤script标签 
        
        Pattern p_style=Pattern.compile(regEx_style,Pattern.CASE_INSENSITIVE); 
        Matcher m_style=p_style.matcher(htmlStr); 
        htmlStr=m_style.replaceAll(""); //过滤style标签 
        
        Pattern p_html=Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE); 
        Matcher m_html=p_html.matcher(htmlStr); 
        htmlStr=m_html.replaceAll(""); //过滤html标签 
        htmlStr=htmlStr.replaceAll("&\\w*?;", "");//	
        htmlStr=htmlStr.replaceAll("\\s*", "");
		// 各种样式
		return htmlStr;
	}
	/**
	 * 字符串替换
	 * @param str
	 * @return
	 */
	public static String replaceAll(String str,String regex,String replacement){
		if(str!=null){
			return str.replaceAll(regex, replacement);
		}
		return "";
	}
	/**
	 * 字符串转换为大写
	 * @param str
	 * @return
	 */
	public static String toUpperCase(String str){
		if(str!=null){
			return str.toUpperCase();
		}
		return "";
	}
	/**
	 * 字符串转换为小写
	 * @param str
	 * @return
	 */
	public static String toLowerCase(String str){
		if(str!=null){
			return str.toLowerCase();
		}
		return "";
	}
	/**
	 * InputStream 转为 String
	 * @param is
	 * @return
	 */
	public static String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));   
        StringBuilder sb = new StringBuilder();   
        String line = null;   
        try {   
            while ((line = reader.readLine()) != null) {   
                sb.append(line);   
            }   
        } catch (IOException e) {   
            //  e.printStackTrace();   
        } finally {   
            try {   
                is.close();   
            } catch (IOException e) {   
               // e.printStackTrace();   
            }   
        }   
        return sb.toString();   
    }
	//-------------------------------------------------------------------------
    private static final char[] hexDigit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
            'B', 'C', 'D', 'E', 'F' };

    private static char toHex(int nibble) {
        return hexDigit[(nibble & 0xF)];
    }

    /**
     * 将字符串编码成 Unicode 形式的字符串. 如 "黄" to "\u9EC4"
     * Converts unicodes to encoded \\uxxxx and escapes
     * special characters with a preceding slash
     * 
     * @param theString
     *        待转换成Unicode编码的字符串。
     * @param escapeSpace
     *        是否忽略空格，为true时在空格后面是否加个反斜杠。
     * @return 返回转换后Unicode编码的字符串。
     */
    public static String toUnicode(String theString, boolean escapeSpace) {
        int len = theString.length();
        int bufLen = len * 2;
        if (bufLen < 0) {
            bufLen = Integer.MAX_VALUE;
        }
        StringBuffer outBuffer = new StringBuffer(bufLen);

        for (int x = 0; x < len; x++) {
            char aChar = theString.charAt(x);
            // Handle common case first, selecting largest block that
            // avoids the specials below
            if ((aChar > 61) && (aChar < 127)) {
                if (aChar == '\\') {
                    outBuffer.append('\\');
                    outBuffer.append('\\');
                    continue;
                }
                outBuffer.append(aChar);
                continue;
            }
            
            switch (aChar) {
            case ' ':
                if (x == 0 || escapeSpace) outBuffer.append('\\');
                outBuffer.append(' ');
                break;
            case '\t':
                outBuffer.append('\\');
                outBuffer.append('t');
                break;
            case '\n':
                outBuffer.append('\\');
                outBuffer.append('n');
                break;
            case '\r':
                outBuffer.append('\\');
                outBuffer.append('r');
                break;
            case '\f':
                outBuffer.append('\\');
                outBuffer.append('f');
                break;
            case '=': // Fall through
            case ':': // Fall through
            case '#': // Fall through
            case '!':
                outBuffer.append('\\');
                outBuffer.append(aChar);
                break;
            default:
                if ((aChar < 0x0020) || (aChar > 0x007e)) {
                    // 每个unicode有16位，每四位对应的16进制从高位保存到低位
                    outBuffer.append('\\');
                    outBuffer.append('u');
                    outBuffer.append(toHex((aChar >> 12) & 0xF));
                    outBuffer.append(toHex((aChar >> 8) & 0xF));
                    outBuffer.append(toHex((aChar >> 4) & 0xF));
                    outBuffer.append(toHex(aChar & 0xF));
                } else {
                    outBuffer.append(aChar);
                }
            }
        }
        return outBuffer.toString();
    }

    /**
     * 从 Unicode 形式的字符串转换成对应的编码的特殊字符串。 如 "\u9EC4" to "黄".
     * Converts encoded \\uxxxx to unicode chars
     * and changes special saved chars to their original forms
     * 
     * @param in
     *        Unicode编码的字符数组。
     * @param off
     *        转换的起始偏移量。
     * @param len
     *        转换的字符长度。
     * @param convtBuf
     *        转换的缓存字符数组。
     * @return 完成转换，返回编码前的特殊字符串。
     */
    public static String unicodeToStr(char[] in, int off, int len) {
        char aChar;
        char[] out = new char[len]; // 只短不长
        int outLen = 0;
        int end = off + len;

        while (off < end) {
            aChar = in[off++];
            if (aChar == '\\') {
                aChar = in[off++];
                if (aChar == 'u') {
                    // Read the xxxx
                    int value = 0;
                    for (int i = 0; i < 4; i++) {
                        aChar = in[off++];
                        switch (aChar) {
                        case '0':
                        case '1':
                        case '2':
                        case '3':
                        case '4':
                        case '5':
                        case '6':
                        case '7':
                        case '8':
                        case '9':
                            value = (value << 4) + aChar - '0';
                            break;
                        case 'a':
                        case 'b':
                        case 'c':
                        case 'd':
                        case 'e':
                        case 'f':
                            value = (value << 4) + 10 + aChar - 'a';
                            break;
                        case 'A':
                        case 'B':
                        case 'C':
                        case 'D':
                        case 'E':
                        case 'F':
                            value = (value << 4) + 10 + aChar - 'A';
                            break;
                        default:
                            throw new IllegalArgumentException("Malformed \\uxxxx encoding.");
                        }
                    }
                    out[outLen++] = (char) value;
                } else {
                    if (aChar == 't') {
                        aChar = '\t';
                    } else if (aChar == 'r') {
                        aChar = '\r';
                    } else if (aChar == 'n') {
                        aChar = '\n';
                    } else if (aChar == 'f') {
                        aChar = '\f';
                    }
                    out[outLen++] = aChar;
                }
            } else {
                out[outLen++] = aChar;
            }
        }
        return new String(out, 0, outLen);
    }
    /**
	 * 将容易引起xss漏洞的半角字符直接替换成全角字符
	 * 
	 * @param s
	 * @return
	 */
	public static String xssEncode(String s) {
		if (s == null || s.isEmpty()) {
			return s;
		}
		StringBuilder sb = new StringBuilder(s.length() + 16);
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			switch (c) {
			case '>':
				sb.append("＞");// 转义大于号
				break;
			case '<':
				sb.append("＜");// 转义小于号
				break;
			case '\'':
				sb.append("＇");// 转义单引号
				break;
			case '\"':
				sb.append("＂");// 转义双引号
				break;
			case '&':
				sb.append("＆");// 转义&
				break;
			case '\\':
				sb.append("＼ ");// 转义斜杠＼
				break;
			case '#':
				sb.append("＃");// 转义＃
				break;
//			case '(':
//				sb.append("（");// 转义小括号（
//				break;
//			case ')':
//				sb.append("）");// 转义小括号）
//				break;
			default:
				sb.append(c);
				break;
			}
		}
		return sb.toString();
	}
	/**
	 * 首字母转小写
	 * @param s
	 * @return
	 */
	public static String toLowerCaseFirstOne(String s){
        if(Character.isLowerCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
    }
	/**
	 * 首字母转大写
	 * @param s
	 * @return
	 */
    public static String toUpperCaseFirstOne(String s){
    	if(s==null){
    		return "";
    	}
        if(Character.isUpperCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
    }
    /**
	 * 以某字符分割的所有单词 首字母大写
	 * @param s
	 * @return
	 */
    public static String toUpperCase(String str,String regex){
    	StringBuffer sb=new StringBuffer("");
    	
    	if(str==null){
    		return "";
    	}
    	String s[]=str.split(regex);
    	
    	if(s!=null){
    		for(String temp:s){
    			sb.append(toUpperCaseFirstOne(temp));
    		}
    	}
    	return sb.toString();
    }
    /**1   去除所有html标签  2清除html样式标签*/
    public static String koHtml(String str,String code)
  	{
    	// 截取长度
  		String len = "";
  		// 截取长度
  		String append_str = "";
  		// 去除标记类型
  	    if (str == null||"".equals(str)) {
  			return "";
  		}
  		if (!"2".equals(code)) { 
  			str = str.replaceAll("&nbsp;", "");
  			int len1 = str.length() - 1;
  			if (len != null) {
  				try {
  					len1 = Integer.parseInt(len);
  				} catch (NumberFormatException e) {
  					// e.printStackTrace();
  					len1 = str.length() - 1;
  				}
  			}
  			String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的正则表达式
  			String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式
  			String regEx_TS = "&{1}[a-zA-Z]*;"; // 以&开头以;结尾去掉HTML的&开头的转义字符－正则
  			String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式

  			Pattern p_script = Pattern.compile(regEx_script,
  					Pattern.CASE_INSENSITIVE);
  			Matcher m_script = p_script.matcher(str);
  			str = m_script.replaceAll(""); // 过滤script标签

  			Pattern p_style = Pattern.compile(regEx_style,
  					Pattern.CASE_INSENSITIVE);
  			Matcher m_style = p_style.matcher(str);
  			str = m_style.replaceAll(""); // 过滤style标签

  			Pattern p_TS = Pattern.compile(regEx_TS, Pattern.CASE_INSENSITIVE);
  			Matcher m_TS = p_TS.matcher(str);
  			str = m_TS.replaceAll(""); // 以&开头以;结尾去掉HTML的&开头的转义字符－正则

  			Pattern p_html = Pattern.compile(regEx_html,
  					Pattern.CASE_INSENSITIVE);
  			Matcher m_html = p_html.matcher(str);
  			str = m_html.replaceAll(""); // 过滤html标签

  			// //System.out.println(str);
  			String result1 = "";
  			str = str.replaceAll("　", "");
  			str = str.trim();
  			int len2 = 0;
  			for (int i = 0; i < str.length(); i++) {
  				if (str.codePointAt(i) > 255) {
  					len2 += 2;
  				} else {
  					len2 += 1;
  				}
  			}
  			if (len2 > len1) {
  				for (int i = 0; i < len1; i++) {
  					// //System.out.println(str.charAt(i)+"==="+str.codePointAt(i));
  					if (str.codePointAt(i) > 255) {
  						// //System.out.println("============================"+str.trim().length()+"===="+len1+"============================");
  						len1 -= 1;
  					}
  				}
  				// //System.out.println("============================"+str.trim().length()+"===="+len1+"============================");
  				result1 = str.substring(0, len1);
  				if (append_str != null) {
  					result1 = result1 + append_str;
  				}
  			} else {
  				result1 = str.trim();
  			}
  			return result1;// 返回文本字符串
  		} else {
  				return delTagsFContent(str);//返回处理后的html
  			
  		}
  	}

  	/**
  	 * 清除html css样式
  	 * 
  	 * @param content
  	 *            HTML文本代码
  	 * @return 处理后的html
  	 */
  	public static String delTagsFContent(String strHtml) {
  		if (strHtml == null) {
  			return "";
  		}
  		String strClear = strHtml
  				.replaceAll("<body><\\/body>", "$1"); // 读出body内里所有内容
  		String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式
  		Pattern p_style = Pattern.compile(regEx_style,
  				Pattern.CASE_INSENSITIVE);
  		Matcher m_style = p_style.matcher(strClear);
  		strClear = m_style.replaceAll(""); // 过滤style标签
  		
  		 
  		strClear = strClear.replaceAll("</?[^/?(br)|(p)|(img)|(table)|(ul)|(div)|(strong)|(BR)|(P)|(IMG)|(TABLE)|(UL)|(DIV)|(STRONG)][^><]*>", "");// 保留标签
  		 
          strClear=strClear.replaceAll("(\\<p+\\s*)[^\\>]*", "$1");//清除p 各种样式
  		strClear=strClear.replaceAll("(\\<span+\\s*)[^\\>]*", "$1");//清除pan 各种样式
  		strClear=strClear.replaceAll("(\\<SPAN+\\s*)[^\\>]*", "$1");//清除PAN 各种样式
  		strClear=strClear.replaceAll("<b>", "");
  		strClear=strClear.replaceAll("</b>", "");
  		// System.out.println(strClear);//输出结果
  		return strClear;
  	}
    
  	/**
	 * 将服务器相对地址转为物理地址
	 * 
	 * @param url
	 * @return
	 */
	public static String escapeRemoteToLocal(String url) {
		String local_url = null;
		if (url != null) {
			if (!Validator.isUrl(url)) {
				local_url = url.replaceAll(
						Resources.getData("UPLOAD_ROOT_FOLDER_URL"),
						Resources.getData("UPLOAD_ROOT_FOLDER")).replaceAll(
						"//", "/");
			}
		}
		return local_url;
	}

	/**
	 * 将物理地址转为服务器相对地址
	 * 
	 * @param url
	 * @return
	 */
	public static String escapeLocalToRemote(String url) {
		String remote_url = null;
		if (url != null) {
			if (!Validator.isUrl(url)){
				remote_url = url.replaceAll(
						Resources.getData("UPLOAD_ROOT_FOLDER"),
						Resources.getData("UPLOAD_ROOT_FOLDER_URL"))
						.replaceAll("//", "/");
			}
		}
		return remote_url;
	}

	
	
	/**
	 * @description 将字符串按指定字符转成数组
	 * @param 		(String)str
	 * @param 		(String)character
	 * @return		String[]
	 */
	public static String[] stringToArrByCharacter(String str, String character) {
		return str.split(character);
	}
	
	
	/**
	 * @description 将字符串按指定字符转成数组
	 * @param 		(String)str
	 * @param 		(String)corrCharacter
	 * @param 		(String)character
	 * @return		String[]
	 */
	public static String[] stringToArrByCharacter(String str, String corrCharacter, String character) {
		str.replaceAll(corrCharacter, character);
		return stringToArrByCharacter(str, character);
	}
	/**
	 *生成随机数
	 * @param min 最小值
	 * @param max 最大值
	 * @return
	 */
	public static  int  randomToStr(int  min,int  max){
		
        Random random = new Random();
        int s = random.nextInt(max)%(max-min+1) + min;
        return s;
        
	}
	
	/**
	 *生成guid
	 * @param min 最小值
	 * @param max 最大值
	 * @return
	 */
	public static  String  generateGuid(){
		Date date = new Date();
		String time=String.valueOf(date.getTime());
		Random random = new Random();
        int s = random.nextInt(999999)%(999999-0+1) + 0;
        String ran=String.valueOf(s);
        System.out.println("生成的加密前的guid:"+ran);
        return  MD5.md5(ran).toUpperCase();
        
    }

	public static String delfornt0(String args){
		String newStr = "";
		if(args!=null&&!args.equals("")) {
			newStr = args.replaceFirst("^0*", "");
			if(newStr.equals("")||newStr.startsWith(".")){
				newStr = "0"+newStr;
			}
		}
		return newStr;
	}
	public static void main(String[] args){
		System.out.println(delfornt0("000000.0"));
	}
}
