package com.jw.common.bean.request;

import lombok.Data;

/**
 * 2019-08-15 11:02
 *
 * @author xiaodong
 */
@Data
public class ResponseContent {

    public String rtn_flag;

    public ResponseData rtn_data;

    public String rtn_msg;
}
