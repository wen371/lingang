package com.jw.admin.factory;


import com.jw.common.constant.state.ManagerStatus;
import com.jw.common.constant.state.MenuStatus;
import com.jw.common.support.Convert;
import com.jw.common.support.SpringContextHolder;
import com.jw.common.support.StrKit;
import com.jw.common.support.ToolUtil;
import com.jw.sys.bean.vo.MenuVo;
import com.jw.sys.bean.vo.NoticeVo;
import com.jw.sys.bean.vo.RoleVo;
import com.jw.sys.bean.vo.UserVo;
import com.jw.sys.api.IMenuService;
import com.jw.sys.api.IRoleService;
import com.jw.sys.api.IUserService;
import com.jw.sys.api.NoticeService;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

/**
 * 常量的生产工厂
 *
 * @author fengshuonan
 * @date 2017年2月13日 下午10:55:21
 */
@Component
@DependsOn("springContextHolder")
public class ConstantFactory implements IConstantFactory {

    private IRoleService roleService = SpringContextHolder.getBean(IRoleService.class);
    private IUserService userService = SpringContextHolder.getBean(IUserService.class);
    private IMenuService menuService = SpringContextHolder.getBean(IMenuService.class);
    private NoticeService noticeService = SpringContextHolder.getBean(NoticeService.class);

    public static IConstantFactory me() {
        return SpringContextHolder.getBean("constantFactory");
    }

    /**
     * 根据用户id获取用户名称
     *
     * @author stylefeng
     * @Date 2017/5/9 23:41
     */
    @Override
    public String getUserNameById(Integer userId) {
        UserVo user = userService.selectById(userId);
        if (user != null) {
            return user.getName();
        } else {
            return "--";
        }
    }

    /**
     * 根据用户id获取用户账号
     *
     * @author stylefeng
     * @date 2017年5月16日21:55:371
     */
    @Override
    public String getUserAccountById(Integer userId) {
        UserVo user = userService.selectById(userId);
        if (user != null) {
            return user.getAccount();
        } else {
            return "--";
        }
    }

    /**
     * 通过角色ids获取角色名称
     */
    @Override
    public String getRoleName(String roleIds) {
        Integer[] roles = Convert.toIntArray(roleIds);
        StringBuilder sb = new StringBuilder();
        for (int role : roles) {
            RoleVo roleObj = roleService.selectById(role);
            if (ToolUtil.isNotEmpty(roleObj) && ToolUtil.isNotEmpty(roleObj.getName())) {
                sb.append(roleObj.getName()).append(",");
            }
        }
        return StrKit.removeSuffix(sb.toString(), ",");
    }

    /**
     * 通过角色id获取角色名称
     */
    @Override
    public String getSingleRoleName(Integer roleId) {
        if (0 == roleId) {
            return "--";
        }
        RoleVo roleObj = roleService.selectById(roleId);
        if (ToolUtil.isNotEmpty(roleObj) && ToolUtil.isNotEmpty(roleObj.getName())) {
            return roleObj.getName();
        }
        return "";
    }

    /**
     * 通过角色id获取角色英文名称
     */
    @Override
    public String getSingleRoleTip(Integer roleId) {
        if (0 == roleId) {
            return "--";
        }
        RoleVo roleObj = roleService.selectById(roleId);
        if (ToolUtil.isNotEmpty(roleObj) && ToolUtil.isNotEmpty(roleObj.getName())) {
            return roleObj.getTips();
        }
        return "";
    }



    /**
     * 获取菜单的名称们(多个)
     */
    @Override
    public String getMenuNames(String menuIds) {
        Integer[] menus = Convert.toIntArray(menuIds);
        StringBuilder sb = new StringBuilder();
        for (int menu : menus) {
            MenuVo menuObj = menuService.selectById(menu);
            if (ToolUtil.isNotEmpty(menuObj) && ToolUtil.isNotEmpty(menuObj.getName())) {
                sb.append(menuObj.getName()).append(",");
            }
        }
        return StrKit.removeSuffix(sb.toString(), ",");
    }

    /**
     * 获取菜单名称
     */
    @Override
    public String getMenuName(Integer menuId) {
        if (ToolUtil.isEmpty(menuId)) {
            return "";
        } else {
            MenuVo menu = menuService.selectById(menuId);
            if (menu == null) {
                return "";
            } else {
                return menu.getName();
            }
        }
    }

    /**
     * 获取菜单名称通过编号
     */
    @Override
    public String getMenuNameByCode(String code) {
        if (ToolUtil.isEmpty(code)) {
            return "";
        } else {
            MenuVo menu = menuService.getMenuByCode(code);
            if (menu == null) {
                return "";
            } else {
                return menu.getName();
            }
        }
    }


    /**
     * 获取通知标题
     */
    @Override
    public String getNoticeTitle(Integer dictId) {
        if (ToolUtil.isEmpty(dictId)) {
            return "";
        } else {
            NoticeVo notice = noticeService.selectById(dictId);
            if (notice == null) {
                return "";
            } else {
                return notice.getTitle();
            }
        }
    }





    /**
     * 获取用户登录状态
     */
    @Override
    public String getStatusName(Integer status) {
        return ManagerStatus.valueOf(status);
    }

    /**
     * 获取菜单状态
     */
    @Override
    public String getMenuStatusName(Integer status) {
        return MenuStatus.valueOf(status);
    }



    /**
     * 获取被缓存的对象(用户删除业务)
     */
 /* @Override
    public String getCacheObject(String para) {
        return LogObjectHolder.me().get().toString();
    }*/






}
