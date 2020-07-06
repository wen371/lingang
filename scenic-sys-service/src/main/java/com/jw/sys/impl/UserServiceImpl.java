package com.jw.sys.impl;

import com.google.common.collect.Lists;
import com.jw.sys.bean.vo.DataScope;
import com.jw.sys.bean.vo.UserVo;
import com.jw.sys.dao.RoleDao;
import com.jw.sys.dao.SysUserDao;
import com.jw.sys.dao.SysUserVsRoleDao;
import com.jw.sys.bean.entity.SysUser;
import com.jw.sys.bean.entity.SysUserVsRole;
import com.jw.sys.api.IUserService;
import com.jw.common.constant.state.ManagerStatus;
import com.jw.common.constant.state.SexName;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Service("userService")
public class UserServiceImpl implements IUserService {

    @Autowired
    private SysUserDao sysUserDao;

    @Autowired
    private SysUserVsRoleDao sysUserVsRoleDao;

    @Autowired
    private RoleDao roleDao;



    @Override
    public UserVo selectById(Integer id) {
        return getVo(sysUserDao.selectById(id));
    }


    @Override
    public List<UserVo> selectUsers(DataScope dataScope, String name, String beginTime, String endTime, Integer scenicId,Integer type,Integer num,Integer pageSize) {
        return getListVo(sysUserDao.selectUsers(dataScope, name, beginTime, endTime, scenicId,type,num,pageSize));
    }


    @Override
    public List<UserVo> selectSubWPUsers(String agentCode,List<String> strs, String name, String beginTime, String endTime, Integer type, Integer num, Integer pageSize) {
        Map<String,Object> map = new HashMap<>();
        map.put("list",strs);
        map.put("agentCode",agentCode);
        map.put("name",name);
        map.put("beginTime",beginTime);
        map.put("endTime",endTime);
        map.put("type",type);
        map.put("num",num);
        map.put("pageSize",pageSize);
        return getListVo(sysUserDao.selectSubWPUsers(map));
    }
    @Override
    public int countSubWPUsers(String agentCode,List<String> strs, String name, String beginTime, String endTime, Integer type) {
        Map<String,Object> map = new HashMap<>();
        map.put("list",strs);
        map.put("agentCode",agentCode);
        map.put("name",name);
        map.put("beginTime",beginTime);
        map.put("endTime",endTime);
        map.put("type",type);
        return sysUserDao.countSubWPUsers(map);
    }

    @Override
    public List<UserVo> selectSubByInnerCode(String name, String beginTime, String endTime, String innerCode, Integer num, Integer pageSize, Integer type ,Integer scenicId) {
        return getListVo(sysUserDao.selectSubByInnerCode(name,beginTime,endTime,innerCode,num,pageSize,type,scenicId));
    }

    @Override
    public int countSubByInnerCode(String name, String beginTime, String endTime, String innerCode, Integer type, Integer scenicId) {
        return sysUserDao.countSubByInnerCode(name,beginTime,endTime,innerCode,type,scenicId);
    }
    @Override
    public UserVo getByAccountAndId(String account, Integer id) {
        return getVo(sysUserDao.getByAccountAndId(account,id));
    }

    @Override
    public UserVo getByAccount(String account) {
        return getVo(sysUserDao.getByAccount(account));
    }

    @Override
    public UserVo getByAccountAndType(String account, Integer type) {
        return getVo(sysUserDao.getByAccountAndType(account,type));
    }

    @Override
    public Integer insert(UserVo user) {
        SysUser sysUser = getEntity(user);
        sysUser.setIsDelete('0');
        sysUserDao.insert(sysUser);
        return sysUser.getId();
    }

    @Override
    public Integer insertUser(UserVo user) {
        SysUser sysUser = getEntity(user);
        sysUserDao.insert(sysUser);
        return sysUser.getId();
    }

    @Override
    public void update(UserVo user) {
        sysUserDao.updateSelectiveById(getEntity(user));
    }

    @Override
    public void setStatus(Integer userId, Integer status, Integer mender) {
        sysUserDao.setStatusById(userId, status, mender);
    }

    @Override
    public void updatePass(UserVo user) {
        sysUserDao.updatePassWorderById(getEntity(user));
    }

    @Override
    public void deleteById(Integer userId, Integer mender) {
        sysUserDao.deleteById(userId, mender);
    }


    @Override
    public List<Integer> selectRoleIdsByUserId(Integer userId) {
        List<Integer> roleIdList = new ArrayList<>();
        try {
            List<SysUserVsRole> sysUserVsRoleList = sysUserVsRoleDao.selectByUserId(userId);
            if (sysUserVsRoleList != null && sysUserVsRoleList.size() > 0) {
                for (SysUserVsRole sysUserVsRole : sysUserVsRoleList) {
                    roleIdList.add(sysUserVsRole.getSysRoleId());
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return roleIdList;
    }


    @Override
    public void setRoles(Integer userId, String roleIds, Integer mender) {
        // 删除旧的角色
        sysUserVsRoleDao.deleteByUserId(userId, mender);
        // 增加新的角色
        String[] roleIdArray = roleIds.split(",");
        for (String roleId : roleIdArray) {
            SysUserVsRole sysUserVsRole = new SysUserVsRole();
            sysUserVsRole.setSysUserId(userId);
            sysUserVsRole.setSysRoleId(Integer.parseInt(roleId));
            sysUserVsRole.setCreator(mender.toString());
            sysUserVsRole.setMender(mender.toString());
            sysUserVsRole.setIsDelete('0');
            sysUserVsRoleDao.insertSelective(sysUserVsRole);
        }
    }

    @Override
    public int countUsers(DataScope dataScope, String name, String beginTime, String endTime, Integer scenicId, Integer type) {
        return sysUserDao.countUsers(dataScope, name, beginTime, endTime, scenicId,type);
    }

    @Override
    public Integer getCountByAccount(String account) {
        return sysUserDao.getCountByAccount(account);
    }

    @Override
    public UserVo getUserByInnerCode(String agentCode){
        SysUser SysUser= null;
        try {
            SysUser = sysUserDao.getUserByInnerCode(agentCode);
        } catch (Exception e) {
            log.error("通过中介code查询 用户信息失败!", e);
        }
        return getVo(SysUser);
    }

    public UserVo getAgentUser(String agentCode) {
        SysUser SysUser = null;
        try {
            SysUser = sysUserDao.getAgentUser(agentCode);
        } catch (Exception e) {
            log.error("通过中介code查询 用户信息失败!", e);
        }
        return getVo(SysUser);
    }


    private UserVo getVo(SysUser sysUser) {
        if (sysUser != null) {
            UserVo userVo = new UserVo();
            userVo.setAccount(sysUser.getAccount());
            userVo.setMender(sysUser.getMender());
            userVo.setCreator(sysUser.getCreator());
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
            if(null != sysUser.getBirthday()){
                userVo.setBirthday(sdf.format(sysUser.getBirthday()));
            }
            userVo.setCreateTime(sysUser.getCreateTime());
            userVo.setScenicId(sysUser.getScenicId());
            userVo.setEmail(sysUser.getEmail());
            userVo.setId(sysUser.getId());
            userVo.setPassword(sysUser.getPassword());
            userVo.setName(sysUser.getName());
            userVo.setPhone(sysUser.getPhone());
            userVo.setSalt(sysUser.getSalt());
            userVo.setSex(sysUser.getSex());
            userVo.setStatus(sysUser.getStatus());
            userVo.setType(sysUser.getType());
            userVo.setStatusName(ManagerStatus.valueOf(sysUser.getStatus()));
            userVo.setAgentCode(sysUser.getAgentCode());
            userVo.setOrgCode(sysUser.getOrgCode());
            userVo.setIdNo(sysUser.getIdNo());
            userVo.setNetworkCode(sysUser.getNetworkCode());
            userVo.setHistoryPass(sysUser.getHistoryPass());
            userVo.setDeptCode(sysUser.getDeptCode());
            userVo.setInnerCode(sysUser.getInnerCode());
            userVo.setErrorLogin(sysUser.getErrorLogin());
            userVo.setModifyTime(sysUser.getModifyTime());
            return userVo;
        }
        return null;
    }

    private SysUser getEntity(UserVo userVo) {
        SysUser sysUser = new SysUser();
        if (userVo != null) {
            if(userVo.getAccount()!=null) {
                sysUser.setAccount(userVo.getAccount().trim());
            }
            sysUser.setCreator(userVo.getCreator());
            sysUser.setMender(userVo.getMender());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                sysUser.setBirthday((userVo.getBirthday() != null && userVo.getBirthday() != "") ? sdf.parse(userVo.getBirthday()) : null);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            sysUser.setCreateTime(userVo.getCreateTime());
            sysUser.setScenicId(userVo.getScenicId());
            sysUser.setEmail(userVo.getEmail());
            sysUser.setId(userVo.getId());
            sysUser.setPassword(userVo.getPassword());
            sysUser.setName(userVo.getName());
            sysUser.setPhone(userVo.getPhone());
            sysUser.setSalt(userVo.getSalt());
            sysUser.setSex(userVo.getSex());
            sysUser.setStatus(userVo.getStatus());
            sysUser.setType(userVo.getType());
            sysUser.setAgentCode(userVo.getAgentCode());
            sysUser.setOrgCode(userVo.getOrgCode());
            sysUser.setIdNo(userVo.getIdNo());
            sysUser.setNetworkCode(userVo.getNetworkCode());
            sysUser.setHistoryPass(userVo.getHistoryPass());
            sysUser.setDeptCode(userVo.getDeptCode());
            sysUser.setInnerCode(userVo.getInnerCode());
            sysUser.setIsDelete(userVo.getIsDelete());
            sysUser.setErrorLogin(userVo.getErrorLogin());
            sysUser.setModifyTime(userVo.getModifyTime());
        }
        return sysUser;
    }

    private List<UserVo> getListVo(List<SysUser> users) {
        List<UserVo> results = Lists.newArrayList();
        for (SysUser sysUser : users) {
            UserVo userVo = new UserVo();
            userVo.setCreator(sysUser.getCreator());
            userVo.setMender(sysUser.getMender());
            userVo.setAccount(sysUser.getAccount());
            SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
            userVo.setBirthday(sysUser.getBirthday() != null ? sdf.format(sysUser.getBirthday()) : "");
            userVo.setCreateTime(sysUser.getCreateTime());
            userVo.setScenicId(sysUser.getScenicId());
            userVo.setEmail(sysUser.getEmail());
            userVo.setId(sysUser.getId());
            userVo.setPassword(sysUser.getPassword());
            userVo.setName(sysUser.getName());
            userVo.setPhone(sysUser.getPhone());
            userVo.setSalt(sysUser.getSalt());
            userVo.setSex(sysUser.getSex());
            userVo.setStatus(sysUser.getStatus());
            userVo.setStatusName(ManagerStatus.valueOf(sysUser.getStatus()));
            userVo.setSexName(SexName.valueOf(sysUser.getSex()));
            userVo.setType(sysUser.getType());
            userVo.setAgentCode(sysUser.getAgentCode());
            userVo.setOrgCode(sysUser.getOrgCode());
            userVo.setIdNo(sysUser.getIdNo());
            userVo.setHistoryPass(sysUser.getHistoryPass());
            userVo.setNetworkCode(sysUser.getNetworkCode());
            userVo.setDeptCode(sysUser.getDeptCode());
            userVo.setInnerCode(sysUser.getInnerCode());
            userVo.setErrorLogin(sysUser.getErrorLogin());
            userVo.setModifyTime(sysUser.getModifyTime());
            results.add(userVo);
        }
        return results;
    }

    @Override
    public void upUsrIsDeleteByAgenCode(String agentCode) {
        sysUserDao.upUsrIsDeleteByAgenCode(agentCode,"1");
    }


    @Override
    public int getUserByIdNoCount(String idno,Integer type) {
        return   sysUserDao.getUserByIdNoCount(idno,type);
    }

    @Override
    public Integer insertWorkPoint(UserVo user) {
        SysUser sysUser = getEntity(user);
        sysUser.setIsDelete('0');
        sysUserDao.insert(sysUser);
        return sysUser.getId();
    }

    @Override
    public List<UserVo> getUserByIdNo(String idNo,Integer type) {
        List<SysUser> sysUserList = sysUserDao.getUserByIdNo(idNo,type);
        if(null == sysUserList || sysUserList.isEmpty()){
            return null;
        }
        List<UserVo> userVoList = getListVo(sysUserList);
        return userVoList;
    }

    @Override
    public List<UserVo> selectByPhone(String phone, Integer type) {
        List<SysUser> sysUserList = sysUserDao.selectByPhone(phone,type);
        if(null == sysUserList || sysUserList.isEmpty()){
            return null;
        }
        List<UserVo> userVoList = getListVo(sysUserList);
        return userVoList;
    }

    /**
     *
     * @return
     */
    @Override
    public List<UserVo> findAllUsersList() {
        log.info("IUserService.findAllUsersList");
        List<SysUser> sysUserList = null;
        try {
            sysUserList = sysUserDao.findAllUsersList();
        } catch (Exception e) {
            log.info("IUserService.findAllUsersList.sysUserList报错了");
        }
        if(null == sysUserList || sysUserList.isEmpty()){
            return null;
        }
        List<UserVo> userVoList = null;
        try {
            userVoList = getListVo(sysUserList);
        } catch (Exception e) {
            log.info("IUserService.findAllUsersList.userVoList报错了");
        }
        return userVoList;
    }
}
