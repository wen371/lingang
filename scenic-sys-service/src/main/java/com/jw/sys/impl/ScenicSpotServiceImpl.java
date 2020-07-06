package com.jw.sys.impl;

import com.google.common.collect.Lists;
import com.jw.sys.api.IScenicSpotService;
import com.jw.sys.bean.entity.ScenicSpot;
import com.jw.sys.bean.vo.ScenicSpotVo;
import com.jw.sys.dao.ScenicSpotMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("scenicSpotService")
public class ScenicSpotServiceImpl implements IScenicSpotService {

    @Autowired
    ScenicSpotMapper scenicSpotMapper;

    @Override
    public long selectAllCount(Map<String, Object> dataMap) {
        System.out.println("start selectAllCount");
        return scenicSpotMapper.selectAllcount(dataMap);
    }

    @Override
    public List<ScenicSpotVo> selectInfoList(Map<String, Object> dataMap) {
        System.out.println("start selectInfoList");
        return getListVo(scenicSpotMapper.selectScenicSpotInfoList(dataMap));
    }


    @Override
    public List<ScenicSpotVo> selectAllList(Map<String, Object> dataMap) {
        return getListVo(scenicSpotMapper.selectScenicSpotAllList(dataMap));
    }

    @Override
    public Integer insert(ScenicSpotVo scenicSpotVo) {
        ScenicSpot scenicSpot = new ScenicSpot();
        BeanUtils.copyProperties(scenicSpotVo, scenicSpot);
        scenicSpotMapper.insertSelective(scenicSpot);
        return scenicSpot.getId();
    }

    @Override
    public ScenicSpotVo findById(Integer id) {
        ScenicSpot scenicSpot = scenicSpotMapper.findById(id);
        if(scenicSpot==null){
            return new ScenicSpotVo();
        }
        ScenicSpotVo scenicSpotVo = new ScenicSpotVo();
        BeanUtils.copyProperties(scenicSpot, scenicSpotVo);
        return scenicSpotVo;
    }

    @Override
    public ScenicSpotVo selectById(Integer id) {
        ScenicSpot scenicSpot = scenicSpotMapper.selectByPrimaryKey(id);
        if(scenicSpot==null){
            return new ScenicSpotVo();
        }
        ScenicSpotVo scenicSpotVo = new ScenicSpotVo();
        BeanUtils.copyProperties(scenicSpot, scenicSpotVo);
        return scenicSpotVo;
    }

    @Override
    public void update(ScenicSpotVo scenicSpotVo) {
        ScenicSpot scenicSpot = new ScenicSpot();
        BeanUtils.copyProperties(scenicSpotVo, scenicSpot);
        scenicSpotMapper.updateByPrimaryKeySelective(scenicSpot);
    }

    @Override
    public List<ScenicSpotVo> findAllNameList() {
        ScenicSpotVo scenicSpotVo = new ScenicSpotVo();
        return getListVo(scenicSpotMapper.findAllNameList(scenicSpotVo));
    }

    private List<ScenicSpotVo> getListVo(List<ScenicSpot> list){
        List<ScenicSpotVo> results = Lists.newArrayList();
        for(ScenicSpot scenicSpot:list) {
            ScenicSpotVo scenicSpotVo = new ScenicSpotVo();
            BeanUtils.copyProperties(scenicSpot, scenicSpotVo);
            results.add(scenicSpotVo);
        }
        return results;
    }
}
