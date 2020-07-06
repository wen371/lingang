package com.jw.sys.api;

import com.jw.sys.bean.vo.RoleVo;
import com.jw.sys.bean.vo.ZTreeNode;

import java.util.List;


public interface IRoleService {
    public RoleVo selectById(Integer roleId);

    String getSingleRoleName(Integer pid);


    String getSingleRoleNameById(Integer roleId);

    List<RoleVo> selectRoles(String roleName, Integer type ,Integer scenicId);

    void insert(RoleVo role);

    void updateById(RoleVo role);

    boolean delRoleById(Integer roleId, Integer mender);

    List<ZTreeNode> roleTreeList(Integer type);

    void setAuthority(Integer roleId, String ids, Integer mender);

    List<ZTreeNode> roleTreeListByRoleId(List<Integer> roleids, Integer type);

    RoleVo selectByIdAndName(Integer roleid, String roleName, Integer type,Integer scenicId);

    List<ZTreeNode> roleTreeListByUserId(Integer userId, Integer type);

    List<ZTreeNode> roleTreeListByRoleIdAndUserId(List<Integer> roleids, Integer type, Integer userId);
}
