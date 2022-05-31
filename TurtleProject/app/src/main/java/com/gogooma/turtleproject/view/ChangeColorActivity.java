package com.gogooma.turtleproject.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gogooma.turtleproject.R;
import com.gogooma.turtleproject.adapter.PreferPhotoAdapter;
import com.gogooma.turtleproject.model.PreferPicture;
import com.gogooma.turtleproject.network.InitMyapi;
import com.gogooma.turtleproject.network.RetrofitClient;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangeColorActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    ImageButton imgbtnGoMain, imgbtnGoBack;
    SeekBar sbRed, sbGreen, sbBlue, sbAlpha;
    int red=0, green=0, blue=0, alpha=255;
    Button btnSeeColor, btnSelectColor, btnExcuteChangeColor;
    View vColor, vSelectedColor;
    EditText etvRed, etvGreen, etvBlue;
    TextView tvHexValue, tvArgbValue;
    private ConstraintLayout constraintLayout;
    private RecyclerView preferRecyclerv;
    private PreferPhotoAdapter preferPhotoAdapter;
    private RetrofitClient retrofitClient;
    private InitMyapi initMyapi;
    public static Context mcontext;
    int preferId;
    String preferUrl;
    ImageView imgvSelectedPref;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changecolor);

        mcontext = this;

        imgvSelectedPref = findViewById(R.id.imgv_selected_clothes);
        preferRecyclerv = findViewById(R.id.recyclerv_like_cloth);
        downloadPreferPhoto();

        constraintLayout = findViewById(R.id.layout_relative);
        constraintLayout.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                hideKeyboard();
                return false;
            }
        });

        imgbtnGoMain = findViewById(R.id.btn_go_main);
        imgbtnGoMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChangeColorActivity.this, MainActivity.class);
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

        sbAlpha = findViewById(R.id.sb_alpha);
        sbRed = findViewById(R.id.sb_red);
        sbGreen = findViewById(R.id.sb_green);
        sbBlue = findViewById(R.id.sb_blue);
        btnSeeColor = findViewById(R.id.btn_see_color);
        btnSelectColor = findViewById(R.id.btn_select_color);
        vColor = findViewById(R.id.v_color);
        vSelectedColor = findViewById(R.id.v_selected_color);
        etvRed = findViewById(R.id.etv_red);
        etvGreen = findViewById(R.id.etv_green);
        etvBlue = findViewById(R.id.etv_blue);
        tvHexValue = findViewById(R.id.tv_hex_value);
        tvArgbValue = findViewById(R.id.tv_argb_value);

        btnSeeColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                red = Integer.parseInt(etvRed.getText().toString());
                green = Integer.parseInt(etvGreen.getText().toString());
                blue = Integer.parseInt(etvBlue.getText().toString());

                vColor.setBackgroundColor(Color.rgb(red, green, blue));

                btnSelectColor.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        vSelectedColor.setBackgroundColor(Color.rgb(red, green, blue));
                        Log.e("color input", "rgb"+ red + " " + green + " " + blue);

                    }
                });

            }
        });


        sbAlpha.setOnSeekBarChangeListener(this);
        sbRed.setOnSeekBarChangeListener(this);
        sbGreen.setOnSeekBarChangeListener(this);
        sbBlue.setOnSeekBarChangeListener(this);

        btnExcuteChangeColor = findViewById(R.id.btn_excute_changecolor);
        btnExcuteChangeColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChangeColorActivity.this, ChangeActivity.class);
                intent.putExtra("changeId", preferId);
                intent.putExtra("color_r", red);
                intent.putExtra("color_g", green);
                intent.putExtra("color_b", blue);
                startActivity(intent);
                Log.e("color", "code: " + preferId + " " + red + " " + green + " " + blue);
                finish();
            }
        });

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        switch (seekBar.getId()) {
            case R.id.sb_red:
                red = i;
                break;
            case R.id.sb_green:
                green = i;
                break;
            case R.id.sb_blue:
                blue = i;
                break;
            case R.id.sb_alpha:
                alpha = i;
                break;
        }

        vColor.setBackgroundColor(Color.argb(alpha, red, green, blue));
        String code = Hexcode(alpha, red, green, blue);
        tvHexValue.setText(String.format("(%d,%d,%d,%d)", alpha, red, green, blue));
        tvArgbValue.setText(code.toUpperCase());

        btnSelectColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vSelectedColor.setBackgroundColor(Color.rgb(red, green, blue));
                Log.e("color", "code: " + red + " " + green + " " + blue);
            }
        });

    }

    private String Hexcode(int alpha, int red, int green, int blue) {
        String code;
        code = "#";
        code += Integer.toHexString(alpha);
        code += Integer.toHexString(red);
        code += Integer.toHexString(green);
        code += Integer.toHexString(blue);
        return code;
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    void hideKeyboard()
    {
        InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(this.constraintLayout.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private void downloadPreferPhoto() {

        retrofitClient = RetrofitClient.getInstance();
        initMyapi = RetrofitClient.getRetrofitInterface();
        initMyapi.downloadPreferCloth().enqueue(new Callback<List<PreferPicture>>() {
            @Override
            public void onResponse(Call<List<PreferPicture>> call, Response<List<PreferPicture>> response) {
                if (response.isSuccessful()) {
                    List<PreferPicture> preferPictureList = response.body();
                    preferPhotoAdapter = new PreferPhotoAdapter(ChangeColorActivity.this, preferPictureList);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ChangeColorActivity.this, LinearLayoutManager.HORIZONTAL, false);
                    preferRecyclerv.setLayoutManager(layoutManager);
                    preferRecyclerv.setAdapter(preferPhotoAdapter);
                }else {
                    String message = "잠시 후 다시 시도해주세요";
                    //방금 추가
                    response.errorBody().close();
                    Toast.makeText(ChangeColorActivity.this, message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<PreferPicture>> call, Throwable t) {
                String message = t.getMessage();
                Toast.makeText(ChangeColorActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }

    //찜목록 클릭 이벤트
    public void clickPreferPhoto(String pUrl, int preferId) {
        this.preferId = preferId;
        this.preferUrl = pUrl;
        Picasso.get().load(preferUrl).into(imgvSelectedPref);
    }
}
