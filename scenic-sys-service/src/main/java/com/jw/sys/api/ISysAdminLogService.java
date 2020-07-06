package com.jw.sys.api;


import com.jw.sys.bean.vo.SysAdminLogVo;

import java.util.List;

public interface ISysAdminLogService {
    int  insertAdminLog(SysAdminLogVo vo);
    List<SysAdminLogVo> findSysAdminLogs(Integer num, Integer pageSize, String describe, String createTime);
    int getAdminLogCount(String describe, String createTime);
}
