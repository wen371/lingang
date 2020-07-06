package com.jw.sys.api;

import com.jw.sys.bean.vo.NoticeVo;

import java.util.List;


public interface NoticeService {


    public NoticeVo selectById(Integer noticeId) ;


    List<NoticeVo> list(String condition);
    List<NoticeVo> listAll();

    void insert(NoticeVo notice);

    void deleteById(Integer noticeId);

    void updateById(Integer id);
}
