package com.jw.common.enm;

/**
 * Created by soft on 2018/1/28.
 */
public enum InsureType implements CodeEnum<String, InsureType, String> {

    YIWAI("0", "意外"),
    JTLY("1", "交通旅游"),
    JC("2", "家财"),
    JK("3", "健康"),
    TS("4", "特色");

    private String code;
    private String name;

    private InsureType(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static String getValueByCode(String code) {
        if (YIWAI.code.equals(code)) {
            return YIWAI.getName();
        }
        if (JTLY.code.equals(code)) {
            return JTLY.getName();
        }
        if (JC.code.equals(code)) {
            return JC.getName();
        }
        if (JK.code.equals(code)) {
            return JK.getName();
        }
        if (TS.code.equals(code)) {
            return TS.getName();
        }
        return null;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getValue() {
        return this.code;
    }
}
