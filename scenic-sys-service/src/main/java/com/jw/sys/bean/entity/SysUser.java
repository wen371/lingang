package com.jw.sys.bean.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * date : 2017-12-29 09:41:25
 * author : mapper generator
 * description : 管理员表
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class SysUser extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键idid
     **/
    private Integer id;
    private String innerCode;
    /**
     * 账号
     **/
    private String account;

    /**
     * 密码
     **/
    private String password;

    /**
     * md5密码盐
     **/
    private String salt;
    private String historyPass;
    /**
     * 名字
     **/
    private String name;
    private String idNo;
    /**
     * 生日
     **/
    private Date birthday;

    /**
     * 性别（1：男 2：女）
     **/
    private Integer sex;

    /**
     * 电子邮件
     **/
    private String email;
    private String agentCode;
    private String deptCode;
    private String orgCode;
    private String networkCode;
    /**
     * 电话
     **/
    private String phone;

    /**
     * 部门id
     **/
    //private Integer deptId;
    private Integer scenicId;

    /**
     * 状态(1：启用  2：冻结  3：删除）
     **/
    private Integer status;

    /**
     *
     **/
    private Integer creator;

    /**
     * 创建时间
     **/
    private Date createTime;

    /**
     *
     **/
    private Integer mender;

    private Integer type;

    /**
     *
     **/
    private Date modifyTime;

    /**
     *
     **/
    private Character isDelete;

    private String errorLogin;

}