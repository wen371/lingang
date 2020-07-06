package com.jw.sys.api;

import com.jw.sys.bean.entity.ScenicSpot;
import com.jw.sys.bean.vo.ScenicSpotVo;

import java.util.List;
import java.util.Map;

public interface IScenicSpotService {

    long selectAllCount(Map<String, Object> dataMap);

    List<ScenicSpotVo> selectInfoList(Map<String, Object> dataMap);

    ScenicSpotVo selectById(Integer Id);

    void update(ScenicSpotVo scenicSpotVo);

    List<ScenicSpotVo> findAllNameList();

    List<ScenicSpotVo> selectAllList(Map<String, Object> dataMap);

    Integer insert(ScenicSpotVo scenicSpotVo);

    ScenicSpotVo findById(Integer id);
}
