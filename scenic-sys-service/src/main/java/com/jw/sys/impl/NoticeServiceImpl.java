package com.jw.sys.impl;

import com.jw.sys.bean.vo.NoticeVo;
import com.jw.sys.dao.NoticeDao;
import com.jw.sys.api.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("noticeService")
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    private NoticeDao noticeDao;


    @Override
    public NoticeVo selectById(Integer noticeId) {
        return noticeDao.selectById(noticeId);
    }

    @Override
    public List<NoticeVo> list(String condition) {
        return noticeDao.list(condition);
    }

    @Override
    public List<NoticeVo> listAll() {
        return noticeDao.listAll();
    }

    @Override
    public void insert(NoticeVo notice) {
        noticeDao.insert(notice);
    }

    @Override
    public void deleteById(Integer noticeId) {
        noticeDao.deleteById(noticeId);
    }

    @Override
    public void updateById(Integer id) {
        noticeDao.updateById(id);
    }
}
