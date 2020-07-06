package com.jw.admin.modular.system.controller;

import com.jw.admin.core.controller.BaseController;
import com.jw.sys.bean.vo.NoticeVo;
import com.jw.sys.api.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 总览信息
 *
 * @author fengshuonan
 * @Date 2017年3月4日23:05:54
 */
@Controller
@RequestMapping("/blackboard")
public class BlackboardController extends BaseController {

    @Autowired
    NoticeService noticeService;

    /**
     * 跳转到黑板
     */
    @RequestMapping("")
    public String blackboard(Model model) {
        List<NoticeVo> notices = noticeService.list(null);
        model.addAttribute("noticeList",notices);
        return "/blackboard.html";
    }
}
