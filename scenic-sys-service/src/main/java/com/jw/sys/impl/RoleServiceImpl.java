package com.jw.sys.impl;

import com.google.common.collect.Lists;
import com.jw.sys.bean.vo.RoleVo;
import com.jw.sys.bean.vo.ZTreeNode;
import com.jw.sys.dao.RelationDao;
import com.jw.sys.dao.RoleDao;
import com.jw.sys.dao.SysUserVsRoleDao;
import com.jw.sys.bean.entity.Relation;
import com.jw.sys.bean.entity.Role;
import com.jw.sys.bean.entity.SysUserVsRole;
import com.jw.sys.api.IRoleService;
import com.jw.common.support.Convert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("roleService")
public class RoleServiceImpl implements IRoleService {
    @Autowired
    private RoleDao roleDao;

    @Autowired
    private RelationDao relationDao;
    @Autowired
    SysUserVsRoleDao sysUserVsRoleDao;

    @Override
    public RoleVo selectById(Integer roleId) {
        return getVo(roleDao.selectById(roleId));
    }

    @Override
    public String getSingleRoleName(Integer pid) {
        return roleDao.getSingleRoleName(pid).getName();
    }

    @Override
    public String getSingleRoleNameById(Integer roleId) {
        try {
            return roleDao.getSingleRoleNameById(roleId).getName();
        }catch (Exception e){
            e.printStackTrace();
        }
        return  null;
    }

    @Override
    public List<RoleVo> selectRoles(String roleName,Integer type, Integer scenicId) {
        List<RoleVo> list = null;
        System.out.println("roleList" + roleName+"  "+type);
        try {
            list = getListVo(roleDao.selectRoles(roleName,type ,scenicId));
        } catch (Exception e) {
            System.out.println("selecrRoles:" + e);
        }
        return list;
    }

    @Override
    public void insert(RoleVo role) {
        roleDao.insert(getEntity(role));
    }

    @Override
    public void updateById(RoleVo role) {
        roleDao.updateById(getEntity(role));
    }

    @Override
    public boolean delRoleById(Integer roleId, Integer mender) {
        List<SysUserVsRole> sysUserVsRole = sysUserVsRoleDao.selectByRoleId(roleId);
        if (sysUserVsRole == null || sysUserVsRole.size() == 0) {
            roleDao.deleteById(roleId, mender);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<ZTreeNode> roleTreeList(Integer type) {
        return roleDao.roleTreeList(type);
    }

    @Override
    public void setAuthority(Integer roleId, String ids, Integer mender) {
        // 删除该角色所有的权限
        this.roleDao.deleteRolesById(roleId, mender);

        // 添加新的权限
        for (Integer id : Convert.toIntArray(ids)) {
            Relation relation = new Relation();
            relation.setRoleId(roleId);
            relation.setMenuId(id);
            relation.setCreator(mender);
            relation.setMender(mender);
            this.relationDao.insert(relation);
        }
    }

    @Override
    public List<ZTreeNode> roleTreeListByRoleId(List<Integer> roleids,Integer type) {
        Map<String,Object> map = new HashMap<>();
        map.put("list",roleids);
        map.put("type",type);
        return roleDao.roleTreeListByRoleId(map);
    }

    @Override
    public RoleVo selectByIdAndName(Integer roleid, String roleName, Integer type ,Integer scenicId) {
        return getVo(roleDao.selectByIdAndName(roleid,roleName,type,scenicId));
    }

    @Override
    public List<ZTreeNode> roleTreeListByUserId(Integer userId, Integer type) {
        Map<String,Object> map = new HashMap<>();
        map.put("userId",userId);
        map.put("type",type);
        return roleDao.roleTreeListByUserId(map);
    }

    @Override
    public List<ZTreeNode> roleTreeListByRoleIdAndUserId(List<Integer> roleids, Integer type, Integer userId) {
        Map<String,Object> map = new HashMap<>();
        map.put("list",roleids);
        map.put("userId",userId);
        map.put("type",type);
        return roleDao.roleTreeListByRoleIdAndUserId(map);
    }

    private List<RoleVo> getListVo(List<Role> entitys) {
        if(entitys!=null) {
            List<RoleVo> results = Lists.newArrayList();
            for (Role role : entitys) {
                RoleVo roleVo = new RoleVo();
                roleVo.setId(role.getId());
                roleVo.setName(role.getName());
                roleVo.setType(role.getType());
                roleVo.setTips(role.getTips());
                roleVo.setScenicId(role.getScenicId());
                roleVo.setCreator(role.getCreator());
                roleVo.setCreateTime(role.getCreateTime());
                roleVo.setIsDelete(role.getIsDelete());
                roleVo.setModifyTime(role.getModifyTime());
                results.add(roleVo);
            }
            return results;
        }return null;
    }

    private RoleVo getVo(Role role) {
        if(role!=null) {
            RoleVo roleVo = new RoleVo();
            roleVo.setId(role.getId());
            roleVo.setName(role.getName());
            roleVo.setType(role.getType());
            roleVo.setTips(role.getTips());
            roleVo.setScenicId(role.getScenicId());
            roleVo.setCreator(role.getCreator());
            roleVo.setCreateTime(role.getCreateTime());
            roleVo.setIsDelete(role.getIsDelete());
            roleVo.setModifyTime(role.getModifyTime());
            return roleVo;
        }
        return null;
    }

    private Role getEntity(RoleVo rolevo) {
        if(rolevo!=null) {
            Role role = new Role();
            role.setId(rolevo.getId());
            role.setName(rolevo.getName());
            role.setType(rolevo.getType());
            role.setTips(rolevo.getTips());
            role.setScenicId(rolevo.getScenicId());
            role.setCreator(rolevo.getCreator());
            role.setCreateTime(rolevo.getCreateTime());
            role.setIsDelete(rolevo.getIsDelete());
            role.setModifyTime(rolevo.getModifyTime());
            return role;
        }
        return null;
    }
}
