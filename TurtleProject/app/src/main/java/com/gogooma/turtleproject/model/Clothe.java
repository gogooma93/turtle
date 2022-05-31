package com.gogooma.turtleproject.model;

import com.google.gson.annotations.SerializedName;

public class Clothe {

    @SerializedName("id")
    private int fittingId;

    @SerializedName("my_photo_id")
    private int myPhotoId;

    @SerializedName("cloth_id")
    private int myPreferClothId;

    @SerializedName("fitting_photo")
    private String fittingPhoto;

    public Clothe(int myPhotoId, int myPreferClothId) {
        this.myPhotoId = myPhotoId;
        this.myPreferClothId = myPreferClothId;
    }

    public int getfittingId() {
        return fittingId;
    }

    public void setfittingId(int perfoUsermId) {
        this.fittingId = fittingId;
    }

    public int getMyPhotoId() {
        return myPhotoId;
    }

    public void setMyPhotoId(int myPhotoId) {
        this.myPhotoId = myPhotoId;
    }

    public int getMyPreferClothId() {
        return myPreferClothId;
    }

    public void setMyPreferClothId(int myPreferClothId) {
        this.myPreferClothId = myPreferClothId;
    }

    public String getFittingPhoto() {
        return fittingPhoto;
    }

    public void setFittingPhoto(String fittingPhoto) {
        this.fittingPhoto = fittingPhoto;
    }
}
