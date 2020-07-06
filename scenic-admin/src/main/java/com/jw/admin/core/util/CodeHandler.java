package com.jw.admin.core.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * 
 * 代码翻译工具
 *
 * <p>用于对查询的结果集进行代码翻译
 * @author YLJ 2016年6月14日
 * @since 1.0
 */
public class CodeHandler
{
    /**
     * 翻译单个字段
     * @param fieldValue    字段的值　
     * @param codeItems     代码表，字段值参照该代码表进行翻译
     * @return  ""表示未翻译成功,否则成功返回代码名称
     */
    public static String convertSingleData(String fieldValue,Map<String,String>codeItems)
    {
        if(fieldValue == null || "".equals(fieldValue))
        {
            return "";
        }
        if(codeItems == null || codeItems.isEmpty())
        {
            return "";
        }
        
        // 取代码值
        String value = codeItems.get(fieldValue);
        if(value != null)
        {
            return value;
        }
        return "";
    }
    /**
     * 代码翻译
     * @param datas 数据集
     * @param codeItems 代码表
     * @param fieldName 数据集中需要翻译的属性名
     */
    public static void convertDatas(List<?>datas,Map<String,String>codeItems,String fieldName)
    {
        // 校验
        if(datas == null || datas.isEmpty())
        {
            // 数据集中无记录，不需要翻译。直接返回
            return;
        }
        if(codeItems == null || codeItems.isEmpty())
        {
            // 代码表为空，无法翻译，直接返回
            return;
        }
        if(fieldName == null || "".equals(fieldName))
        {
            // 未指定需要翻译的属性名称，无法判断翻译哪一列数据，直接返回
            return;
        }
        // 取出数据集中的一个对象，为了获得其Class类型        
        Object tmp = datas.get(0);
        Class<? extends Object> classType = tmp.getClass();
        // 构建getter和setter方法的名称
        //String getMethodName = "get" + fieldName.substring(0,1).toUpperCase() + fieldName.substring(1);
        //String setMethodName = "set" + fieldName.substring(0,1).toUpperCase() + fieldName.substring(1);
        
        String getMethodName = ExportReportTool.buildGetMethodName(fieldName);
        String setMethodName = ExportReportTool.buildSetMethodName(fieldName);
        try
        {
            // 获得get方法对象
            Method getMethod = classType.getDeclaredMethod(getMethodName, new Class[]{});
            Field field = classType.getDeclaredField(fieldName);
            // 获得set方法对象
            Method setMethod = classType.getDeclaredMethod(setMethodName, new Class[]{field.getType()});
            
            String value = "";
            String code = "";
            // 准备开始翻译
            for(Object obj : datas)
            {
                // 取代码值
                code = String.valueOf(getMethod.invoke(obj, new Object[]{}));
                value = codeItems.get(code);
                if(value != null)
                {
                    // 找到对应的代码名称
                    setMethod.invoke(obj, new Object[]{value});
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return;
        }
        
    }
}
