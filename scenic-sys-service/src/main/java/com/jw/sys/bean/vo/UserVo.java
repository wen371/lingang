package com.jw.sys.bean.vo;

import lombok.Data;

import java.util.Date;

@Data
public class UserVo {

    private Integer id;
    private String innerCode;
    private String statusName;
    private String deptName;
    private String deptCode;
    private String roleName;
    private String sexName;
    private Integer creator;
    private Integer mender;
    private String account;
    private String password;
    private String salt;
    private String historyPass;
    private String name;
    private String birthday;
    private Integer sex;
    private String email;
    private String phone;
    //private Integer deptId;
    private Integer scenicId;
    private String scenicName;
    private Integer status;
    private Date createTime;
    private Integer type;
    private String idNo;
    private String agentCode;
    private String agentName;
    private String orgCode;
    private String orgName;
    private String networkCode;
    private String networkName;
    private Character isDelete;
    private Date modifyTime;
    private String errorLogin;
}
