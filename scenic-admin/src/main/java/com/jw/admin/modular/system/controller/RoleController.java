package com.jw.admin.modular.system.controller;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.jw.admin.core.annotion.BussinessLog;
import com.jw.admin.core.annotion.Permission;
import com.jw.admin.core.controller.BaseController;
import com.jw.admin.core.shiro.ShiroKit;
import com.jw.common.support.exception.BizExceptionEnum;
import com.jw.common.support.exception.BussinessException;
import com.jw.admin.core.tips.Tip;
import com.jw.admin.core.util.ToolUtil;
import com.jw.sys.api.IScenicSpotService;
import com.jw.sys.bean.vo.RoleVo;
import com.jw.sys.bean.vo.ScenicSpotVo;
import com.jw.sys.bean.vo.ZTreeNode;
import com.jw.common.constant.Const;
import com.jw.common.constant.Dict;
import com.jw.sys.api.IRoleService;
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
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 角色控制器
 *
 * @author fengshuonan
 * @Date 2017年2月12日21:59:14
 */
@Controller
@RequestMapping("/role")
@Slf4j
public class RoleController extends BaseController {

    private static String PREFIX = "/modular/system/role";

    @Resource
    private IRoleService roleService;
    @Resource
    private IUserService userService;

    @Resource
    IScenicSpotService scenicSpotService;

    /**
     * 跳转到角色列表页面
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "/role.html";
    }

    /**
     * 跳转到添加角色
     */
    @Permission
    @RequestMapping(value = "/role_add")
    public String roleAdd(Model model) {

        int scenicId = ShiroKit.getUser().getScenicId();
        Map<String, Object> map = new HashMap<>();
        List<ScenicSpotVo> resp1 = scenicSpotService.selectAllList(map);
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
        model.addAttribute("scenic",resp);
        return PREFIX + "/role_add.html";
    }

    /**
     * 跳转到修改角色
     */
    @Permission
    @RequestMapping(value = "/role_edit/{roleId}")
    public String roleEdit(@PathVariable Integer roleId, Model model) {
        if (ToolUtil.isEmpty(roleId)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        RoleVo role = this.roleService.selectById(roleId);
        if(role.getScenicId()!=null && role.getScenicId()==0){
            role.setScenicName("全部景区");
        }else{
            ScenicSpotVo scenicSpotVo = scenicSpotService.selectById(role.getScenicId());
            role.setScenicName(scenicSpotVo.getName());
        }
        model.addAttribute("role", role);
        return PREFIX + "/role_edit.html";
    }

    /**
     * 跳转到权限分配
     */
    @Permission
    @RequestMapping(value = "/role_menu/{roleId}")
    public String roleMenu(@PathVariable("roleId") Integer roleId, Model model) {
        if (ToolUtil.isEmpty(roleId)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        model.addAttribute("roleId", roleId);
        String name = roleService.getSingleRoleNameById(roleId);
        model.addAttribute("roleName", name);
        return PREFIX + "/role_menu.html";
    }

    /**
     * 获取角色列表
     */
    @Permission
    @RequestMapping(value = "/list")
    @ResponseBody
    public List<RoleVo> list(@RequestParam(required = false) String roleName) {
        List<RoleVo> roles = Lists.newArrayList();
        List<Integer> roleids = userService.selectRoleIdsByUserId( ShiroKit.getUser().getId());
        if(roleids.contains(1)){
             roles = roleService.selectRoles(super.getPara("roleName"),ShiroKit.getUser().getType(),ShiroKit.getUser().getScenicId());
        }else {
            for(Integer roleid:roleids){
                RoleVo roleVo = new RoleVo();
                roleVo = roleService.selectByIdAndName(roleid,super.getPara("roleName"),ShiroKit.getUser().getType(),ShiroKit.getUser().getScenicId());
                if(roleVo!=null){
                    roles.add(roleVo);
                }
            }
        }
        log.info("roles" + JSON.toJSONString(roles));

        for(RoleVo roleVo:roles){
            if(roleVo.getScenicId()!=null && roleVo.getScenicId()==0){
                roleVo.setScenicName("全部景区");
            }else{
                ScenicSpotVo scenicSpotVo = scenicSpotService.selectById(roleVo.getScenicId());
                roleVo.setScenicName(scenicSpotVo.getName());
            }
        }

        /*for(RoleVo roleVo:roles){
            if(deptService.selectById(roleVo.getDeptid())!=null) {
                roleVo.setDeptName(deptService.selectById(roleVo.getDeptid()).getSimplename());
            }else{
                System.out.print("roleVo.getDeptId()"+roleVo.getDeptid()+"!!!!!!");

            }
        }*/

        log.info("roles: "+JSON.toJSONString(roles));
        return roles;
    }

    /**
     * 角色新增
     */
    @Permission(Const.ADMIN_NAME)
    @RequestMapping(value = "/add")
    @BussinessLog(value = "添加角色", key = "name", dict = "RoleDict")
    @ResponseBody
    public Tip add(@Valid RoleVo role, BindingResult result) {
        log.info("role: "+JSON.toJSONString(role));

        if (result.hasErrors()) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        role.setCreator(ShiroKit.getUser().getId());
        role.setMender(ShiroKit.getUser().getId());
        role.setType(ShiroKit.getUser().getType());
        /*role.setType(ShiroKit.getUser().getType());*/
        this.roleService.insert(role);
        return SUCCESS_TIP;
    }

    /**
     * 角色修改
     */
    @Permission(Const.ADMIN_NAME)
    @RequestMapping(value = "/edit")
    @BussinessLog(value = "修改角色", key = "name", dict = "RoleDict")
    @ResponseBody
    public Tip edit(@Valid RoleVo role, BindingResult result) {
        if (result.hasErrors()) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        role.setMender(ShiroKit.getUser().getId());
        this.roleService.updateById(role);

        //删除缓存
        //CacheKit.removeAll(Cache.CONSTANT);
        return SUCCESS_TIP;
    }

    /**
     * 删除角色
     */
    @Permission(Const.ADMIN_NAME)
    @RequestMapping(value = "/remove")
    @BussinessLog(value = "删除角色", key = "roleId", dict = "DeleteDict")
    @ResponseBody
    public Tip remove(@RequestParam Integer roleId) {
        if (ToolUtil.isEmpty(roleId)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }

        //不能删除超级管理员角色
        if (roleId == 1) {
            throw new BussinessException(BizExceptionEnum.CANT_DELETE_ADMIN);
        }

        //缓存被删除的角色名称
        //LogObjectHolder.me().set(ConstantFactory.me().getSingleRoleName(roleId));
        if (!this.roleService.delRoleById(roleId, ShiroKit.getUser().getId())) {
            throw new BussinessException(BizExceptionEnum.BEING_USED);
        }

        //删除缓存
        //CacheKit.removeAll(Cache.CONSTANT);
        return SUCCESS_TIP;
    }

    /**
     * 查看角色
     */
    @RequestMapping(value = "/view/{roleId}")
    @ResponseBody
    public Tip view(@PathVariable Integer roleId) {
        if (ToolUtil.isEmpty(roleId)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        this.roleService.selectById(roleId);
        return SUCCESS_TIP;
    }

    /**
     * 配置权限
     */
    @RequestMapping("/setAuthority")
    @BussinessLog(value = "配置权限", key = "roleId,ids", dict = Dict.RoleDict)
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Tip setAuthority(@RequestParam("roleId") Integer roleId, @RequestParam("ids") String ids) {
        if (ToolUtil.isOneEmpty(roleId)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        this.roleService.setAuthority(roleId, ids, ShiroKit.getUser().getId());
        return SUCCESS_TIP;
    }

    /**
     * 获取角色列表
     */
    @RequestMapping(value = "/roleTreeList")
    @ResponseBody
    public List<ZTreeNode> roleTreeList() {
        List<ZTreeNode> roleTreeList = this.roleService.roleTreeList(ShiroKit.getUser().getType());
        roleTreeList.add(ZTreeNode.createParent());
        return roleTreeList;
    }

    /**
     * 获取角色列表
     */
    @RequestMapping(value = "/roleTreeListByUserId/{userId}")
    @ResponseBody
    public List<ZTreeNode> roleTreeListByUserId(@PathVariable Integer userId) {
        List<Integer> roleids = userService.selectRoleIdsByUserId(userId);
        List<Integer> roleids1 = userService.selectRoleIdsByUserId(ShiroKit.getUser().getId());
        try {
            if (ToolUtil.isEmpty(roleids)) {
                if (roleids1.contains(1)) {
                    List<ZTreeNode> roleTreeList = this.roleService.roleTreeList(ShiroKit.getUser().getType());
                    return roleTreeList;
                } else {
                    List<ZTreeNode> roleTreeList = this.roleService.roleTreeListByUserId(ShiroKit.getUser().getId(), ShiroKit.getUser().getType());
                    return roleTreeList;
                }

            } else {
                if (roleids1.contains(1)) {
                    List<ZTreeNode> roleTreeListByUserId = this.roleService.roleTreeListByRoleId(roleids, ShiroKit.getUser().getType());
                    return roleTreeListByUserId;
                } else {
                    List<ZTreeNode> roleTreeListByUserId = this.roleService.roleTreeListByRoleIdAndUserId(roleids, ShiroKit.getUser().getType(), ShiroKit.getUser().getId());
                    return roleTreeListByUserId;
                }

            }
        }catch (Exception e){
            List<ZTreeNode> roleTreeList = this.roleService.roleTreeList(ShiroKit.getUser().getType());
            e.printStackTrace();
            return roleTreeList;

        }
    }

    @RequestMapping(value = "/judgeRole")
    @ResponseBody
    public Map<String,String> judgeRole() {
        Map<String,String>map = new HashMap<>();
        map.put("code","100");
        List<Integer> roleids1 = userService.selectRoleIdsByUserId(ShiroKit.getUser().getId());
        if(roleids1.contains(1)){
            map.put("code","200");
        }
        return map;
    }

}
