package com.gogooma.turtleproject.view;

import android.app.ProgressDialog;
import android.content.Context;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.gogooma.turtleproject.R;
import com.gogooma.turtleproject.model.Clothe;
import com.gogooma.turtleproject.network.InitMyapi;
import com.gogooma.turtleproject.network.RetrofitClient;
import com.gogooma.turtleproject.network.ThreadProgress;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ComposeActivity extends AppCompatActivity {

    int myPhotoId, myPreferClothId;
    private ImageView imgvPerformance;
    private InitMyapi initMyapi;
    private RetrofitClient retrofitClient;
    private ProgressDialog progressDialog;
    private Context context;
    ImageButton imgbtnGoMain, imgbtnGoBack;
    Button btnCloseImg, btnSeeImg;

    //아래로 추가 프로그레스 바
    private ProgressBar prob;
    private ConstraintLayout layoutProgress;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);

        //아래로 추가 프로그레스바
        prob = findViewById(R.id.bar_progress);
        layoutProgress = findViewById(R.id.layout_prob);

        context = this;
        imgbtnGoMain = findViewById(R.id.btn_go_main);
        imgbtnGoMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ComposeActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        imgbtnGoBack = findViewById(R.id.btn_go_back);
        imgbtnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Intent intent = getIntent();
        this.myPhotoId = intent.getExtras().getInt("myPhotoId");
        this.myPreferClothId = intent.getExtras().getInt("preferId");
        Log.d("Intent", "myPhotoId: " + myPhotoId + " preferId: " + myPreferClothId);

        imgvPerformance = findViewById(R.id.imgv_perform);
        downloadCompositionPhoto();

        btnCloseImg = findViewById(R.id.btn_close_photo);
        btnSeeImg = findViewById(R.id.btn_save_photo);

        btnCloseImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ComposeActivity.this, ClotheActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnSeeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ComposeActivity.this, ClosetActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }


    //최종합성사진
    private void downloadCompositionPhoto() {

        retrofitClient = RetrofitClient.getInstance();
        initMyapi = RetrofitClient.getRetrofitInterface();
        initMyapi.downloadFinalPhoto(myPhotoId, myPreferClothId).enqueue(new Callback<Clothe>() {
            @Override
            public void onResponse(Call<Clothe> call, Response<Clothe> response) {
                Clothe clothe = response.body();
                Picasso.get().load(clothe.getFittingPhoto()).into(imgvPerformance);
                if (response.code() == 201) {
                    layoutProgress.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<Clothe> call, Throwable t) {
                String message = t.getMessage();
                Toast.makeText(ComposeActivity.this, message, Toast.LENGTH_LONG).show();

            }
        });

    }






//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_performance);
//
//        context = this;
//
//        progressDialog = new ProgressDialog(context);
//        progressDialog.setTitle("Loading");
//        progressDialog.setMessage("잠시만 기다려주세요.");
//        progressDialog.show();
//
//        imgbtnGoMain = findViewById(R.id.btn_go_main);
//        imgbtnGoMain.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(PerformanceActivity.this, MainActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        });
//
//        imgbtnGoBack = findViewById(R.id.btn_go_back);
//        imgbtnGoBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });
//
//        이미지 고정 불러오기
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                progressDialog.dismiss();
//                Picasso.get().load("https://turtle-project.s3.amazonaws.com/media/images/fitting_test/4174006942_0.jpg").into(imgvPerformance);
//            }
//        }, 7000);
//
//        imgvPerformance = findViewById(R.id.imgv_perform);
        //downloadTestVersion();
        //Picasso.get().load("https://turtle-project.s3.amazonaws.com/media/images/fitting_test/5644035800_0.jpg").into(imgvPerformance);
    }


