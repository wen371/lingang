package com.jw.sys.bean.vo;


import lombok.Data;

import java.util.Date;

@Data
public class ScenicSpotVo{

    private Integer id;

    private String name;

    private String phone;

    private String city;

    private String area;

    private String synopsis;

    private String address;

    private String ticketingAddress;

    private String rank;

    private String openHours;

    private String ticketingHours;

    private String theme;

    private String reservationNotes;

    private String details;

    private String trafficInformation;

    private String code;

    private String headImg;

    private String fileName;

    private String fileSize;

    private String fileUrl;

    private Date createTime;

    private Date modifyTime;

    private String creator;

    private String mender;

    private String isDelete;

}