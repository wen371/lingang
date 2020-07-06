package com.jw.sys.impl;

import com.jw.sys.bean.vo.SysAdminLogVo;
import com.jw.sys.dao.SysAdminLogDao;
import com.jw.sys.bean.entity.SysAdminLog;
import com.jw.sys.api.ISysAdminLogService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service("sysAdminLogService")
public class SysAdminLogServiceImpl implements ISysAdminLogService {
    @Autowired
    private SysAdminLogDao sysAdminLogDao;
    private static final Log logger = LogFactory.getLog(SysAdminLogServiceImpl.class);


    @Override
    public int insertAdminLog(SysAdminLogVo vo) {
        SysAdminLog log =new SysAdminLog();
        BeanUtils.copyProperties(vo,log);
        sysAdminLogDao.insert(log);
        return log.getId();
    }

    @Override
    public List<SysAdminLogVo> findSysAdminLogs(Integer num, Integer pageSize,String describe,String createTime) {
        try {
         List<SysAdminLogVo>  vos = new ArrayList<>();
         List<SysAdminLog>  logs = sysAdminLogDao.findSysAdminLogs(num,pageSize,describe,createTime);
         for(SysAdminLog log:logs){
             SysAdminLogVo vo =new SysAdminLogVo();
             BeanUtils.copyProperties(log,vo);
             vos.add(vo);
         }
            return  vos;
        }catch (Exception e){
            logger.error("日志查询失败:",e);
            return null;
        }
    }

    @Override
    public int getAdminLogCount(String describe, String  createTime) {
        return sysAdminLogDao.getAdminLogCount( describe,   createTime);
    }
}
