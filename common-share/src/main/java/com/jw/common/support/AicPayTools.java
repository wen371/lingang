package com.jw.common.support;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * 米粒支付工具类
 * 
 * @author wanmei
 *
 */

/**
 * @author wanmei
 *
 */
@Slf4j
public class AicPayTools {

	/** 切换米粒支付机构 key */
	private static final String aicpayDepart_Key = "aicpay.depart";

	/** 是否切换米粒支付机构*/
	public static String isAicPay(String branchCode) {
		String departs = Resources.getData(aicpayDepart_Key);
		System.out.println("departs : " + departs);

		if (StringUtils.isBlank(departs)) {
			log.error("未从配置文件【resources.properties】中获取到切换米粒支付机构的配置********");
			return "0";
		}
		if (departs.contains("'" + branchCode + "'")) {
			return "1";
		}

		return "0";
	}

}
