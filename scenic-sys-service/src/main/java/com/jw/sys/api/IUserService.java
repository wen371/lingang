package com.jw.sys.api;


import com.jw.sys.bean.vo.DataScope;
import com.jw.sys.bean.vo.UserVo;

import java.util.List;

public interface IUserService {
    UserVo selectById(Integer id);

    List<UserVo> selectUsers(DataScope dataScope, String name, String beginTime, String endTime, Integer scenicId, Integer type, Integer num, Integer pageSize);

    UserVo getByAccount(String account);

    UserVo getByAccountAndType(String account, Integer type);

    Integer insert(UserVo user);

    Integer insertUser(UserVo user);

    void update(UserVo user);

    void setStatus(Integer userId, Integer code, Integer mender);

    void updatePass(UserVo user);

    void deleteById(Integer userId, Integer mender);

    List<Integer> selectRoleIdsByUserId(Integer userId);

    void setRoles(Integer userId, String roleIds, Integer mender);

    int countUsers(DataScope dataScope, String name, String beginTime, String endTime, Integer scenicId, Integer type);

    Integer getCountByAccount(String account);

    List<UserVo> selectSubWPUsers(String agentCode, List<String> strs, String name, String beginTime, String endTime, Integer type, Integer num, Integer pageSize);

    int countSubWPUsers(String agentCode, List<String> strs, String name, String beginTime, String endTime, Integer type);

    List<UserVo> selectSubByInnerCode(String name, String beginTime, String endTime, String innerCode, Integer num, Integer pageSize, Integer type, Integer scenicId);

    int countSubByInnerCode(String name, String beginTime, String endTime, String innerCode, Integer type, Integer scenicId);

    UserVo getUserByInnerCode(String agentCode);

    UserVo getByAccountAndId(String account, Integer id);

    UserVo getAgentUser(String agentCode);

    void upUsrIsDeleteByAgenCode(String agentCode);

    int getUserByIdNoCount(String idNo, Integer type);

    Integer insertWorkPoint(UserVo user);

    List<UserVo> getUserByIdNo(String idNo, Integer type);

    List<UserVo> selectByPhone(String phone, Integer type);

    /**
     *无条件查找sysUser里所有数据
     * @return
     */
    List<UserVo> findAllUsersList();
}
