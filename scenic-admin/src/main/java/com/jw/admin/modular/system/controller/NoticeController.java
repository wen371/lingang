package com.jw.admin.modular.system.controller;

import com.jw.admin.core.annotion.BussinessLog;
import com.jw.admin.core.controller.BaseController;
import com.jw.admin.core.util.ToolUtil;
import com.jw.sys.bean.vo.NoticeVo;
import com.jw.sys.api.NoticeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 通知控制器
 *
 * @author fengshuonan
 * @Date 2017-05-09 23:02:21
 */
@Controller
@RequestMapping("/notice")
public class NoticeController extends BaseController {

    private String PREFIX = "/modular/system/notice/";

    @Resource
    private NoticeService noticeService;



    /**
     * 跳转到通知列表首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "notice.html";
    }

    /**
     * 跳转到添加通知
     */
    @RequestMapping("/notice_add")
    public String noticeAdd() {
        return PREFIX + "notice_add.html";
    }

    /**
     * 跳转到修改通知
     */
    @RequestMapping("/notice_update/{noticeId}")
    public String noticeUpdate(@PathVariable Integer noticeId, Model model) {
        NoticeVo notice = this.noticeService.selectById(noticeId);
        model.addAttribute("notice",notice);
        //LogObjectHolder.me().set(notice);
        return PREFIX + "notice_edit.html";
    }

    /**
     * 跳转到首页通知
     */
    @RequestMapping("/hello")
    public String hello() {
        List<NoticeVo> notices = noticeService.listAll();
        super.setAttr("noticeList",notices);
        return "/blackboard.html";
    }

    /**
     * 获取通知列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public List<NoticeVo> list(String condition) {
        List<NoticeVo> list = this.noticeService.list(condition);
        return list;
    }

    /**
     * 新增通知
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    @BussinessLog(value = "新增通知",key = "title",dict = "NoticeMap")
    public String  add(NoticeVo notice) {
        if (ToolUtil.isOneEmpty(notice, notice.getTitle(), notice.getContent())) {
            return null;
        }
        notice.setCreatetime(new Date());
        noticeService.insert(notice);
        return "SUCCESS_TIP";
    }

    /**
     * 删除通知
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    @BussinessLog(value = "删除通知",key = "noticeId",dict = "DeleteDict")
    public String delete(@RequestParam Integer noticeId) {

        //缓存通知名称
        //LogObjectHolder.me().set(ConstantFactory.me().getNoticeTitle(noticeId));

        this.noticeService.deleteById(noticeId);

        return "SUCCESS_TIP";
    }

    /**
     * 修改通知
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    @BussinessLog(value = "修改通知",key = "title",dict = "NoticeMap")
    public Object update(NoticeVo notice) {
        if (ToolUtil.isOneEmpty(notice, notice.getId(), notice.getTitle(), notice.getContent())) {
            return null;
        }
        NoticeVo old = this.noticeService.selectById(notice.getId());
        old.setTitle(notice.getTitle());
        old.setContent(notice.getContent());
        noticeService.updateById(old.getId());
        return "SUCCESS_TIP";
    }

}
