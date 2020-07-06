package com.jw.admin.core.controller;


import com.jw.admin.core.tips.ErrorTip;
import com.jw.admin.core.tips.SuccessTip;
import com.jw.common.support.HttpKit;

import javax.servlet.http.HttpSession;


public class BaseController {
    protected void setAttr(String name, Object value) {
        HttpKit.getRequest().setAttribute(name, value);
    }
    protected String getPara(String name) {
        return HttpKit.getRequest().getParameter(name);
    }
    protected static SuccessTip SUCCESS_TIP = new SuccessTip();
    protected static ErrorTip ERROR_TIP = new ErrorTip();
    protected static String REDIRECT = "redirect:";
    protected static String FORWARD = "forward:";
    protected static String SUCCESS = "SUCCESS";
    protected static String ERROR = "ERROR";
    protected HttpSession getSession() {
        return HttpKit.getRequest().getSession();
    }
}
