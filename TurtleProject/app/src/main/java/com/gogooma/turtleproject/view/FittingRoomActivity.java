package com.gogooma.turtleproject.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.gogooma.turtleproject.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class FittingRoomActivity extends AppCompatActivity {

    private long pressedTime;
    ImageButton imgbtnGoMain, imgbtnGoBack;
    Button goFitting, goChageColor;
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
        setContentView(R.layout.activity_fittingroom);

        imgbtnGoMain = findViewById(R.id.btn_go_main);
        imgbtnGoMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FittingRoomActivity.this, MainActivity.class);
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

        goFitting = findViewById(R.id.btn_go_fitting);
        goChageColor = findViewById(R.id.btn_go_changecolor);

        goFitting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FittingRoomActivity.this, ClotheActivity.class);
                startActivity(intent);
            }
        });

        goChageColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FittingRoomActivity.this, ChangeColorActivity.class);
                startActivity(intent);
            }
        });


        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.menu3);
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu2:
                        startActivity(new Intent(getApplicationContext(), ShopActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.menu3:
                        return true;
                    case R.id.menu1:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.menu4:
                        startActivity(new Intent(getApplicationContext(), ClosetActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.menu5:
                        startActivity(new Intent(getApplicationContext(), MypageActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }
}
