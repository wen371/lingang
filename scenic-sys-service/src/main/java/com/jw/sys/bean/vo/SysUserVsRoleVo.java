package com.jw.sys.bean.vo;

import lombok.Data;

import java.util.Date;

@Data
public class SysUserVsRoleVo {

    /**
     *
     **/
    private Integer id;

    /**
     * 用户id
     **/
    private Integer sysUserId;

    /**
     * 角色id
     **/
    private Integer sysRoleId;

    /**
     * 创建者
     **/
    private String creator;

    /**
     * 创建时间
     **/
    private Date createTime;

    /**
     * 修改者
     **/
    private String mender;

    /**
     * 修改时间
     **/
    private Date modifyTime;

    /**
     * 是否生效（0，生效；1，删除）
     **/
    private Character isDelete;
}
