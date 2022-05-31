package com.gogooma.turtleproject.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.gogooma.turtleproject.R;
import com.gogooma.turtleproject.model.ChangeColor;
import com.gogooma.turtleproject.network.InitMyapi;
import com.gogooma.turtleproject.network.RetrofitClient;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangeActivity extends AppCompatActivity {

    private InitMyapi initMyapi;
    private RetrofitClient retrofitClient;
    private Context context;
    ImageButton imgbtnGoMain, imgbtnGoBack;
    TextView tvCloseImg, tvSaveImg;
    private ImageView imgvChanged;
    int changeId, colorR, colorG, colorB;
    Button btnCloseChange, btnSavechange;

    private ProgressBar pb;
    private ConstraintLayout layoutPb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);

        pb = findViewById(R.id.bar_pb);
        layoutPb = findViewById(R.id.layout_pb);

        context = this;

        imgbtnGoMain = findViewById(R.id.btn_go_main);
        imgbtnGoMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChangeActivity.this, MainActivity.class);
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
        this.changeId = intent.getExtras().getInt("changeId");
        this.colorR = intent.getExtras().getInt("color_r");
        this.colorG = intent.getExtras().getInt("color_g");
        this.colorB = intent.getExtras().getInt("color_b");
        Log.d("Intent", "changeId: " + changeId + " colorR: " + colorR + " colorG: " + colorG + " colorB: " + colorB );

        imgvChanged = findViewById(R.id.imgv_changed);
        downloadChangeColor();

        btnCloseChange = findViewById(R.id.btn_close_change);
        btnSavechange = findViewById(R.id.btn_save_change);

        btnCloseChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChangeActivity.this, ChangeColorActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnSavechange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChangeActivity.this, ClosetActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    //최종 색바꾸기 사진
    private void downloadChangeColor() {

        retrofitClient = RetrofitClient.getInstance();
        initMyapi = RetrofitClient.getRetrofitInterface();
        initMyapi.downloadChangedPhoto(changeId, colorR, colorG, colorB).enqueue(new Callback<ChangeColor>() {
            @Override
            public void onResponse(Call<ChangeColor> call, Response<ChangeColor> response) {
                Log.e("cloth_id", "id: " + changeId);
                ChangeColor changeColor = response.body();
                Picasso.get().load(changeColor.getChangedClothUrl()).into(imgvChanged);
                if (response.code() ==201) {
                    layoutPb.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ChangeColor> call, Throwable t) {
                String message = t.getMessage();
                Toast.makeText(ChangeActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }
}
