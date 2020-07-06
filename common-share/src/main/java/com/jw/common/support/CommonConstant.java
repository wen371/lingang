package com.jw.common.support;

/**
 * <p>
 * 系统程序用常量
 * </p>
 * 
 * @author wuxiaogang
 *
 */
public class CommonConstant {
	/** 数据库事务默认超时时间 */
	public static final int DB_DEFAULT_TIMEOUT = 300;
	/** 分页对象KEY */
	public static final String PAGEROW_OBJECT_KEY = "PAGEROW_OBJECT_KEY";
	/** 分页偏移量名称 */
	public static final String PAGEROW_OFFSET_NAME = "offset";
	/** 默认画面每页的记录数 */
	public static final int PAGEROW_DEFAULT_COUNT = 15;
	/** 画面显示的页码数量 */
	public static final int PAGEROW_CURR_NENT_COUNT = 15;

	// ================================微信+企业号====begin=============================
	/** SESSION里面存放企业账号信息 */
	public static final String SESSION_WECHAT_QIYE_BEAN = "SESSION_WECHAT_QIYE_BEAN_";
	/** SESSION里面存放企业账号信息 flag */
	public static final String SESSION_WECHAT_QIYE_BEAN_FLAG = "SESSION_WECHAT_QIYE_BEAN_FLAG_";
	/** 存放 微信企业账号的Access_token */
	public static final String SESSION_KEY_USER_WECHAT_QIYE_ACCESS_TOKEN = "SESSION_KEY_USER_WECHAT_QIYE_ACCESS_TOKEN";
	/** 存放 微信企业账号的Jsapi_ticket */
	public static final String SESSION_KEY_USER_WECHAT_QIYE_JSAPI_TICKET = "SESSION_KEY_USER_WECHAT_QIYE_JSAPI_TICKET";
	/** 存放 微信企业账号的应用套件令牌 suite_token */
	public static final String SESSION_KEY_USER_WECHAT_QIYE_SUITE_ACCESS_TOKEN = "SESSION_KEY_USER_WECHAT_QIYE_SUITE_ACCESS_TOKEN";
	/** 存放 微信企业号 【调用企业接口所需】的access_token */
	public static final String SESSION_KEY_USER_WECHAT_QIYE_CORP_ACCESS_TOKEN = "SESSION_KEY_USER_WECHAT_QIYE_CORP_ACCESS_TOKEN";
	
	public static final  int   EXPIRETIME=7100;
	// ================================微信+企业号=====end==============================
	
	/** 存放 米粒商户的Access_token */
	public static final String SESSION_KEY_USER_MERCHAN_ACCESS_TOKEN = "SESSION_KEY_USER_MERCHAN_ACCESS_TOKEN";
	
	
	/** 存放 微信服务号公共账号的Access_token 宝山旅游*/
	public static final String SESSION_KEY_USER_WECHAT_FWH_ACCESS_TOKEN = "SESSION_KEY_USER_WECHAT_FWH_ACCESS_TOKEN_YCLY";
	/** 存放 微信服务账号的Jsapi_ticket 宝山旅游*/
	public static final String SESSION_KEY_USER_WECHAT_FWH_JSAPI_TICKET = "SESSION_KEY_USER_WECHAT_FWH_JSAPI_TICKET_YCLY";
}
