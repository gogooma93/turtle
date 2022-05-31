package com.gogooma.turtleproject.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.gogooma.turtleproject.R;
import com.gogooma.turtleproject.network.CustomSharedPreferences;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MypageActivity extends AppCompatActivity {

    private long pressedTime;
    ImageButton imgbtnGoMain, imgbtnGoBack;
    Button btnLogOut;
    BottomNavigationView bottomNavigationView;
    TextView tvcspUserEmail;
    private Context context;
    String uEmail, uName;

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
        setContentView(R.layout.activity_mypage);

        context = this;

        imgbtnGoMain = findViewById(R.id.btn_go_main);
        imgbtnGoMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MypageActivity.this, MainActivity.class);
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

        btnLogOut = findViewById(R.id.btn_logout);
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logOutShowDialog();

            }
        });

        this.uName = CustomSharedPreferences.getString(context, "username");
        //this.uEmail = CustomSharedPreferences.getString(context, "useremail");
        tvcspUserEmail = findViewById(R.id.tv_csp_useremail);
        tvcspUserEmail.setText(uName + " 님");

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.menu5);
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu2:
                        startActivity(new Intent(getApplicationContext(), ShopActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.menu3:
                        startActivity(new Intent(getApplicationContext(), FittingRoomActivity.class));
                        overridePendingTransition(0,0);
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
                        return true;
                }
                return false;
            }
        });
    }

    public void logout() {
        Intent intent = new Intent(MypageActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();

    }

    //로그아웃 메세지
    void logOutShowDialog() {

        AlertDialog.Builder msgBuilder = new AlertDialog.Builder(MypageActivity.this)
                .setTitle(uName + "님!")
                .setMessage("로그아웃 하시겠습니까?")
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                })
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        logout();
                        //Toast.makeText(ClotheActivity.this, "내 사진에 저장되었습니다.", Toast.LENGTH_LONG).show();
                        dialogInterface.cancel();
                    }
                });
        msgBuilder.show();
    }
}

