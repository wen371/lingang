package com.jw.sys.dao;

import com.jw.sys.bean.entity.TFile;

import java.util.List;

public interface TFileMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(TFile record);

    int insertSelective(TFile record);

    TFile selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TFile record);

    int updateByPrimaryKey(TFile record);

    List<TFile> selectFileList(TFile file);

    int updateByPrimaryKeySelectiveByLinkid(TFile file);

    List<TFile> selectFileTicketProductList(TFile file);
    int updateByPrimaryKeySelectiveByTicketProduct(TFile file);

    List<TFile> selectFileAppList(TFile record);
}