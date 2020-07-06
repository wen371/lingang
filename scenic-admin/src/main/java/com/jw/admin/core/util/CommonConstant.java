package com.jw.admin.core.util;

public class CommonConstant {
    /** 数据库事务默认超时时间 */
    public static final int DB_DEFAULT_TIMEOUT = 300;
    /** 分页对象KEY */
    public static final String PAGEROW_OBJECT_KEY = "PAGEROW_OBJECT_KEY";
//	/** 分页偏移量名称 */
//	public static final String PAGEROW_OFFSET_NAME = "offset";
    /** 默认画面每页的记录数 */
    public static final int PAGEROW_DEFAULT_COUNT = 15;
    /** 画面显示的页码数量 */
    public static final int PAGEROW_CURR_NENT_COUNT = 15;
//	/** SESSION里面存放认证码 */
//	public static final String SESSION_VERIFY_CODE = "SESSION_VERIFY_CODE";
//	/** SESSION里面存放 用户信息 */
//	public static final String SESSION_KEY_USER = "SESSION_KEY_USER";
//	/** SESSION里面存放 用户名 提供监控页面显示 */
//	public static final String SESSION_KEY_USERNAME = "SESSION_KEY_USERNAME";
//	/** SESSION里面存放 系统用户信息 */
//	public static final String SESSION_KEY_USER_ADMIN = "SESSION_KEY_USER_ADMIN";
//	/** SESSION里面存放 会员用户信息 */
//	public static final String SESSION_KEY_USER_MEMBER = "SESSION_KEY_USER_MEMBER";
    /** 路径分隔符 */
    public static final String PATH_SEPARATOR = "/";
    /** 系统默认编码 */
    public static final String DEFAULT_ENCODE = "UTF-8";
    /** 空字符串 */
    public static final String EMPTY_STRING = "";
    /** 空格 */
    public static final String BLANK_STRING = " ";
    /** 异常信息 */
    public static final String LOG_ERROR_TITLE = "异常信息";


    public static final String JWT_HEADER_TOKEN_KEY = "Authorization";
    public static final String JWT_ID = "jwt";
    public static final String JWT_SECRET_KEY = "43455454gjixiuowrmkhdiuhs#^&(klefk!";
    public static final long JWT_TTL = 2 * 60 * 60 * 1000;  //jwt过期时间 毫秒
    public static final long JWT_TTL_REFRESH = 2 * 55 * 60 * 1000;  //jwt刷新 毫秒

    public static final String FEIGN_ERROR_SYMBOL_STRING = "[Symbol/]";

    /** 存放 微信公共账号的Access_token */
    public static final String SESSION_KEY_USER_WECHAT_FWH_ACCESS_TOKEN = "SESSION_KEY_USER_WECHAT_FWH_ACCESS_TOKEN";



    /**token超时时间  秒*/
    public static final  int   EXPIRETIME=7100;
}
