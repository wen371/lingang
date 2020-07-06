package com.jw.sys.api;

import com.jw.common.bean.BaseResult;
import com.jw.sys.bean.entity.TFile;
import com.jw.sys.bean.vo.TFileVo;

import java.util.List;

public interface ITFileService {

    List<TFileVo> fileList(TFileVo tFileVo);


    BaseResult<Integer> insert(TFileVo tFileVo);

    BaseResult delete(TFileVo tFileVo);

    List<TFileVo> fileTicketProductList(TFileVo tFileVo);

    BaseResult deleteTicketProduct(TFileVo tFileVo);
    
    List<TFile> selectFileAppList(TFile record);


}
