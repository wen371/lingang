package com.jw.sys.dao;

import com.jw.common.bean.BaseDao;
import com.jw.sys.bean.vo.DataScope;
import com.jw.sys.bean.vo.UserVo;
import com.jw.sys.bean.entity.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * date : 2017-12-29 09:41:25 author : mapper generator description :
 * SysUserDao数据库操作接口类
 **/
public interface SysUserDao extends BaseDao {

    /**
     * 根据Id查询记录
     **/
    SysUser selectById(@Param("id") Integer id);

    /**
     * 根据给定的记录查询一批记录
     **/
    List<SysUser> selectByIds(@Param("ids") List<Integer> ids);

    /**
     * 新增记录
     **/
    int insert(SysUser pojo);

    /**
     * 新增记录，只匹配有值的字段
     **/
    int insertSelective(SysUser pojo);

    /**
     * 根据Id更新记录
     **/
    int updateById(SysUser pojo);

    /**
     * 根据Id更新记录,只更新有值的字段
     **/
    int updateSelectiveById(SysUser pojo);

    /**
     * 根据Id删除记录
     **/
    /* int deleteById(@Param("id") Integer id); */

    /**
     * 根据Id删除一批记录
     **/
    int deleteByIds(@Param("ids") List<Integer> ids);

    // 以上为工具生成，公用，以下为业务逻辑，专用

    List<SysUser> selectUsers(@Param("dataScope") DataScope dataScope, @Param("name") String name,
                              @Param("beginTime") String beginTime, @Param("endTime") String endTime,
                              @Param("scenicId") Integer scenicId, @Param("type") Integer type, @Param("num") Integer num,
                              @Param("pageSize") Integer pageSize);

    void setStatusById(@Param("userId") Integer userId, @Param("status") Integer status,
                       @Param("mender") Integer mender);

    void updatePassWorderById(SysUser sysUser);

    void deleteById(@Param("userId") Integer userId, @Param("mender") Integer mender);

    SysUser getByAccount(String account);

    SysUser getByAccountAndType(@Param("account") String account, @Param("type") Integer type);

    int countUsers(@Param("dataScope") DataScope dataScope, @Param("name") String name,
                   @Param("beginTime") String beginTime, @Param("endTime") String endTime,
                   @Param("scenicId") Integer scenicId, @Param("type") Integer type);

    List<SysUser> selectSubWPUsers(Map<String, Object> map);

    int countSubWPUsers(Map<String, Object> map);

    List<SysUser> selectSubByInnerCode(@Param("name") String name, @Param("beginTime") String beginTime,
                                       @Param("endTime") String endTime, @Param("innerCode") String innerCode,
                                       @Param("num") Integer num, @Param("pageSize") Integer pageSize,
                                       @Param("type") Integer type, @Param("scenicId") Integer scenicId);

    int countSubByInnerCode(@Param("name") String name, @Param("beginTime") String beginTime,
                            @Param("endTime") String endTime, @Param("innerCode") String innerCode,
                            @Param("type") Integer type, @Param("scenicId") Integer scenicId);

    SysUser getByAccountAndId(@Param("account") String account, @Param("id") Integer id);

    SysUser getUserByInnerCode(@Param("agentCode") String agentCode);

    SysUser getAgentUser(@Param("agentCode") String agentCode);

    void upUsrIsDeleteByAgenCode(@Param("agentCode") String agentCode, @Param("isDelete") String isDelete);

    int getUserByIdNoCount(@Param("idno") String idno, @Param("type") Integer type);

    Integer getCountByAccount(@Param("account") String account);

    Integer insertWorkPoint(UserVo user);

    List<SysUser> getUserByIdNo(@Param("idNo") String idNo, @Param("type") Integer type);

    List<SysUser> selectByPhone(@Param("phone") String phone, @Param("type") Integer type);

    /**
     * 无条件查找sysUser里所有数据
     * @return
     */
    List<SysUser> findAllUsersList();
}
