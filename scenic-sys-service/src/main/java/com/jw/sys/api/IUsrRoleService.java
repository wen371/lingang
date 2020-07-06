package com.jw.sys.api;

import com.jw.sys.bean.vo.SysUserVsRoleVo;

import java.util.List;

public interface IUsrRoleService {
    void insert(Integer usrId, Integer roleId);

    List<SysUserVsRoleVo> selectByUserId(Integer id);
}
