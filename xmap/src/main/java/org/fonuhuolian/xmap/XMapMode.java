package org.fonuhuolian.xmap;

public enum XMapMode {

    XAMAP("高德"), XBAIDU("百度"), XTENCENT("腾讯");

    private String mapName;

    XMapMode(String mapName) {
        this.mapName = mapName;
    }

    public String getMapName() {
        return mapName;
    }
}