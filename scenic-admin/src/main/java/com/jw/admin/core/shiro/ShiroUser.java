package com.jw.admin.core.shiro;

import java.io.Serializable;
import java.util.List;

/**
 * 自定义Authentication对象，使得Subject除了携带用户的登录名外还可以携带更多信息
 *
 * @author fengshuonan
 * @date 2016年12月5日 上午10:26:43
 */
public class ShiroUser implements Serializable {

    private static final long serialVersionUID = 3537545454455L;

    public Integer id;          // 主键ID
    public String account;      // 账号
    public String name;         // 姓名
    //public Integer deptId;      // 部门id
    public Integer scenicId;      // 部门id
    public List<Integer> roleList; // 角色集
    public String deptName;        // 部门名称
    public List<String> roleNames; // 角色名称集
    public List<String> tips;       // 角色别名集
    public Integer type;           //判断天安还是企业

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getScenicId() {
        return scenicId;
    }

    public void setScenicId(Integer scenicId) {
        this.scenicId = scenicId;
    }

    public List<Integer> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Integer> roleList) {
        this.roleList = roleList;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public List<String> getRoleNames() {
        return roleNames;
    }

    public void setRoleNames(List<String> roleNames) {
        this.roleNames = roleNames;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public List<String> getTips() {
        return tips;
    }

    public void setTips(List<String> tips) {
        this.tips = tips;
    }

    @Override
    public String toString() {
        return "ShiroUser{" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", name='" + name + '\'' +
                ", scenicId=" + scenicId +
                ", roleList=" + roleList +
                ", deptName='" + deptName + '\'' +
                ", roleNames=" + roleNames +
                ", tips=" + tips +
                ", type=" + type +
                '}';
    }
}
