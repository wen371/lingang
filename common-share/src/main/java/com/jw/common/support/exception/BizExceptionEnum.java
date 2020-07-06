package com.jw.common.support.exception;

/**
 * @Description 所有业务异常的枚举
 * @author fengshuonan
 * @date 2016年11月12日 下午5:04:51
 */
public enum BizExceptionEnum {

	/**
	 * 字典
	 */
	DICT_EXISTED(400,"字典已经存在"),
	ERROR_CREATE_DICT(500,"创建字典失败"),
	ERROR_WRAPPER_FIELD(500,"包装字典属性失败"),


	/**
	 * 文件上传
	 */
	FILE_READING_ERROR(400,"FILE_READING_ERROR!"),
	FILE_NOT_FOUND(400,"FILE_NOT_FOUND!"),
	UPLOAD_ERROR(500,"上传图片出错"),

	/**
	 * 权限和数据问题
	 */
	DB_RESOURCE_NULL(400,"数据库中没有该资源"),
	NO_PERMITION(405, "权限异常"),
	REQUEST_INVALIDATE(400,"请求数据格式不正确"),
	INVALID_KAPTCHA(400,"验证码不正确"),
	CANT_DELETE_ADMIN(600,"不能删除超级管理员"),
	CANT_FREEZE_ADMIN(600,"不能冻结超级管理员"),
	CANT_CHANGE_ADMIN(600,"不能修改超级管理员角色"),
	BEING_USED(600,"不能删除正在使用的角色"),
	NO_CLOSE_SHOP(600,"有下线，不能关店"),
	NO_CLOSE_SHOP_REPEAT(600,"已关店，不能再次关店"),
	NO_OPEN_SHOP_REPEAT(600,"已开店，不能再次开店"),

	/**
	 * 账户问题
	 */
	USER_ALREADY_REG(401,"该用户已经注册"),
	USER_ALREADY_IDNO(401,"该身份证已经注册"),
	USER_ALREADY_PHONE(401,"该手机号已经注册"),
	USER_ALREADY_IDNO_OR_PHONE_MEMBER(401,"该身份证或者手机号已经被出单员注册"),
	USER_ALREADY_IDNO_OR_PHONE_SALESMAN(401,"该身份证或者手机号已经店长注册"),
	NO_THIS_USER(400,"没有此用户"),
	USER_NOT_EXISTED(400, "没有此用户"),
	ACCOUNT_FREEZED(401, "账号被冻结"),
	OLD_PWD_NOT_RIGHT(402, "原密码不正确"),
	TWO_PWD_NOT_MATCH(405, "两次输入密码不一致"),
	EXISTED_THE_PWD(405, "新密码不得与此前三次密码重复"),
	Beyond_Limit(400,"新增超出位数"),
	ACCOUNT_HAS_FREEZED(401, "账户已冻结，不能进行此操作！"),
	ACCOUNT_NOT_FREEZED(401, "账户已启用，不能进行此操作！"),


	EXISTED_THE_WORK_POINT(400,"该网点代码已存在，不能添加"),
	EXISTED_THE_TREE_CODE(400,"该机构代码已存在，不能添加"),

	/**
	 * 角色问题
	 */
	ROLE_NOT_EXISTED(400, "未匹配到符合条件的角色"),

	/**
	 * 错误的请求
	 */
	MENU_PCODE_COINCIDENCE(400,"菜单编号和副编号不能一致"),
	EXISTED_THE_MENU(400,"编号重复，不能添加"),
	EXISTED_THE_Program(400,"栏目编号重复，不能添加"),
	DICT_MUST_BE_NUMBER(400,"字典的值必须为数字"),
	REQUEST_NULL(400, "请求有错误"),
	DELETE_NULL(400, "该数据已经修改成功,"),
	DELETE_ERROR(400, "操作错误,只能删除子栏目"),
	DELETE_ERROR_SUB(400, "该栏目下有子栏目，请先删除子栏目"),
	SESSION_TIMEOUT(400, "会话超时"),
	ORG_TINGJI(400, "停机中"),
	SERVER_ERROR(500, "服务器异常");



	BizExceptionEnum(int code, String message) {
		this.friendlyCode = code;
		this.friendlyMsg = message;
	}
	
	BizExceptionEnum(int code, String message, String urlPath) {
		this.friendlyCode = code;
		this.friendlyMsg = message;
		this.urlPath = urlPath;
	}

	private int friendlyCode;

	private String friendlyMsg;
	
	private String urlPath;

	public int getCode() {
		return friendlyCode;
	}

	public void setCode(int code) {
		this.friendlyCode = code;
	}

	public String getMessage() {
		return friendlyMsg;
	}

	public void setMessage(String message) {
		this.friendlyMsg = message;
	}

	public String getUrlPath() {
		return urlPath;
	}

	public void setUrlPath(String urlPath) {
		this.urlPath = urlPath;
	}

}
