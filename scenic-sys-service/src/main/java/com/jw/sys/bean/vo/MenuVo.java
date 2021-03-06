package com.jw.sys.bean.vo;

import java.io.Serializable;

public class MenuVo implements Serializable{
    private static final long serialVersionUID = 3170197715771029624L;
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column menu.id
     *
     * @mbggenerated
     */
    private Integer id;
    private  Integer creator;
    private Integer mender;
    private Integer type;


    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column menu.code
     *
     * @mbggenerated
     *
     *
     *
     */
    private String code;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column menu.pcode
     *
     * @mbggenerated
     */
    private String pcode;



    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column menu.name
     *
     * @mbggenerated
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column menu.icon
     *
     * @mbggenerated
     */
    private String icon;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column menu.url
     *
     * @mbggenerated
     */
    private String url;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column menu.num
     *
     * @mbggenerated
     */
    private Integer num;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column menu.levels
     *
     * @mbggenerated
     */
    private Integer levels;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column menu.ismenu
     *
     * @mbggenerated
     */
    private Integer ismenu;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column menu.tips
     *
     * @mbggenerated
     */
    private String tips;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column menu.status
     *
     * @mbggenerated
     */
    private Integer status;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column menu.isopen
     *
     * @mbggenerated
     */
    private Integer isopen;
    private String isMenuName;
    private String statusName;



    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column menu.id
     *
     * @return the value of menu.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column menu.id
     *
     * @param id the value for menu.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column menu.code
     *
     * @return the value of menu.code
     *
     * @mbggenerated
     */
    public String getCode() {
        return code;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column menu.code
     *
     * @param code the value for menu.code
     *
     * @mbggenerated
     */
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column menu.pcode
     *
     * @return the value of menu.pcode
     *
     * @mbggenerated
     */
    public String getPcode() {
        return pcode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column menu.pcode
     *
     * @param pcode the value for menu.pcode
     *
     * @mbggenerated
     */
    public void setPcode(String pcode) {
        this.pcode = pcode == null ? null : pcode.trim();
    }


    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column menu.name
     *
     * @return the value of menu.name
     *
     * @mbggenerated
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column menu.name
     *
     * @param name the value for menu.name
     *
     * @mbggenerated
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column menu.icon
     *
     * @return the value of menu.icon
     *
     * @mbggenerated
     */
    public String getIcon() {
        return icon;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column menu.icon
     *
     * @param icon the value for menu.icon
     *
     * @mbggenerated
     */
    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column menu.url
     *
     * @return the value of menu.url
     *
     * @mbggenerated
     */
    public String getUrl() {
        return url;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column menu.url
     *
     * @param url the value for menu.url
     *
     * @mbggenerated
     */
    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column menu.num
     *
     * @return the value of menu.num
     *
     * @mbggenerated
     */
    public Integer getNum() {
        return num;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column menu.num
     *
     * @param num the value for menu.num
     *
     * @mbggenerated
     */
    public void setNum(Integer num) {
        this.num = num;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column menu.levels
     *
     * @return the value of menu.levels
     *
     * @mbggenerated
     */
    public Integer getLevels() {
        return levels;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column menu.levels
     *
     * @param levels the value for menu.levels
     *
     * @mbggenerated
     */
    public void setLevels(Integer levels) {
        this.levels = levels;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column menu.ismenu
     *
     * @return the value of menu.ismenu
     *
     * @mbggenerated
     */
    public Integer getIsmenu() {
        return ismenu;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column menu.ismenu
     *
     * @param ismenu the value for menu.ismenu
     *
     * @mbggenerated
     */
    public void setIsmenu(Integer ismenu) {
        this.ismenu = ismenu;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column menu.tips
     *
     * @return the value of menu.tips
     *
     * @mbggenerated
     */
    public String getTips() {
        return tips;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column menu.tips
     *
     * @param tips the value for menu.tips
     *
     * @mbggenerated
     */
    public void setTips(String tips) {
        this.tips = tips == null ? null : tips.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column menu.status
     *
     * @return the value of menu.status
     *
     * @mbggenerated
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column menu.status
     *
     * @param status the value for menu.status
     *
     * @mbggenerated
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column menu.isopen
     *
     * @return the value of menu.isopen
     *
     * @mbggenerated
     */
    public Integer getIsopen() {
        return isopen;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column menu.isopen
     *
     * @param isopen the value for menu.isopen
     *
     * @mbggenerated
     */
    public void setIsopen(Integer isopen) {
        this.isopen = isopen;
    }


    public String getIsMenuName() {
        return isMenuName;
    }

    public void setIsMenuName(String isMenuName) {
        this.isMenuName = isMenuName;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public Integer getMender() {
        return mender;
    }

    public void setMender(Integer mender) {
        this.mender = mender;
    }

    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "MenuVo{" +
                "id=" + id +
                ", creator=" + creator +
                ", mender=" + mender +
                ", type=" + type +
                ", code='" + code + '\'' +
                ", pcode='" + pcode + '\'' +
                ", name='" + name + '\'' +
                ", icon='" + icon + '\'' +
                ", url='" + url + '\'' +
                ", num=" + num +
                ", levels=" + levels +
                ", ismenu=" + ismenu +
                ", tips='" + tips + '\'' +
                ", status=" + status +
                ", isopen=" + isopen +
                ", isMenuName='" + isMenuName + '\'' +
                ", statusName='" + statusName + '\'' +
                '}';
    }
}
