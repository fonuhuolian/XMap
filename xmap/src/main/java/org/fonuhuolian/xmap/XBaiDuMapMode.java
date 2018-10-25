package org.fonuhuolian.xmap;

public enum XBaiDuMapMode {

    //transit（公交）、driving（驾车）、walking（步行）、riding（骑行）
    TRANSIT("transit"), DRIVING("driving"), WALKING("walking"), RIDING("riding");

    private String mode;


    XBaiDuMapMode(String mode) {
        this.mode = mode;
    }


    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
}
