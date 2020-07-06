package com.jw.sys.bean.vo;


import com.jw.sys.bean.entity.BaseEntity;
import lombok.Data;

import java.util.Date;

@Data
public class TFileVo extends BaseEntity {

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