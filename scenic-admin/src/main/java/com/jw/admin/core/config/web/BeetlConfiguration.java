package com.jw.admin.core.config.web;

import com.jw.admin.core.util.KaptchaUtil;
import com.jw.common.support.ToolUtil;
import org.beetl.ext.spring.BeetlGroupUtilConfiguration;

import java.util.HashMap;
import java.util.Map;

public class BeetlConfiguration extends BeetlGroupUtilConfiguration {

    @Override
    public void initOther() {
        groupTemplate.registerFunctionPackage("shiro", new ShiroExt());
        groupTemplate.registerFunctionPackage("kaptcha", new KaptchaUtil());
        groupTemplate.registerFunctionPackage("tool", new ToolUtil());
        // 共享变量
        Map<String,Object> shared = new HashMap<String,Object>();
        shared.put("version", ToolUtil.getRandomInt());
        groupTemplate.setSharedVars(shared);
    }

}
