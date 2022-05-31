package com.gogooma.turtleproject.view;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.gogooma.turtleproject.R;
import com.gogooma.turtleproject.adapter.BodyPageAdapter;
import com.gogooma.turtleproject.adapter.FittingPageAdapter;
import com.gogooma.turtleproject.adapter.PreferPageAdapter;
import com.gogooma.turtleproject.model.BodyPicture;
import com.gogooma.turtleproject.model.Clothe;
import com.gogooma.turtleproject.model.PreferPicture;
import com.gogooma.turtleproject.network.InitMyapi;
import com.gogooma.turtleproject.network.RetrofitClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClosetActivity extends AppCompatActivity {

    private long pressedTime;
    ImageButton imgbtnGoMain, imgbtnGoBack;
    private View viewBody, viewLike, viewFitting;
    private TabLayout tabGallery;
    private RecyclerView recyclervBody, recyclerPrefer, recyclerFitting;
    private RetrofitClient retrofitClient;
    private InitMyapi initMyapi;
    private PreferPageAdapter preferPageAdapter;
    private BodyPageAdapter bodyPageAdapter;
    private FittingPageAdapter fittingPageAdapter;
    BottomNavigationView bottomNavigationView;
    public static Context pcontext;
    List<PreferPicture> preferPictureList;
    List<Clothe> clotheList;
    ArrayList<BodyPicture> bodyPictureList;

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
        setContentView(R.layout.activity_closet);

        pcontext = this;

        downloadBodyPage();


        imgbtnGoMain = findViewById(R.id.btn_go_main);
        imgbtnGoMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ClosetActivity.this, MainActivity.class);
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

        viewBody = findViewById(R.id.page_body);
        viewLike = findViewById(R.id.page_like);
        viewFitting = findViewById(R.id.page_fitting);
        recyclervBody = findViewById(R.id.recyclerv_body);
        recyclerPrefer = findViewById(R.id.recyclerv_like);
        recyclerFitting = findViewById(R.id.recyclerv_fitting);

        //탭 뷰
        tabGallery = findViewById(R.id.tab_gallery);
        tabGallery.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                    int index = tab.getPosition();
                    changeGalleryView(index);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }

        });

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.menu4);
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu2:
                        startActivity(new Intent(getApplicationContext(), ShopActivity.class));
                        overridePendingTransition(0, 0);
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
    private void changeGalleryView(int index) {
        switch (index) {
            case 0:
                viewBody.setVisibility(View.VISIBLE);
                viewLike.setVisibility(View.INVISIBLE);
                viewFitting.setVisibility(View.INVISIBLE);
                //downloadBodyPage();
                break;
            case 1:
                viewBody.setVisibility(View.INVISIBLE);
                viewLike.setVisibility(View.VISIBLE);
                viewFitting.setVisibility(View.INVISIBLE);
                downloadPreferPage();

                break;
            case 2:
                viewBody.setVisibility(View.INVISIBLE);
                viewLike.setVisibility(View.INVISIBLE);
                viewFitting.setVisibility(View.VISIBLE);
                downloadFittingPage();
                break;
        }
    }

    private void downloadBodyPage() {
        retrofitClient = RetrofitClient.getInstance();
        initMyapi = RetrofitClient.getRetrofitInterface();
        initMyapi.downloadProfilePhoto().enqueue(new Callback<ArrayList<BodyPicture>>() {
            @Override
            public void onResponse(Call<ArrayList<BodyPicture>> call, Response<ArrayList<BodyPicture>> response) {
                if (response.isSuccessful()) {
                    bodyPictureList = response.body();
                    bodyPageAdapter = new BodyPageAdapter(ClosetActivity.this, bodyPictureList);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ClosetActivity.this);
                    recyclervBody.setLayoutManager(layoutManager);
                    recyclervBody.setAdapter(bodyPageAdapter);
                }else {
                    String message = "잠시 후 다시 시도해주세요";
                    //방금 추가
                    response.errorBody().close();
                    Toast.makeText(ClosetActivity.this, message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<BodyPicture>> call, Throwable t) {

            }
        });
    }

    private void downloadPreferPage() {

        retrofitClient = RetrofitClient.getInstance();
        initMyapi = RetrofitClient.getRetrofitInterface();
        initMyapi.downloadPreferCloth().enqueue(new Callback<List<PreferPicture>>() {
            @Override
            public void onResponse(Call<List<PreferPicture>> call, Response<List<PreferPicture>> response) {
                if (response.isSuccessful()) {
                    preferPictureList = response.body();
                    preferPageAdapter = new PreferPageAdapter(ClosetActivity.this, preferPictureList);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ClosetActivity.this);
                    recyclerPrefer.setLayoutManager(layoutManager);
                    recyclerPrefer.setAdapter(preferPageAdapter);
                }else {
                    String message = "잠시 후 다시 시도해주세요";
                    //방금 추가
                    response.errorBody().close();
                    Toast.makeText(ClosetActivity.this, message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<PreferPicture>> call, Throwable t) {
                String message = t.getMessage();
                Toast.makeText(ClosetActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void downloadFittingPage() {

        retrofitClient = RetrofitClient.getInstance();
        initMyapi = RetrofitClient.getRetrofitInterface();
        initMyapi.downloadFittingList().enqueue(new Callback<List<Clothe>>() {
            @Override
            public void onResponse(Call<List<Clothe>> call, Response<List<Clothe>> response) {
                if (response.isSuccessful()) {
                    clotheList = response.body();
                    fittingPageAdapter = new FittingPageAdapter(ClosetActivity.this, clotheList);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ClosetActivity.this);
                    recyclerFitting.setLayoutManager(layoutManager);
                    recyclerFitting.setAdapter(fittingPageAdapter);
                }else {
                    String message = "잠시 후 다시 시도해주세요";
                    //방금 추가
                    response.errorBody().close();
                    Toast.makeText(ClosetActivity.this, message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Clothe>> call, Throwable t) {
                String message = t.getMessage();
                Toast.makeText(ClosetActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void clickDeleteClothButton(int deleteId, int pos) {
        retrofitClient = RetrofitClient.getInstance();
        initMyapi = RetrofitClient.getRetrofitInterface();
        initMyapi.deletePreferCloth(deleteId).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                    preferPictureList.remove(pos);
                    preferPageAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

                String message = t.getMessage();
                Toast.makeText(ClosetActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });

    }

    public void clickDeleteProfileButton(int delProfileId, int pos) {
        retrofitClient = RetrofitClient.getInstance();
        initMyapi = RetrofitClient.getRetrofitInterface();
        initMyapi.deleteProfilePhoto(delProfileId).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                bodyPictureList.remove(pos);
                bodyPageAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                String message = t.getMessage();
                Toast.makeText(ClosetActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });

    }

    //사진 삭제 메세지
    public void deleteProfileShowDialog(int delProfileId, int pos) {
        AlertDialog.Builder msgBuilder = new AlertDialog.Builder(ClosetActivity.this)
                .setTitle("프로필")
                .setMessage("사진을 삭제하시겠습니까?")
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                })
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        clickDeleteProfileButton(delProfileId, pos);

                        dialogInterface.cancel();
//                        try {
//                            //TODO 액티비티 화면 재갱신 시키는 코드
//                            Intent intent = getIntent();
//                            finish(); //현재 액티비티 종료 실시
//                            overridePendingTransition(0, 0); //인텐트 애니메이션 없애기
//                            startActivity(intent); //현재 액티비티 재실행 실시
//                            overridePendingTransition(0, 0); //인텐트 애니메이션 없애기
//
//                        }
//                        catch (Exception e){
//                            e.printStackTrace();
//                        }
                    }
                });
        msgBuilder.show();
    }

    //찜한옷 삭제 메세지
    public void deletePrefShowDialog(int deleteId, int pos) {
        AlertDialog.Builder msgBuilder = new AlertDialog.Builder(ClosetActivity.this)
                .setTitle("찜한 옷")
                .setMessage("상품을 삭제하시겠습니까?")
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                })
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        clickDeleteClothButton(deleteId,pos);

                        dialogInterface.cancel();
//                        try {
//                            //TODO 액티비티 화면 재갱신 시키는 코드
//                            Intent intent = getIntent();
//                            finish(); //현재 액티비티 종료 실시
//                            overridePendingTransition(0, 0); //인텐트 애니메이션 없애기
//                            startActivity(intent); //현재 액티비티 재실행 실시
//                            overridePendingTransition(0, 0); //인텐트 애니메이션 없애기
//                        }
//                        catch (Exception e){
//                            e.printStackTrace();
//                        }
                    }
                });
        msgBuilder.show();
    }

    public void clickBtnFittingClothe() {
        Intent intent = new Intent(ClosetActivity.this, ClotheActivity.class);
        startActivity(intent);
        finish();
    }

    public void clickBtnFittingColor() {
        Intent intent = new Intent(ClosetActivity.this, ChangeColorActivity.class);
        startActivity(intent);
        finish();
    }
}
