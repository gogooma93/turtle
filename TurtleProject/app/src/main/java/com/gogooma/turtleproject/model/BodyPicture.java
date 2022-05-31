package com.gogooma.turtleproject.model;

import com.google.gson.annotations.SerializedName;

public class BodyPicture {

    @SerializedName("id")
    private int myPhotoId;

    @SerializedName("my_photo_title")
    private String myPhotoTitle;

    @SerializedName("my_photo")
    private String myPhotoUrl;

    @SerializedName("user_id")
    private int userId;

    public BodyPicture(String myPhotoTitle, String myPhotoUrl) {
        this.myPhotoTitle = myPhotoTitle;
        this.myPhotoUrl = myPhotoUrl;
    }

    public int getMyPhotoId() {
        return myPhotoId;
    }

    public void setMyPhotoId(int myPhotoId) {
        this.myPhotoId = myPhotoId;
    }

    public String getMyPhotoTitle() {
        return myPhotoTitle;
    }

    public void setMyPhotoTitle(String myPhotoTitle) {
        this.myPhotoTitle = myPhotoTitle;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public BodyPicture(String myPhotoUrl) {

        this.myPhotoUrl = myPhotoUrl;
    }


    public String getMyPhotoUrl() {

        return myPhotoUrl;
    }

    public void setMyPhotoUrl(String myPhotoUrl) {

        this.myPhotoUrl = myPhotoUrl;
    }
}
