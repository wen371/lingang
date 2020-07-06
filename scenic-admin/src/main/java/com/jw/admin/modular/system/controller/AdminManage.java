package com.jw.admin.modular.system.controller;

import com.jw.admin.core.annotion.BussinessLog;
import com.jw.admin.core.annotion.Permission;
import com.jw.admin.core.controller.BaseController;
import com.jw.sys.api.IRoleService;
import com.jw.admin.core.shiro.ShiroKit;
import com.jw.admin.core.tips.Tip;
import com.jw.admin.core.util.ToolUtil;
import com.jw.common.support.exception.BizExceptionEnum;
import com.jw.common.support.exception.BussinessException;
import com.jw.sys.api.IScenicSpotService;
import com.jw.sys.bean.vo.ScenicSpotVo;
import com.jw.sys.bean.vo.UserVo;
import com.jw.sys.api.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/admin")
public class AdminManage extends BaseController {
    private static String PREFIX = "/modular/system/admin/";

    @Resource
    private IUserService userService;
    @Resource
    private IRoleService roleService;

    @Resource
    IScenicSpotService scenicSpotService;

    /**
     * 跳转到查看管理员列表的页面
     */
    @RequestMapping("")
    public String index(Model model) {
        return PREFIX + "admin.html";
    }

    /**
     * 跳转到查看管理员列表的页面
     */
    @RequestMapping("/admin_add")
    public String addView(Model model,HttpServletRequest request) {
        UserVo userVo = new UserVo();
        //Map<String, Object> map = new HashMap<>();
        //int scenicId = ShiroKit.getUser().getScenicId();
        //List<ScenicSpotVo> resp1 = scenicSpotService.selectAllList(map);
        int scenicId = 0 ;
        List<ScenicSpotVo> resp1 = new ArrayList<>();
        ScenicSpotVo allScenicSpotVo = new ScenicSpotVo();
        allScenicSpotVo.setName("全部景区");
        allScenicSpotVo.setId(0);
        resp1.add(allScenicSpotVo);
        List<ScenicSpotVo> resp = new ArrayList<>();
        if(scenicId == 0){
            resp = resp1;
        }else{
            for(ScenicSpotVo scenicSpotVo:resp1){
                if(scenicSpotVo.getId() == scenicId){
                    resp.add(scenicSpotVo);
                    break;
                }
            }
        }
        model.addAttribute("user",userVo);
        model.addAttribute("scenic",resp);
        return PREFIX + "admin_add.html";
    }/**
     * 跳转到编辑管理员页面
     */
    @Permission
    @RequestMapping("/admin_edit/{userId}")
    public String userEdit(@PathVariable Integer userId, Model model) {
        if (ToolUtil.isEmpty(userId)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        UserVo user = userService.selectById(userId);
        if(user.getScenicId()!=null && user.getScenicId() == 0){
            user.setScenicName("全部景区");
        }else{
            ScenicSpotVo scenicSpotVo =  scenicSpotService.selectById(user.getScenicId());
            user.setScenicName(scenicSpotVo.getName());
        }
        model.addAttribute("user", user);
        return PREFIX + "admin_edit.html";
    }

    /**
     * 修改
     */
    @RequestMapping("/edit")
    @ResponseBody
    @BussinessLog(value = "修改管理员")
    public Tip edit(@Valid UserVo user, BindingResult result) {
        log.info(user + "修改管理员");
        if (result.hasErrors()) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        int id = ShiroKit.getUser().getId();
        // 完善账号信息
        user.setMender(id);
        UserVo userVo =  userService.getByAccountAndId(user.getAccount(),user.getId());
        if(userVo!=null){
            log.info("账号已存在");
            throw new BussinessException(BizExceptionEnum.USER_ALREADY_REG);
        }
        List<UserVo> userVos = userService.getUserByIdNo(user.getIdNo(),ShiroKit.getUser().getType());
        if(userVos!=null) {
            for (UserVo userVo1 : userVos) {
                if (userVo1.getId().toString().equals(user.getId().toString())) {
                    log.info("身份证已存在");
                    throw new BussinessException(BizExceptionEnum.USER_ALREADY_IDNO);
                }
            }
        }
        List<UserVo> userVos1 = userService.selectByPhone(user.getPhone(),ShiroKit.getUser().getType());
        for(UserVo userVo1:userVos1){
            if(!(userVo1.getId()+"").equals(user.getId()+"")){
                log.info("手机号已存在");
                throw new BussinessException(BizExceptionEnum.USER_ALREADY_PHONE);
            }
        }
        //user.setRoleid("5");
        userService.update(user);
        return SUCCESS_TIP;
    }

    /**
     * 添加管理员
     */
    @RequestMapping("/add")
    @ResponseBody
    @BussinessLog(value = "添加管理员")
    public Tip add(@Valid UserVo user, BindingResult result) {
        System.out.println(user + "添加管理员");
       if (result.hasErrors()) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
         // 判断账号是否重复
        UserVo theUser = userService.getByAccountAndType(user.getAccount(),ShiroKit.getUser().getType());
        if (theUser != null) {
            throw new BussinessException(BizExceptionEnum.USER_ALREADY_REG);
        }
        // 判断身份证是否重复
        int count = userService.getUserByIdNoCount(user.getIdNo(),ShiroKit.getUser().getType());
        if(count>0){
            log.info("身份证已存在");
            throw new BussinessException(BizExceptionEnum.USER_ALREADY_IDNO);
        }
        List<UserVo> userVos1 = userService.selectByPhone(user.getPhone(),ShiroKit.getUser().getType());
        if(userVos1!=null&&userVos1.size()>0){
            log.info("手机号已存在");
            throw new BussinessException(BizExceptionEnum.USER_ALREADY_PHONE);
        }
        int id = ShiroKit.getUser().getId();
        // 完善账号信息
        user.setCreator(id);
        user.setMender(id);
        user.setSalt(ToolUtil.getRandomString(5));
        user.setPassword(ShiroKit.md5("As12345678", user.getSalt()));
        user.setStatus(4);
        //user.setDeptId(24);
        userService.insert(user);
        return SUCCESS_TIP;
    }

    /**
     * 查询管理员列表
     */
    @RequestMapping("/list")
    @ResponseBody
    @Permission
    public Map<String,Object> list(@RequestParam(required = false) String name, @RequestParam(required = false) String beginTime, @RequestParam(required = false) String endTime, @RequestParam(required = false) Integer pageNo, @RequestParam(required = false) Integer pageSize) {
        System.out.println("加载管理员界面 ");
        Integer num = (pageNo-1)*pageSize;
        Map<String,Object> map = new HashMap<>();
        List<UserVo> users  = userService.selectSubByInnerCode(name,beginTime,endTime,"",num,pageSize,ShiroKit.getUser().getType(),ShiroKit.getUser().getScenicId());
        int total = userService.countSubByInnerCode(name,beginTime,endTime,"",ShiroKit.getUser().getType(),ShiroKit.getUser().getScenicId());
        for (UserVo userVo : users) {
            List<Integer> roleIdList = userService.selectRoleIdsByUserId(userVo.getId());
            String roleName = "";
            for (Integer roleId : roleIdList) {
                roleName += roleService.selectById(roleId).getTips() + ",";
            }
            if (roleName.length() > 1) {
                roleName = roleName.substring(0, roleName.length() - 1);
            }
            userVo.setRoleName(roleName);

            if(userVo.getScenicId()!=null &&userVo.getScenicId() == 0){
                userVo.setScenicName("全部景区");
            }else{
                ScenicSpotVo scenicSpotVo =  scenicSpotService.selectById(userVo.getScenicId());
                userVo.setScenicName(scenicSpotVo.getName());
            }



        }
            map.put("total",total);
            map.put("rows",users);
            return map;
    }
}
