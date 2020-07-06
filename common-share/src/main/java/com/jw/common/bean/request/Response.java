package com.jw.common.bean.request;

import lombok.Data;

/**
 * 2019-08-15 11:00
 *
 * @author xiaodong
 */
@Data
public class Response {

    public String ret_code;

    public ResponseContent content;

    public String ret_msg;

    public String sign;
}
