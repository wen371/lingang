package com.jw.sys.bean.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Role extends BaseEntity implements Serializable {
    private Integer id;
    private Integer type;
    private String name;
    private String tips;
    private Integer scenicId;
    private Integer creator;
    private Date createTime;
    private Integer mender;
    private Date modifyTime;
    private Integer isDelete;

}