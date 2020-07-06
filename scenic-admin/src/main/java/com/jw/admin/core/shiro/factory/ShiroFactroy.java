package com.jw.admin.core.shiro.factory;

import com.jw.sys.bean.vo.RoleVo;
import com.jw.sys.bean.vo.UserVo;
import com.jw.sys.api.IMenuService;
import com.jw.sys.api.IRoleService;
import com.jw.sys.api.IUserService;
import com.jw.admin.core.shiro.ShiroUser;
import com.jw.common.support.SpringContextHolder;
import com.jw.common.support.ToolUtil;
import org.apache.shiro.authc.CredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@DependsOn("springContextHolder")
@Transactional(readOnly = true)
public class ShiroFactroy implements IShiro {

    @Autowired
    private IUserService userService;
    @Autowired
    private IMenuService menuService;
    @Autowired
    private IRoleService roleService;

    public static IShiro me() {
        return SpringContextHolder.getBean(IShiro.class);
    }

    @Override
    public UserVo user(String account, String type) {
        UserVo user = userService.getByAccountAndType(account,Integer.parseInt(type));
        // 账号不存在
        if (null == user) {
            throw new CredentialsException();
        }
        // 账号被冻结
        /*if (user.getStatus() != ManagerStatus.OK.getCode()&&user.getStatus() != ManagerStatus.INIT.getCode()) {
            throw new LockedAccountException();
        }*/
        return user;
    }

    @Override
    public ShiroUser shiroUser(UserVo user) {
        ShiroUser shiroUser = new ShiroUser();
        shiroUser.setId(user.getId());            // 账号id
        shiroUser.setAccount(user.getAccount());// 账号
        shiroUser.setScenicId(user.getScenicId());    // 部门id
        shiroUser.setDeptName("");// 部门名称
        shiroUser.setName(user.getName());        // 用户名称
        shiroUser.setType(0);
        List<Integer> roleIdList = userService.selectRoleIdsByUserId(user.getId());
        //Integer[] roleArray = Convert.toIntArray(user.getRoleid());// 角色集合
        List<Integer> roleList = new ArrayList<Integer>();
        List<String> roleNameList = new ArrayList<String>();
        List<String> tips = new ArrayList<String>();
        for (Integer roleId : roleIdList) {
            RoleVo roleVo = roleService.selectById(roleId);
            roleList.add(roleId);
            roleNameList.add(roleVo.getName());
            tips.add(roleVo.getTips());
        }
        shiroUser.setRoleList(roleList);
        shiroUser.setRoleNames(roleNameList);
        shiroUser.setTips(tips);
        return shiroUser;
    }

    @Override
    public List<String> findPermissionsByRoleId(Integer roleId) {
        List<String> resUrls = menuService.getResUrlsByRoleId(roleId);
        return resUrls;
    }

    @Override
    public String findRoleNameByRoleId(Integer roleId) {
        if (0 == roleId) {
            return "--";
        }
        RoleVo roleObj = roleService.selectById(roleId);
        if (ToolUtil.isNotEmpty(roleObj) && ToolUtil.isNotEmpty(roleObj.getName())) {
            return roleObj.getName();
        }
        return "";
    }

    @Override
    public SimpleAuthenticationInfo info(ShiroUser shiroUser, UserVo user, String realmName) {
        String credentials = user.getPassword();
        // 密码加盐处理
        String source = user.getSalt();
        ByteSource credentialsSalt = new Md5Hash(source);
        return new SimpleAuthenticationInfo(shiroUser, credentials, credentialsSalt, realmName);
    }

}
