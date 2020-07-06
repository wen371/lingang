package com.jw.admin.core.util;

/**
 * 
 * 数据加工接口，用于报表导出时，自定义数据加工方法对数据进行处理
 *
 * <p>detailed comment
 * @author YLJ 2016年6月22日
 * @see
 * @since 1.0
 */
public interface IDataHandler
{
    public String process(Object value);
}
