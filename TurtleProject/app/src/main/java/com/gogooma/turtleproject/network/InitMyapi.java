package com.gogooma.turtleproject.network;

import com.gogooma.turtleproject.model.BodyPicture;
import com.gogooma.turtleproject.model.ChangeColor;
import com.gogooma.turtleproject.model.LoginResponse;
import com.gogooma.turtleproject.model.Clothe;
import com.gogooma.turtleproject.model.PreferPicture;
import com.gogooma.turtleproject.model.ShopItem;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface InitMyapi {


//    @FormUrlEncoded
//    @POST("accounts/register/")
//    Call<SignupResponse> getSignupResponse(@Field("username") String username, @Field("email") String email, @Field("password1") String password1, @Field("password2") String password2);



    @FormUrlEncoded
    @POST("api/token/")
    Call<LoginResponse> getLoginResponse(@Field("username") String username, @Field("password") String password);

    @FormUrlEncoded
    @POST("upload_clothes/cloth_list/")
    Call<ArrayList<ShopItem>> getImageUrl(@Field("gender") String gender, @Field("category") String category);

    @Multipart
    @POST("fitting/upload_user_photo/")
//    Call<RequestBody> uploadProfilePhoto(@Part MultipartBody.Part part);
    Call<ResponseBody> uploadProfilePhoto(@Part MultipartBody.Part part);

    @GET("fitting/upload_user_photo/")
    Call<ArrayList<BodyPicture>> downloadProfilePhoto();

    @FormUrlEncoded
    @POST("fitting/my_prefer_cloth/")
    Call<ResponseBody> uploadPreferCloth(@Field("serial_number") String serialnumber, @Field("color_number") String colornumber);

    @GET("fitting/my_prefer_cloth/")
    Call <List<PreferPicture>> downloadPreferCloth();

    @FormUrlEncoded
    @POST("fitting_photo/fitting_my_photo/")
    Call<Clothe> downloadFinalPhoto(@Field("my_photo_id") int myPhotoId, @Field("cloth_id") int myPreferClothId);


//    @DELETE("fitting/my_prefer_cloth/")
//    Call<DeletePref> deletePreferCloth(@Path("cloth_id") int deleteId);

    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "fitting/my_prefer_cloth/", hasBody = true)
    Call<Void> deletePreferCloth(@Field("cloth_id") int deleteId);

    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "fitting/upload_user_photo/", hasBody = true)
    Call<Void> deleteProfilePhoto(@Field("id") int delProfileId);


    @FormUrlEncoded
    @POST("upload_clothes/change_color_png/")
    Call<ChangeColor> downloadChangedPhoto(@Field("cloth_id") int changeId, @Field("color_r") int colorR, @Field("color_g") int colorG, @Field("color_b") int colorB);

    @GET("fitting_photo/fitting_my_photo/")
    Call <List<Clothe>> downloadFittingList();


}
