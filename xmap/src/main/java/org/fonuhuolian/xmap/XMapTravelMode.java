package org.fonuhuolian.xmap;

public enum XMapTravelMode {

    AMAP_TRANSIT("高德", "公交", "1"), AMAP_DRIVING("高德", "驾车", "0"), AMAP_WALKING("高德", "步行", "2"), AMAP_RIDING("高德", "骑行", "3"), AMAP_TRAIN("高德", "火车", "4"), AMAP_INTERCITYBUS("高德", "长途汽车", "5"),
    BAIDU_TRANSIT("百度", "公交", "transit"), BAIDU_DRIVING("百度", "驾车", "driving"), BAIDU_WALKING("百度", "步行", "walking"), BAIDU_RIDING("百度", "骑行", "riding"),
    TENCENT_TRANSIT("腾讯", "公交", "bus"), TENCENT_DRIVING("腾讯", "驾车", "drive"), TENCENT_WALKING("腾讯", "步行", "walk"), TENCENT_RIDING("腾讯", "骑行", "bike");


    private String mapName;
    private String modeName;
    private String mode;

    XMapTravelMode(String mapName, String modeName, String mode) {
        this.mode = mode;
        this.mapName = mapName;
        this.modeName = modeName;
    }

    public String getMode() {
        return mode;
    }

    public String getMapName() {
        return mapName;
    }
}
