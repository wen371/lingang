package com.jw.sys.bean.entity;


import lombok.Data;

import java.util.Date;

@Data
public class TFile extends BaseEntity {

    private Integer id;

    private String name;

    private String extend;

    private Integer size;

    private String url;

    private Integer linktId;

    private String type;

    private Date createTime;

    private Date modifyTime;

    private String creator;

    private String mender;

    private String isDelete;

}