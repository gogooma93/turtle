package com.gogooma.turtleproject.model;

import com.google.gson.annotations.SerializedName;

public class ChangeColor {

    @SerializedName("cloth_id")
    private int changeId;

    @SerializedName("color_r")
    private int colorR;

    @SerializedName("color_g")
    private int colorG;

    @SerializedName("color_b")
    private int colorB;

    @SerializedName("changed_cloth")
    private String changedClothUrl;

    @SerializedName("cloth")
    private String beforeClothUrl;

    @SerializedName("color")
    private String colorCode;


//    public ChangeColor(int changeId, int colorR, int colorG, int colorB) {
//        this.changeId = changeId;
//        this.colorR = colorR;
//        this.colorG = colorG;
//        this.colorB = colorB;
//    }

    public int getChangeId() {
        return changeId;
    }

    public void setChangeId(int changeId) {
        this.changeId = changeId;
    }

    public int getColorR() {
        return colorR;
    }

    public void setColorR(int colorR) {
        this.colorR = colorR;
    }

    public int getColorG() {
        return colorG;
    }

    public void setColorG(int colorG) {
        this.colorG = colorG;
    }

    public int getColorB() {
        return colorB;
    }

    public void setColorB(int colorB) {
        this.colorB = colorB;
    }

    public String getChangedClothUrl() {
        return changedClothUrl;
    }

    public void setChangedClothUrl(String changedClothUrl) {
        this.changedClothUrl = changedClothUrl;
    }

    public String getBeforeClothUrl() {
        return beforeClothUrl;
    }

    public void setBeforeClothUrl(String beforeClothUrl) {
        this.beforeClothUrl = beforeClothUrl;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }
}
