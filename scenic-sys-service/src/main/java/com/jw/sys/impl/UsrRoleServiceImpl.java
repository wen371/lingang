package com.jw.sys.impl;

import com.jw.sys.bean.vo.SysUserVsRoleVo;
import com.jw.sys.dao.SysUserVsRoleDao;
import com.jw.sys.bean.entity.SysUserVsRole;
import com.jw.sys.api.IUsrRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service("usrRoleService")
@Slf4j
public class UsrRoleServiceImpl  implements IUsrRoleService {

    @Autowired
    private SysUserVsRoleDao sysUserVsRoleDao;

    @Override
    public void insert(Integer usrId, Integer roleId) {
        SysUserVsRole role = new SysUserVsRole();
        role.setSysUserId(usrId);
        role.setSysRoleId(roleId);
        role.setCreateTime(new Date());
        sysUserVsRoleDao.insertSelective(role);
    }

    @Override
    public List<SysUserVsRoleVo> selectByUserId(Integer id) {
        return getListVo(sysUserVsRoleDao.selectByUserId(id));
    }

    private List<SysUserVsRoleVo> getListVo(List<SysUserVsRole> sysUserVsRoles) {
        List<SysUserVsRoleVo> userVsRoleVos = new ArrayList<>();
        if(sysUserVsRoles!=null){
            for(SysUserVsRole sysUserVsRole:sysUserVsRoles){
                SysUserVsRoleVo userVsRoleVo = new SysUserVsRoleVo();
                BeanUtils.copyProperties(sysUserVsRole,userVsRoleVo);
                userVsRoleVos.add(userVsRoleVo);
            }
            return userVsRoleVos;
        }
        return null;
    }
}
