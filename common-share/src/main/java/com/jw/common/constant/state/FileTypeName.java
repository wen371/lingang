package com.jw.common.constant.state;

/**
 * Created by huangfeng on 2018/1/19.
 */
public enum FileTypeName {
    FILE_TYPE_0(0, "标题图"),
    FILE_TYPE_1(1, "轮播图"),
    FILE_TYPE_2(2, "投保须知"),
    FILE_TYPE_3(3, "理赔指南"),
    FILE_TYPE_4(4, "常见问题"),
    FILE_TYPE_5(5, "条款附件"),
    FILE_TYPE_6(6, "投保须知附件"),
    FILE_TYPE_7(7, "理赔指南附件"),
    FILE_TYPE_8(8, "偿付能力披露附件"),
    FILE_TYPE_9(9, "健康告知附件");

    Integer type;
    String message;

    FileTypeName(Integer type, String message) {
        this.type = type;
        this.message = message;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static String valueOf(Integer type) {
        if (type == null) {
            return "";
        } else {
            for (FileTypeName s : FileTypeName.values()) {
                if (s.getType() == type) {
                    return s.getMessage();
                }
            }
            return "";
        }
    }
}
