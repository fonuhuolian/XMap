package org.fonuhuolian.xmap;

public enum XAMapMode {

    //transit（公交）、driving（驾车）、walking（步行）、riding（骑行）、火车、长途汽车
    TRANSIT("1"), DRIVING("0"), WALKING("2"), RIDING("3"), TRAIN("4"), INTERCITYBUS("5");

    private String mode;


    XAMapMode(String mode) {
        this.mode = mode;
    }


    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
}
