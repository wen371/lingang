package com.jw.common.support;

import java.util.HashMap;
import java.util.Map;

public class DTBDMap {

    public static Map<String,Map<String,String>>getDTBDMap(){
        Map<String,Map<String,String>> dtbdMap = new HashMap<>();
        Map<String,String> dtbdVal = new HashMap<>();
        dtbdVal.put("fieldAA","0");
        dtbdVal.put("fieldAC","0");
        dtbdVal.put("fieldAD","0");
        dtbdMap.put("1005",dtbdVal);
        dtbdVal.clear();
        dtbdVal.put("fieldAC","0");
        dtbdVal.put("fieldAD","3");
        dtbdVal.put("fieldAE","0");
        dtbdMap.put("1010",dtbdVal);
        dtbdVal.clear();
        dtbdVal.put("fieldAF","3");
        dtbdMap.put("1019",dtbdVal);
        dtbdVal.clear();
        dtbdVal.put("fieldAA","3");
        dtbdVal.put("fieldAC","0");
        dtbdVal.put("fieldAD","0");
        dtbdMap.put("1028",dtbdVal);
        dtbdVal.clear();
        dtbdVal.put("fieldAE","3");
        dtbdVal.put("fieldAA","0");
        dtbdVal.put("fieldAD","机动车");
        dtbdVal.put("fieldAG","非运营");
        dtbdMap.put("1101",dtbdVal);
        dtbdVal.clear();
        dtbdVal.put("fieldAA","0");
        dtbdVal.put("fieldAL","3");
        dtbdMap.put("1103",dtbdVal);
        dtbdVal.clear();
        dtbdVal.put("fieldAA","3");
        dtbdMap.put("1105",dtbdVal);
        dtbdVal.clear();
        dtbdVal.put("fieldAA","0");
        dtbdVal.put("fieldAB;","");
        dtbdVal.put("fieldAC","3");
        dtbdMap.put("1106",dtbdVal);
        dtbdVal.clear();
        dtbdVal.put("fieldAA","3");
        dtbdMap.put("1107",dtbdVal);
        dtbdVal.clear();
        dtbdVal.put("fieldAA","3");
        dtbdMap.put("1110",dtbdVal);
        dtbdVal.clear();
        dtbdVal.put("fieldAA","3");
        dtbdMap.put("1111",dtbdVal);
        dtbdVal.clear();
        dtbdVal.put("fieldAA","1");
        dtbdVal.put("fieldAB","小型汽车");
        dtbdVal.put("fieldAI","3");
        dtbdMap.put("1113",dtbdVal);
        dtbdVal.clear();
        dtbdVal.put("fieldAA","3");
        dtbdVal.put("fieldAC","0");
        dtbdMap.put("1121",dtbdVal);
        dtbdVal.clear();
        dtbdVal.put("fieldAC","3");
        dtbdMap.put("1122",dtbdVal);
        dtbdVal.clear();
        /*dtbdVal.put("fieldAX;","0");
        dtbdVal.put("fieldAZ","0");
        dtbdMap.put("1133",dtbdVal);
        dtbdVal.clear();*/
        dtbdVal.put("fieldAC","3");
        dtbdVal.put("fieldAA","0");
        dtbdMap.put("1135",dtbdVal);
        dtbdVal.clear();
        dtbdVal.put("fieldAA","0");
        dtbdVal.put("fieldAC","3");
        dtbdMap.put("1139",dtbdVal);
        dtbdVal.clear();
        dtbdVal.put("fieldAA","3");
        dtbdVal.put("fieldAB","C1");
        dtbdMap.put("1142",dtbdVal);
        dtbdVal.clear();
        dtbdVal.put("fieldAA","所有账户");
        dtbdVal.put("fieldAB","所有账户");
        dtbdMap.put("0311",dtbdVal);
        dtbdVal.clear();
        dtbdVal.put("fieldAA","一级建筑：钢骨、水泥、砖石结构且邻居结构登记相同");
        dtbdVal.put("fieldAB","一类建筑");
        dtbdVal.put("fieldAD","1-5分钟");
        dtbdVal.put("fieldAE","有效的安全措施");
        dtbdVal.put("fieldAF","好");
        dtbdVal.put("fieldAG","良好");
        dtbdVal.put("fieldAH","1");
        dtbdVal.put("fieldAJ","1");
        dtbdVal.put("fieldAO","团体客户");
        dtbdVal.put("fieldAP","不提供赔付率");
        dtbdMap.put("0303",dtbdVal);
        dtbdVal.clear();
        return dtbdMap;
    }
}