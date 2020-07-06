package com.jw.admin.core.tips;

/**
 * 返回给前台的成功提示
 *
 * @author fengshuonan
 * @date 2016年11月12日 下午5:05:22
 */
public class SuccessTip extends Tip {
	
	public SuccessTip(){
		super.code = 200;
		super.message = "操作成功";
	}

	public SuccessTip(Integer code,String message){
		super();
		this.code = code;
		this.message = message;
	}

	public SuccessTip(Integer code,String message,String info){
		super();
		this.code = code;
		this.message = message;
		this.info = info;
	}
}
