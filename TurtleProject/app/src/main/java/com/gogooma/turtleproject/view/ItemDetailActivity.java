package com.gogooma.turtleproject.view;

import static android.content.ContentValues.TAG;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gogooma.turtleproject.R;
import com.gogooma.turtleproject.adapter.OtherColorAdapter;
import com.gogooma.turtleproject.model.ShopItem;
import com.gogooma.turtleproject.network.InitMyapi;
import com.gogooma.turtleproject.network.RetrofitClient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemDetailActivity extends AppCompatActivity {

    private ImageView imgvCloth;
    private TextView tvClothName, tvClothPrice, tvClothDesc, tvClothColor, tvClothColorNum, tvClothSerialNum;
    private Button btnLike, btnFitting;
    String clothImgUrl, clothName, clothDesc, clothColor, clothColorNum, clothSerialNum;
    int clothPrice;
    ImageButton imgbtnGoMain, imgbtnGoBack;
    private RetrofitClient retrofitClient;
    private InitMyapi initMyapi;
    String serialNum, colorNum;
    ArrayList<ShopItem> sameModelList = new ArrayList<>();
    ArrayList<ShopItem> shopItemList;
    private RecyclerView recyclervOtherColor;
    private OtherColorAdapter otherColorAdapter;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();

        this.clothImgUrl = intent.getExtras().getString("clothImgUrl");
        this.clothName = intent.getExtras().getString("clothName");
        this.clothPrice = intent.getExtras().getInt("clothPrice");
        this.clothDesc = intent.getExtras().getString("clothDesc");
        this.clothColor = intent.getExtras().getString("clothColor");
        this.clothColorNum = intent.getExtras().getString("clothColorNum");
        this.clothSerialNum = intent.getExtras().getString("clothSerialNum");
        this.shopItemList = intent.getExtras().getParcelableArrayList("shopItemList");

        imgbtnGoMain = findViewById(R.id.btn_go_main);
        imgbtnGoMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ItemDetailActivity.this, MainActivity.class);
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

        btnLike = findViewById(R.id.btn_likelist);
        btnFitting = findViewById(R.id.btn_fittingroom);
        imgvCloth = findViewById(R.id.imgv_cloth);
        tvClothName = findViewById(R.id.tv_clothname);
        tvClothPrice = findViewById(R.id.tv_clothprice);
        tvClothDesc = findViewById(R.id.tv_clothdesc);
        tvClothColor = findViewById(R.id.tv_clothcolor);
        tvClothColorNum = findViewById(R.id.tv_clothcolornum);
        tvClothSerialNum = findViewById(R.id.tv_clothserialnum);

        Picasso.get().load(clothImgUrl).into(imgvCloth);
        tvClothName.setText(clothName);
        tvClothPrice.setText(String.valueOf(clothPrice) + "원");
        tvClothDesc.setText(clothDesc);
        tvClothColor.setText(clothColor + " | ");
        tvClothColorNum.setText(clothColorNum);
        tvClothSerialNum.setText(clothSerialNum);

        //같은옷 다른색상
        getSameModelList(clothSerialNum, clothColorNum);
        otherColorAdapter = new OtherColorAdapter(ItemDetailActivity.this, sameModelList);
        recyclervOtherColor = findViewById(R.id.recyclerv_other_color);
        recyclervOtherColor.setLayoutManager(new LinearLayoutManager(ItemDetailActivity.this,LinearLayoutManager.HORIZONTAL,false));
        recyclervOtherColor.setAdapter(otherColorAdapter);


        btnFitting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ItemDetailActivity.this, ClotheActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadLikeListShowDialog();
            }
        });
    }

    public void uploadLikeList() {

        serialNum = tvClothSerialNum.getText().toString();
        colorNum = tvClothColorNum.getText().toString();

        retrofitClient = RetrofitClient.getInstance();
        initMyapi = RetrofitClient.getRetrofitInterface();
        initMyapi.uploadPreferCloth(serialNum, colorNum).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG,  "요청실패" + t);

            }
        });
    }

    void uploadLikeListShowDialog() {
        AlertDialog.Builder msgBuilder = new AlertDialog.Builder(ItemDetailActivity.this)
                .setTitle("찜하기")
                .setMessage("이 상품을 찜하시겠습니까?")
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                })
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        uploadLikeList();
                        //Toast.makeText(ItemDetailActivity.this, "찜 목록에 저장되었습니다.", Toast.LENGTH_LONG).show();
                        dialogInterface.cancel();
                    }
                });
        msgBuilder.show();
    }

    public ArrayList<ShopItem> getSameModelList(String clothSerialNum, String clothColorNum) {
        sameModelList.clear();
        for(ShopItem item: shopItemList) {
            if(item.getSerialNum().equals(clothSerialNum)) {
                if(!item.getColorNum().equals(clothColorNum)) {
                    sameModelList.add(item);
                }
            }
        }

        return sameModelList;
    }
}
