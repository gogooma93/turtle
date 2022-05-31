package com.gogooma.turtleproject.model;

import com.google.gson.annotations.SerializedName;

public class PreferPicture {

    @SerializedName("id")
    private int preferId;

    @SerializedName("serial_number")
    private String serialNumber;

    @SerializedName("cloth_number")
    private String clothNumber;

    @SerializedName("cloth_name")
    private String clothName;

    @SerializedName("cloth_image")
    private String clothImage;

    @SerializedName("model_image")
    private String modelImage;

    public PreferPicture(String clothImage) {

        this.clothImage = clothImage;
    }

    public int getPreferId() {
        return preferId;
    }

    public void setPreferId(int preferId) {
        this.preferId = preferId;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getClothNumber() {
        return clothNumber;
    }

    public void setClothNumber(String clothNumber) {
        this.clothNumber = clothNumber;
    }

    public String getClothName() {
        return clothName;
    }

    public void setClothName(String clothName) {
        this.clothName = clothName;
    }

    public String getClothImage() {
        return clothImage;
    }

    public void setClothImage(String clothImage) {
        this.clothImage = clothImage;
    }

    public String getModelImage() {
        return modelImage;
    }

    public void setModelImage(String modelImage) {
        this.modelImage = modelImage;
    }
}
