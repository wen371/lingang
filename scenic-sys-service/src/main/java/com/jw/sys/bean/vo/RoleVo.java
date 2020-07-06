package com.jw.sys.bean.vo;

import lombok.Data;

import java.util.Date;

@Data
public class RoleVo {

    private Integer id;
    private  Integer creator;
    private Integer mender;

    private Integer type;
    private Integer scenicId;
    private String scenicName;
    private String name;
    private String tips;
    private Date modifyTime;
    private Date createTime;
    private  Integer isDelete;

}
