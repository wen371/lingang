package com.jw.sys.impl;

import com.jw.common.bean.BaseResult;
import com.jw.sys.api.ITFileService;
import com.jw.sys.bean.entity.TFile;
import com.jw.sys.bean.vo.TFileVo;
import com.jw.sys.dao.TFileMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class TFileServiceImpl implements ITFileService {

    @Autowired
    TFileMapper fileMapper;

    @Override
    public List<TFileVo> fileList(TFileVo tFileVo) {
        List<TFileVo> response=new ArrayList<>();
        TFile file = new TFile();
        BeanUtils.copyProperties(tFileVo, file);
        try {
            List<TFile> list = fileMapper.selectFileList(file);
            for(TFile tFile:list) {
                TFileVo fileVo = new TFileVo();
                BeanUtils.copyProperties(tFile, fileVo);
                response.add(fileVo);
            }
        }catch (Exception e){
            log.error("file数据查询异常!", e);
        }
        return response;
    }

    @Override
    public List<TFileVo> fileTicketProductList(TFileVo tFileVo) {
        List<TFileVo> response=new ArrayList<>();
        TFile file = new TFile();
        BeanUtils.copyProperties(tFileVo, file);
        try {
            List<TFile> list = fileMapper.selectFileTicketProductList(file);
            for(TFile tFile:list) {
                TFileVo fileVo = new TFileVo();
                BeanUtils.copyProperties(tFile, fileVo);
                response.add(fileVo);
            }
        }catch (Exception e){
            log.error("file数据查询异常!", e);
        }
        return response;
    }

    @Override
    public BaseResult<Integer> insert(TFileVo tFileVo) {
        BaseResult<Integer> response = new BaseResult<>();
        TFile file = new TFile();
        BeanUtils.copyProperties(tFileVo, file);
        try {
            Integer id = fileMapper.insertSelective(file);
            response.setSuccess(id);
        } catch (Exception e) {
            response.setMessage("file插入异常!");
            log.error("file插入异常!", e);
        }

        return response;
    }

    @Override
    public BaseResult delete(TFileVo tFileVo) {
        BaseResult<Integer> response = new BaseResult<>();
        TFile file = new TFile();
        BeanUtils.copyProperties(tFileVo, file);
        try {
            file.setIsDelete("1");
            fileMapper.updateByPrimaryKeySelectiveByLinkid(file);
            response.setSuccess(null);
        } catch (Exception e) {
            response.setMessage("file插入异常!");
            log.error("file插入异常!", e);
        }

        return response;
    }

    @Override
    public BaseResult deleteTicketProduct(TFileVo tFileVo) {
        BaseResult<Integer> response = new BaseResult<>();
        TFile file = new TFile();
        BeanUtils.copyProperties(tFileVo, file);
        try {
            file.setIsDelete("1");
            fileMapper.updateByPrimaryKeySelectiveByTicketProduct(file);
            response.setSuccess(null);
        } catch (Exception e) {
            response.setMessage("file插入异常!");
            log.error("file插入异常!", e);
        }

        return response;
    }

    @Override
    public List<TFile> selectFileAppList(TFile record) {
        return fileMapper.selectFileAppList(record);
    }
}
