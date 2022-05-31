package com.gogooma.turtleproject.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ShopItem implements Parcelable {

    @SerializedName("id")
    private int id;
    @SerializedName("serial_number")
    private String serialNum;
    @SerializedName("color_number")
    private String colorNum;
    @SerializedName("color")
    private String color;
    @SerializedName("cloth_name")
    private String clothName;
    @SerializedName("cloth_price")
    private int clothPrice;
    @SerializedName("gender")
    private String gender;
    @SerializedName("category")
    private String category;
    @SerializedName("description")
    private String description;
    @SerializedName("cloth_image")
    private String clothImgUrl;
    @SerializedName("model_image")
    private String modelImgUrl;

    public ShopItem(String gender, String category) {
        this.gender = gender;
        this.category = category;
    }

    protected ShopItem(Parcel in) {
        id = in.readInt();
        serialNum = in.readString();
        colorNum = in.readString();
        color = in.readString();
        clothName = in.readString();
        clothPrice = in.readInt();
        gender = in.readString();
        category = in.readString();
        description = in.readString();
        clothImgUrl = in.readString();
        modelImgUrl = in.readString();
    }

    public static final Creator<ShopItem> CREATOR = new Creator<ShopItem>() {
        @Override
        public ShopItem createFromParcel(Parcel in) {
            return new ShopItem(in);
        }

        @Override
        public ShopItem[] newArray(int size) {
            return new ShopItem[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(String serialNum) {
        this.serialNum = serialNum;
    }

    public String getColorNum() {
        return colorNum;
    }

    public void setColorNum(String colorNum) {
        this.colorNum = colorNum;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getClothName() {
        return clothName;
    }

    public void setClothName(String clothName) {
        this.clothName = clothName;
    }

    public int getClothPrice() {
        return clothPrice;
    }

    public void setClothPrice(int clothPrice) {
        this.clothPrice = clothPrice;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getClothImgUrl() {
        return clothImgUrl;
    }

    public void setClothImgUrl(String clothImgUrl) {
        this.clothImgUrl = clothImgUrl;
    }

    public String getModelImgUrl() {
        return modelImgUrl;
    }

    public void setModelImgUrl(String modelImgUrl) {
        this.modelImgUrl = modelImgUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.id);
        parcel.writeString(this.serialNum);
        parcel.writeString(this.colorNum);
        parcel.writeString(this.color);
        parcel.writeString(this.clothName);
        parcel.writeInt(this.clothPrice);
        parcel.writeString(this.gender);
        parcel.writeString(this.category);
        parcel.writeString(this.description);
        parcel.writeString(this.clothImgUrl);
        parcel.writeString(this.modelImgUrl);
    }
}
