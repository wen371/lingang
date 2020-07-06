package com.jw.admin.modular.system.controller;


import com.jw.admin.core.annotion.BussinessLog;
import com.jw.admin.core.annotion.Permission;
import com.jw.admin.core.controller.BaseController;
import com.jw.admin.core.shiro.ShiroKit;
import com.jw.common.support.BeanKit;
import com.jw.common.support.exception.BizExceptionEnum;
import com.jw.common.support.exception.BussinessException;
import com.jw.admin.core.tips.Tip;
import com.jw.admin.core.util.ToolUtil;
import com.jw.sys.bean.vo.MenuVo;
import com.jw.sys.bean.vo.ZTreeNode;
import com.jw.common.constant.Const;
import com.jw.common.constant.state.MenuStatus;
import com.jw.sys.api.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 菜单控制器
 *
 * @author fengshuonan
 * @Date 2017年2月12日21:59:14
 */
@Controller
@RequestMapping("/menu")
public class MenuController extends BaseController {

    private static String PREFIX = "/modular/system/menu/";


    @Autowired
    private IMenuService menuService;


    /**
     * 跳转到菜单列表列表页面
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "menu.html";
    }

    /**
     * 跳转到菜单列表列表页面
     */

    @RequestMapping(value = "/menu_add")
    public String menuAdd() {
        return PREFIX + "menu_add.html";
    }

    /**
     * 跳转到菜单详情列表页面
     */
    @Permission(Const.ADMIN_NAME)
    @RequestMapping(value = "/menu_edit/{menuId}")
    public String menuEdit(@PathVariable Integer menuId, Model model) {
        if (ToolUtil.isEmpty(menuId)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        MenuVo menu = menuService.selectById(menuId);

        //获取父级菜单的id
        //MenuVo temp = new MenuVo();
        MenuVo pMenu = menuService.selectPMenuByPcode(menu.getPcode());

        //如果父级是顶级菜单
        if (pMenu == null) {
            menu.setPcode("0");
        } else {
            //设置父级菜单的code为父级菜单的id
            menu.setPcode(String.valueOf(pMenu.getId()));
        }

        Map<String, Object> menuMap = BeanKit.beanToMap(menu);
        String codeName = menuService.getMenuNameByCode(pMenu.getCode());
        if (null != codeName) {
            menuMap.put("pcodeName", codeName);
        } else {
            menuMap.put("pcodeName", "顶级");
        }

        model.addAttribute("menu", menuMap);
        //model.addAttribute("pcodeName",codeName);

        return PREFIX + "menu_edit.html";
    }

    /**
     * 修改菜单
     */
    @Permission(Const.ADMIN_NAME)
    @RequestMapping(value = "/edit")
    @BussinessLog(value = "修改菜单", key = "name", dict = "DeptDict")
    @ResponseBody
    public Tip edit(@Valid MenuVo menu, BindingResult result) {
        if (result.hasErrors()) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        //设置父级菜单编号
        String pCode = "0";
        if (null == menu.getPcode() || pCode.equals(menu.getPcode())) {
            menu.setPcode("0");
            menu.setLevels(1);
        } else {
            menuSetPcode(menu);
        }
        menu.setMender(ShiroKit.getUser().getId());
        menuService.updateById(menu);
        return SUCCESS_TIP;
    }


    /**
     * 获取菜单列表
     */
    @Permission(Const.ADMIN_NAME)
    @RequestMapping(value = "/list")
    @ResponseBody
    public List<MenuVo> list(@RequestParam(required = false) String menuName, @RequestParam(required = false) String level) {
        System.out.println("首页菜单加载11   " + ShiroKit.getUser().getType());
        List<MenuVo> list = menuService.selectMenus(menuName, level, ShiroKit.getUser().getType());
        return list;
    }

    /*  */

    /**
     * 新增菜单
     */
    @Permission(Const.ADMIN_NAME)
    @RequestMapping(value = "/add")
    @BussinessLog(value = "菜单新增", key = "name", dict = "MenuDict")
    @ResponseBody
    public MenuVo add(@Valid MenuVo menu, BindingResult result) {

        if (result.hasErrors()) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }

        //判断是否存在该编号
        String existedMenuName = menuService.getMenuNameByCode(menu.getCode());
        if (ToolUtil.isNotEmpty(existedMenuName)) {
            throw new BussinessException(BizExceptionEnum.EXISTED_THE_MENU);
        }
        //设置父级菜单编号
        menuSetPcode(menu);
        menu.setStatus(MenuStatus.ENABLE.getCode());
        menu.setMender(ShiroKit.getUser().getId());
        menu.setCreator(ShiroKit.getUser().getId());
        menu.setType(ShiroKit.getUser().getType());
        System.out.println("新增菜单:" + menu.getName() + ",pcode" + menu.getPcode() + ",等级:" + menu.getLevels());
        menuService.insert(menu);
        return menu;
    }

    /**
     * 删除菜单
     */
    @Permission(Const.ADMIN_NAME)
    @RequestMapping(value = "/remove")
    @BussinessLog(value = "删除菜单", key = "menuId", dict = "DeleteDict")
    @ResponseBody
    public Tip remove(@RequestParam Integer menuId) {
        if (ToolUtil.isEmpty(menuId)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        //查询当前菜单下面是否有菜单
        MenuVo menuVo = menuService.selectById(menuId);

        List<MenuVo> menuVoList = menuService.selectNextLevelMenu(menuVo.getCode());
        System.out.println("删除菜单:子菜单个数" + menuVoList != null ? menuVoList.size() : 0);
        if (menuVoList != null && menuVoList.size() > 0) {
            throw new BussinessException(BizExceptionEnum.DELETE_ERROR_SUB);
        }

        //缓存菜单的名称
        //LogObjectHolder.me().set(menuService.getMenuName(menuId));


        menuService.delMenuContainSubMenus(menuId, ShiroKit.getUser().getId());
        return SUCCESS_TIP;
    }


    /**
     * 修改状态
     */
    @Permission(Const.ADMIN_NAME)
    @RequestMapping(value = "/upstatus")
    @BussinessLog(value = "修改状态", key = "menuId", dict = "DeptDict")
    @ResponseBody
    public Tip upstatus(@RequestParam Integer menuId) {
        if (ToolUtil.isEmpty(menuId)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        MenuVo menuVo = menuService.selectById(menuId);
        Integer menuRoleStatus = null;
        if (0 == menuVo.getStatus()) {
            menuVo.setStatus(1);
            menuRoleStatus = 0;
        } else {
            menuVo.setStatus(0);
            menuRoleStatus = 1;
        }
        menuService.updateById(menuVo);
        menuService.updateMenuRole(menuVo.getId(), menuRoleStatus);
        return SUCCESS_TIP;
    }

    /**
     * 查看菜单
     */
    @RequestMapping(value = "/view/{menuId}")
    @ResponseBody
    public Tip view(@PathVariable Integer menuId) {
        if (ToolUtil.isEmpty(menuId)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        menuService.selectById(menuId);
        return SUCCESS_TIP;
    }

    /**
     * 获取菜单列表(首页用)
     */
    @RequestMapping(value = "/menuTreeList")
    @ResponseBody
    public List<ZTreeNode> menuTreeList() {
        List<ZTreeNode> roleTreeList = menuService.menuTreeList(ShiroKit.getUser().getType());
        return roleTreeList;
    }

    /**
     * 获取菜单列表(选择父级菜单用)
     */
    @RequestMapping(value = "/selectMenuTreeList")
    @ResponseBody
    public List<ZTreeNode> selectMenuTreeList() {
        List<ZTreeNode> roleTreeList = menuService.menuTreeList(ShiroKit.getUser().getType());
        roleTreeList.add(ZTreeNode.createParent());
        return roleTreeList;
    }


    /**
     * 获取角色列表
     */
    @RequestMapping(value = "/menuTreeListByRoleId/{roleId}")
    @ResponseBody
    public List<ZTreeNode> menuTreeListByRoleId(@PathVariable Integer roleId) {
        List<Integer> menuIds = this.menuService.getMenuIdsByRoleId(roleId, ShiroKit.getUser().getType());
        if (ToolUtil.isEmpty(menuIds)) {
            List<ZTreeNode> roleTreeList = this.menuService.menuTreeList(ShiroKit.getUser().getType());
            return roleTreeList;
        } else {
            List<ZTreeNode> roleTreeListByUserId = this.menuService.menuTreeListByMenuIds(menuIds, ShiroKit.getUser().getType());
            return roleTreeListByUserId;
        }
    }

    /**
     * 根据请求的父级菜单编号设置pcode和层级
     */
    private void menuSetPcode(@Valid MenuVo menu) {
        String pCode = "0";
        try {
            if (ToolUtil.isEmpty(menu.getPcode()) || pCode.equals(menu.getPcode())) {
                menu.setPcode("0");
                menu.setLevels(1);
            } else {
                int code = Integer.parseInt(menu.getPcode());
                MenuVo pMenu = menuService.selectById(code);
                Integer pLevels = pMenu.getLevels();
                menu.setPcode(pMenu.getCode());
                if (menu.getCode().equals(menu.getPcode())) {
                    return;
                }
                menu.setLevels(pLevels + 1);
            }
        } catch (Exception e) {
            //e.printStackTrace();
            menu.setPcode("0");
            menu.setLevels(1);
        }
    }

}
