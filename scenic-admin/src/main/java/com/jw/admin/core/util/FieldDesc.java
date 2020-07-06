/**
 * Copyright (c) 2015-2016, Chill Zhuang 庄骞 (smallchill@163.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jw.admin.core.util;
import java.util.HashMap;

/**
 * 
 * 字段描述类
 *
 * <p>用于描述字段与代码表是否有对应关系，用于翻译代码
 * @author YLJ 2016年6月16日
 * @since 1.0
 */
public class FieldDesc
{
    private String fieldName = "";
    /**
     * 是否是代码表字段，如果是，则需要翻译
     */
    private boolean isCode = false;
    /**
     * 当字段是代码表时，根据此代码表进行翻译
     */
    private HashMap<String,String> codeItem = null;
    /**
     * 主要用于解决，字符类型的一串数字，写到csv中时，会默认显示成科学计数，为了显示成文本，该标志为true时在数字前加一个单引号
     */
    private boolean convertToString = false;
    
    /**
     * 是否采用固定的值
     */
    private boolean isFixed = false;
    /**
     * 固定的值
     */
    private String fixedValue = "";
    
    /**
     * 高级用法，可以通过该接口自定义对数据的处理
     */
    private IDataHandler handler = null;
    /**
     * 构造方法，当字段不需要翻译时，用此方面描述
     * @param name
     */
    public FieldDesc(String name)
    {
        this.fieldName = name;
    }
    
    /**
     * 构造方法，当字段需要转换成文本显示时，用此方面描述
     * @param name
     */
    public FieldDesc(String name, boolean isToString)
    {
        this.fieldName = name;
        this.convertToString = isToString;
    }
    
    /**
     * 构造方法，当字段需要进行代码翻译时，必须使用本方法
     * @param name
     * @param items
     */
    public FieldDesc(String name, HashMap<String,String> items)
    {
        this(name);
        this.isCode = true;
        this.codeItem = items;
    }
    /**
     * 构造方法，当字段需要显示固定内容时，使用本方法
     * @param name
     * @param value
     */
    public FieldDesc(String name, String value)
    {
        this(name);
        this.isFixed = true;
        this.fixedValue = value;
    }
    
    public FieldDesc(String name, IDataHandler h)
    {
        this(name);
        this.handler = h;
    }
    
    public boolean isConvertToString()
    {
        return convertToString;
    }

    public void setConvertToString(boolean convertToString)
    {
        this.convertToString = convertToString;
    }

    public String getFieldName()
    {
        return fieldName;
    }

    public void setFieldName(String fieldName)
    {
        this.fieldName = fieldName;
    }

    public HashMap<String, String> getCodeItem()
    {
        return codeItem;
    }

    public void setCodeItem(HashMap<String, String> codeItem)
    {
        this.codeItem = codeItem;
    }

    public boolean isCode()
    {
        return isCode;
    }

    public void setCode(boolean isCode)
    {
        this.isCode = isCode;
    }

    public boolean isFixed()
    {
        return isFixed;
    }

    public void setFixed(boolean isFixed)
    {
        this.isFixed = isFixed;
    }

    public String getFixedValue()
    {
        return fixedValue;
    }

    public void setFixedValue(String fixedValue)
    {
        this.fixedValue = fixedValue;
    }

    public IDataHandler getHandler()
    {
        return handler;
    }

    public void setHandler(IDataHandler handler)
    {
        this.handler = handler;
    }
}
