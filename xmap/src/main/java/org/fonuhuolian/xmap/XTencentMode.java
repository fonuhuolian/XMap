package org.fonuhuolian.xmap;

public enum XTencentMode {

    //transit（公交）、driving（驾车）、walking（步行）、riding（骑行）
    TRANSIT("bus"), DRIVING("drive"), WALKING("walk"), RIDING("bike");

    private String mode;


    XTencentMode(String mode) {
        this.mode = mode;
    }


    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
}
