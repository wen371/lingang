package com.jw.sys.dao;

import com.jw.common.bean.BaseDao;
import com.jw.sys.bean.entity.SysUserVsRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * date : 2017-12-28 17:35:26
 * author : mapper generator
 * description : SysUserVsRoleDao数据库操作接口类
 **/
public interface SysUserVsRoleDao extends BaseDao {

    /**
     * 根据Id查询记录
     **/
    SysUserVsRole selectById(Integer id);

    /**
     * 根据给定的记录查询一批记录
     **/
    List<SysUserVsRole> selectByIds(@Param("ids") List<Integer> ids);

    /**
     * 新增记录
     **/
    int insert(SysUserVsRole pojo);

    /**
     * 新增记录，只匹配有值的字段
     **/
    int insertSelective(SysUserVsRole pojo);

    /**
     * 根据Id更新记录
     **/
    int updateById(SysUserVsRole pojo);

    /**
     * 根据Id更新记录,只更新有值的字段
     **/
    int updateSelectiveById(SysUserVsRole pojo);

    /**
     * 根据Id删除记录
     **/
    int deleteById(Integer id);

    /**
     * 根据Id删除一批记录
     **/
    int deleteByIds(@Param("ids") List<Integer> ids);

    // 以上为工具生成，公用，以下为业务逻辑，专用

    /**
     * 根据userId查询记录
     **/
    List<SysUserVsRole> selectByUserId(int userId);

    int deleteByUserId(@Param("userId") Integer userId, @Param("mender") Integer mender);

    List<SysUserVsRole> selectByRoleId(Integer roleId);
}