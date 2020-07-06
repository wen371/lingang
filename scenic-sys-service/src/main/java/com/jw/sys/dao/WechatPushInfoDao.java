package com.jw.sys.dao;
import com.jw.common.bean.BaseDao;
import com.jw.sys.bean.entity.WechatPushInfo;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

/**
 * 
 * date : 2019-10-30 18:35:59
 * author : mapper generator
 * description : WechatPushInfoDao数据库操作接口类
 * 
 **/
public interface WechatPushInfoDao extends BaseDao {

	/**
	 * 根据Id查询记录
	 **/
	WechatPushInfo selectById(@Param("id") Integer id);

	/**
	 * 根据给定的记录查询一批记录
	 **/
	List<WechatPushInfo> selectByIds(@Param("ids") List<Integer> ids);

	/**
	 * 新增记录
	 **/
	int insert(@Param("pojo") WechatPushInfo pojo);

	/**
	 * 新增记录，只匹配有值的字段
	 **/
	int insertSelective(@Param("pojo") WechatPushInfo pojo);

	/**
	 * 根据Id更新记录
	 **/
	int updateById(@Param("pojo") WechatPushInfo pojo);

	/**
	 * 根据Id更新记录,只更新有值的字段
	 **/
	int updateSelectiveById(@Param("pojo") WechatPushInfo pojo);

	/**
	 * 根据Id删除记录
	 **/
	int deleteById(@Param("id") Integer id);

	/**
	 * 根据Id删除一批记录
	 **/
	int deleteByIds(@Param("ids") List<Integer> ids);

	long selectAllcount(Map<String, Object> dataMap);

	List<WechatPushInfo> selectInfoList(Map<String, Object> dataMap);

	List<WechatPushInfo> selectInfo(WechatPushInfo wechatPushInfo);
}