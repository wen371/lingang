package com.jw.sys.bean.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * date : 2017-12-28 17:35:26
 * author : mapper generator
 * description : 用户-角色关联表
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class SysUserVsRole extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

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