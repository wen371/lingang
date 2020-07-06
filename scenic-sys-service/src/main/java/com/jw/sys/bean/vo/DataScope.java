package com.jw.sys.bean.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 数据范围
 *
 * @author fengshuonan
 * @date 2017-07-23 22:19
 */
public class DataScope implements Serializable{

    private static final long serialVersionUID = 305039731985205470L;
    /**
     * 限制范围的字段名称
     */
    private String scopeName = "scenicid";

    /**
     * 具体的数据范围
     */
    private List<Integer> scenicIds;

    public DataScope() {
    }

    public DataScope(List<Integer> scenicIds) {
        this.scenicIds = scenicIds;
    }

    public DataScope(String scopeName, List<Integer> scenicIds) {
        this.scopeName = scopeName;
        this.scenicIds = scenicIds;
    }

    public List<Integer> getScenicIdss() {
        return scenicIds;
    }

    public void setScenicIdss(List<Integer> scenicIds) {
        this.scenicIds = scenicIds;
    }

    public String getScopeName() {
        return scopeName;
    }

    public void setScopeName(String scopeName) {
        this.scopeName = scopeName;
    }
}
