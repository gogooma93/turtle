package com.gogooma.turtleproject.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.gogooma.turtleproject.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

public class ShopActivity extends AppCompatActivity {

    private long pressedTime;
    TabLayout tabLayout;
    View page_w, page_m;
    Context context;
    TextView tvWTshirt, tvWKnit, tvWSweatshirt, tvWShirt, tvWBlazer, tvMTshirt, tvMKnit, tvMSweatshirt, tvMShirt, tvMBlazer;
    ImageButton imgbtnGoMain, imgbtnGoBack;
    BottomNavigationView bottomNavigationView;

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        if (pressedTime == 0) {
            Toast.makeText(this, "한번 더 누르면 종료됩니다", Toast.LENGTH_SHORT).show();
            pressedTime = System.currentTimeMillis();
        }else {
            int seconds = (int) (System.currentTimeMillis() - pressedTime);

            if (seconds >20000) {
                pressedTime = 0;
            }else{
                ActivityCompat.finishAffinity(this);
                System.exit(0);
            }
        }
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        imgbtnGoMain = findViewById(R.id.btn_go_main);
        imgbtnGoMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShopActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        imgbtnGoBack = findViewById(R.id.btn_go_back);
        imgbtnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //생성
        page_w = findViewById(R.id.page_w);
        page_m = findViewById(R.id.page_m);
        tvWTshirt = findViewById(R.id.tv_w_tshirt);
        tvWKnit = findViewById(R.id.tv_w_knit);
        tvWSweatshirt = findViewById(R.id.tv_w_sweatshirt);
        tvWShirt = findViewById(R.id.tv_w_shirt);
        tvWBlazer = findViewById(R.id.tv_w_blazer);
        tvMTshirt = findViewById(R.id.tv_m_tshirt);
        tvMKnit = findViewById(R.id.tv_m_knit);
        tvMSweatshirt = findViewById(R.id.tv_m_sweatshirt);
        tvMShirt = findViewById(R.id.tv_m_shirt);
        tvMBlazer = findViewById(R.id.tv_m_blazer);
        context = this;

        //탭 뷰
        tabLayout = findViewById(R.id.tab_gender);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int index = tab.getPosition();
                changeGenderView(index);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }

        });

        //각 버튼 클릭시 화면 전환
        tvWTshirt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent shopintent = new Intent(ShopActivity.this, ItemActivity.class);
                shopintent.putExtra("gender", "여성");
                shopintent.putExtra("category", "티셔츠");
                startActivity(shopintent);
//                finish();
            }
        });
        tvWKnit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent shopintent = new Intent(ShopActivity.this, ItemActivity.class);
                shopintent.putExtra("gender", "여성");
                shopintent.putExtra("category", "니트");
                startActivity(shopintent);
//                finish();
            }
        });
        tvWSweatshirt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent shopintent = new Intent(ShopActivity.this, ItemActivity.class);
                shopintent.putExtra("gender", "여성");
                shopintent.putExtra("category", "맨투맨");
                startActivity(shopintent);
//                finish();
            }
        });
        tvWShirt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent shopintent = new Intent(ShopActivity.this, ItemActivity.class);
                shopintent.putExtra("gender", "여성");
                shopintent.putExtra("category", "셔츠");
                startActivity(shopintent);
//                finish();
            }
        });
        tvWBlazer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent shopintent = new Intent(ShopActivity.this, ItemActivity.class);
                shopintent.putExtra("gender", "여성");
                shopintent.putExtra("category", "블레이저");
                startActivity(shopintent);
//                finish();
            }
        });
        tvMTshirt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent shopintent = new Intent(ShopActivity.this, ItemActivity.class);
                shopintent.putExtra("gender", "남성");
                shopintent.putExtra("category", "티셔츠");
                startActivity(shopintent);
//                finish();
            }
        });
        tvMKnit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent shopintent = new Intent(ShopActivity.this, ItemActivity.class);
                shopintent.putExtra("gender", "남성");
                shopintent.putExtra("category", "니트");
                startActivity(shopintent);
//                finish();
            }
        });
        tvMSweatshirt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent shopintent = new Intent(ShopActivity.this, ItemActivity.class);
                shopintent.putExtra("gender", "남성");
                shopintent.putExtra("category", "맨투맨");
                startActivity(shopintent);
//                finish();
            }
        });
        tvMShirt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent shopintent = new Intent(ShopActivity.this, ItemActivity.class);
                shopintent.putExtra("gender", "남성");
                shopintent.putExtra("category", "셔츠");
                startActivity(shopintent);
//                finish();
            }
        });
        tvMBlazer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent shopintent = new Intent(ShopActivity.this, ItemActivity.class);
                shopintent.putExtra("gender", "남성");
                shopintent.putExtra("category", "블레이저");
                startActivity(shopintent);
//                finish();
            }
        });

        //하단바
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.menu2);
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu2:
                        return true;
                    case R.id.menu3:
                        startActivity(new Intent(getApplicationContext(), FittingRoomActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.menu1:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.menu4:
                        startActivity(new Intent(getApplicationContext(), ClosetActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.menu5:
                        startActivity(new Intent(getApplicationContext(), MypageActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
    }

    //탭 뷰 교체 메서드
    private void changeGenderView(int index) {
        switch (index) {
            case 0:
                page_w.setVisibility(View.VISIBLE);
                page_m.setVisibility(View.INVISIBLE);
                break;
            case 1:
                page_w.setVisibility(View.INVISIBLE);
                page_m.setVisibility(View.VISIBLE);
                break;
        }
    }
}


