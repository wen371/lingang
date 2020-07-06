package com.jw.sys.bean.entity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 
 * date : 2019-10-30 18:35:59
 * author : mapper generator
 * description : 微信推送信息
 * 
 **/
@Data
public class WechatPushInfo extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	**/
	private Integer id;

	/**
	 * 名称
	**/
	private String name;

	/**
	 * 描述
	**/
	private String instruction;

	/**
	 * 类别 (0-门票，1-自行车，2-观光车，3-游船)
	**/
	private String category;

	/**
	 * 0购买，1退票
	 **/
	private String operate;

	/**
	 * 创建日期
	**/
	private Date createTime;

	/**
	 * 建立者ID
	**/
	private Integer creator;

	/**
	 * 修改日期
	**/
	private Date modifyTime;

	/**
	 * 修改者ID
	**/
	private Integer mender;

	/**
	 * 删除：0否1是
	**/
	private String isDelete;

}