package com.gogooma.turtleproject.view;


import android.content.Intent;
import android.os.Bundle;

import android.util.Log;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.gogooma.turtleproject.R;
import com.gogooma.turtleproject.adapter.ShopItemAdapter;
import com.gogooma.turtleproject.model.ShopItem;
import com.gogooma.turtleproject.network.CustomSharedPreferences;
import com.gogooma.turtleproject.network.InitMyapi;
import com.gogooma.turtleproject.network.RetrofitClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Callback;
import retrofit2.Call;
import retrofit2.Response;

public class ItemActivity extends AppCompatActivity {

    String gender, category;
    private RecyclerView recyclerView;
    private RetrofitClient retrofitClient;
    private InitMyapi initMyapi;
    ImageButton imgbtnGoMain, imgbtnGoBack;
    ArrayList<ShopItem> shopItemList;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);


        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));

        Intent itemintent = getIntent();
        this.gender = itemintent.getExtras().getString("gender");
        this.category = itemintent.getExtras().getString("category");
        Log.d("intent", "gender: " + gender + " category: " + category);

        imgbtnGoMain = findViewById(R.id.btn_go_main);
        imgbtnGoMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ItemActivity.this, MainActivity.class);
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

        //이미지 다운로드 실행
        itemImageDownload();
    }
    private void itemImageDownload() {
        retrofitClient = RetrofitClient.getInstance();
        initMyapi = RetrofitClient.getRetrofitInterface();
        initMyapi.getImageUrl(gender, category).enqueue(new Callback<ArrayList<ShopItem>>() {
            @Override
            public void onResponse(Call<ArrayList<ShopItem>> call, Response<ArrayList<ShopItem>> response) {
                if (response.isSuccessful()) {
                    shopItemList = response.body();
                    recyclerView.setAdapter(new ShopItemAdapter(ItemActivity.this, shopItemList));

                }else {
                    String message = "잠시 후 다시 시도해주세요";
                    //방금 추가
                    response.errorBody().close();
                    Toast.makeText(ItemActivity.this, message, Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<ArrayList<ShopItem>> call, Throwable t) {
                String message = t.getMessage();
                Toast.makeText(ItemActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });

    }


}