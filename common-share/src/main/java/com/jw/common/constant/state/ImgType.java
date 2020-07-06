package com.jw.common.constant.state;

public enum  ImgType {

    RoutesRecommendedCover(7,"线路推荐封面图"),
    RoutesRecommendedSlide(8,"线路推荐轮播图"),
    FestivalActivityCover(9,"节庆活动封面图");
    private Integer type;
    private String name;

    ImgType(Integer type, String name){
        this.type = type;
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
