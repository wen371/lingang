package com.jw.admin.modular.system.controller;


import com.jw.admin.core.controller.BaseController;
import com.jw.common.support.ToolUtil;
import com.jw.sys.bean.vo.SysAdminLogVo;
import com.jw.sys.api.ISysAdminLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/adminlog")
public class SysAdminLogController extends BaseController {
    private static String PREFIX = "/modular/system/adminLog/";

    @Resource
    private ISysAdminLogService sysAdminLogService;

    @RequestMapping("")
    public String index(Model model) {
        return PREFIX + "adminLogList.html";
    }

    @RequestMapping("/list")
    @ResponseBody
    public Map<String, Object> map(@RequestParam(required = false) Integer pageNo, @RequestParam(required = false) Integer pageSize,@RequestParam(required = false) String describe,@RequestParam(required = false) String createTime) {
        Integer num = (pageNo - 1) * pageSize;
        Map<String, Object> map = new HashMap();
        if (ToolUtil.isNotEmpty(createTime)){
            createTime = createTime.substring(0,14);
            log.info("createTime:{}",createTime);
        }
        List<SysAdminLogVo> list = sysAdminLogService.findSysAdminLogs(num, pageSize,describe,createTime);
        int total = sysAdminLogService.getAdminLogCount(describe,createTime);
        map.put("rows", list);
        map.put("total", total);
        return map;
    }


}
