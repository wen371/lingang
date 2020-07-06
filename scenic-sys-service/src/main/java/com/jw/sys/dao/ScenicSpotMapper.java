package com.jw.sys.dao;

import com.jw.sys.bean.entity.ScenicSpot;
import com.jw.sys.bean.vo.ScenicSpotVo;

import java.util.List;
import java.util.Map;

public interface ScenicSpotMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(ScenicSpot record);

    int insertSelective(ScenicSpot record);

    ScenicSpot selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ScenicSpot record);

    int updateByPrimaryKey(ScenicSpot record);

    long selectAllcount(Map<String, Object> dataMap);

    List<ScenicSpot> selectScenicSpotInfoList(Map<String, Object> paramMap);

    List<ScenicSpot> selectScenicSpotAllList(Map<String, Object> paramMap);

    List<ScenicSpot>findAllNameList(ScenicSpotVo scenicSpotVo);

    ScenicSpot findById(Integer id);
}