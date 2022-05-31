package com.gogooma.turtleproject.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.database.Cursor;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;

import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.gogooma.turtleproject.BuildConfig;
import com.gogooma.turtleproject.R;
import com.gogooma.turtleproject.adapter.BodyPictureAdapter;
import com.gogooma.turtleproject.adapter.PreferPictureAdapter;
import com.gogooma.turtleproject.model.BodyPicture;
import com.gogooma.turtleproject.model.PreferPicture;
import com.gogooma.turtleproject.network.InitMyapi;
import com.gogooma.turtleproject.network.RetrofitClient;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClotheActivity extends AppCompatActivity {


    ImageButton imgbtnGoMain, imgbtnGoBack;
    ImageView imgvSelectedProfile, imgvSelectedPrefer;
    private Button btnCamera, btnGallery;
    File file;
    Uri uri;
    String path, filename;
    private RetrofitClient retrofitClient;
    private InitMyapi initMyapi;
    private RecyclerView profileRecyclerView, preferRecyclerView;
    private BodyPictureAdapter bodyPictureAdapter;
    private PreferPictureAdapter preferPictureAdapter;
    String bodyUrl, preferUrl;
    public static Context context;
    Button btnExecuteFitting, btnGuide;
    int myPhotoId, preferId;
    SwipeRefreshLayout swipeRefreshLayout;
    Bitmap bmap;
    ArrayList<BodyPicture> bodyPictureList;
    List<PreferPicture> preferPictureList;
    BodyPicture bodyPicture;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothe);

        context = this;

        swipeRefreshLayout = findViewById(R.id.layout_swiper);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                downloadProfilePicture();
                swipeRefreshLayout.setRefreshing(false);
            }
        });


        imgbtnGoMain = findViewById(R.id.btn_go_main);
        imgbtnGoMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ClotheActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


        imgbtnGoBack = findViewById(R.id.btn_go_back);
        imgbtnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //onBackPressed();
                finish();
            }
        });

        //사진촬영
        imgvSelectedProfile = findViewById(R.id.imgv_selected_profile);
        imgvSelectedPrefer = findViewById(R.id.imgv_selected_prefer);


//        imgvSelectedProfile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                takePictureShowDialog();
//            }
//        });


//        btnCamera = findViewById(R.id.btn_camera);
//        btnCamera.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent cintent = new Intent(ClotheActivity.this, CameraActivity.class);
//                startActivity(cintent);
//                finish();
//            }
//        });

        btnGallery = findViewById(R.id.btn_gallery);
        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cintent = new Intent(ClotheActivity.this, CameraActivity.class);
                startActivity(cintent);
                finish();
            }
        });

        profileRecyclerView = findViewById(R.id.recyclerv_profile);
        downloadProfilePicture();

        preferRecyclerView = findViewById(R.id.recyclerv_like_clothes);
        downloadPreferPicture();


        btnExecuteFitting = findViewById(R.id.btn_execute_fitting);
        btnExecuteFitting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                        Intent intent = new Intent(ClotheActivity.this, ComposeActivity.class);
                        intent.putExtra("myPhotoId", myPhotoId);
                        intent.putExtra("preferId", preferId);
                        startActivity(intent);
                        finish();
                        //progressDialog();
                }
            });
    }

//    //카메라촬영
//    private void takePicture() {
//        try {
//            file = createFile();
//            if (file.exists()) {
//                file.delete();
//            }
//
//            file.createNewFile();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        uri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID, file);
//
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
//
//        startActivityForResult(intent, 101);
//
//    }

//    //사진회전방지
//    public static Bitmap rotateImage(Bitmap source, float angle) {
//        Matrix matrix = new Matrix();
//        matrix.postRotate(angle);
//        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
//                matrix, true);
//    }
//
//    //갤러리연동
//    private void getGallery() {
//        Intent intent = new Intent(Intent.ACTION_PICK);
////        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
//        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        intent.setType("image/*");
//        startActivityForResult(intent, 102);
//    }
//
//    //갤러리 사진 절대경로
//    private String getRealPath(Uri uri) {
//        String path;
//        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
//        if (cursor == null) {
//            // Source is Dropbox or other similar local file path
//            path = uri.getPath();
//        } else {
//            cursor.moveToFirst();
//            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
//            path = cursor.getString(idx); cursor.close();
//        }
//        return path;
//    }
//
//    //카메라 파일 생성
//    private File createFile() {
//        String filename = "capture.jpg";
//        File outFile = new File(getFilesDir(), filename);
//        Log.d("Profile", "File path : " + outFile.getAbsolutePath());
//        this.path = outFile.getPath();
//        Log.d("Profile", "path확인: " + path);
//        this.filename = filename;
//
//        return outFile;
//    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//
//        if (requestCode == 101 && resultCode == RESULT_OK) {
//
//
//            try {
//                bmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
//                if (bmap != null) {
//                    ExifInterface ei = new ExifInterface(path);
//                    int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
//                            ExifInterface.ORIENTATION_UNDEFINED);
//
//                    Bitmap rotatedBitmap = null;
//                    switch(orientation) {
//
//                        case ExifInterface.ORIENTATION_ROTATE_90:
//                            rotatedBitmap = rotateImage(bmap, 90);
//                            break;
//
//                        case ExifInterface.ORIENTATION_ROTATE_180:
//                            rotatedBitmap = rotateImage(bmap, 180);
//                            break;
//
//                        case ExifInterface.ORIENTATION_ROTATE_270:
//                            rotatedBitmap = rotateImage(bmap, 270);
//                            break;
//
//                        case ExifInterface.ORIENTATION_NORMAL:
//                        default:
//                            rotatedBitmap = bmap;
//                    }
//
//                    imgvSelectedProfile.setImageBitmap(rotatedBitmap);
//                }
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//        if (requestCode == 102 && resultCode == RESULT_OK) {
//            try {
//                uri = data.getData();
//                Bitmap img = null;
//                img = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
//                ///imgvSelectedProfile.setImageBitmap(img);
//                this.path = getRealPath(uri);
//                this.filename = "gallery.jpg";
//
//                    if (img != null) {
//                        ExifInterface ei = new ExifInterface(path);
//                        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
//                                ExifInterface.ORIENTATION_UNDEFINED);
//
//                        Bitmap rotatedBitmap = null;
//                        switch(orientation) {
//
//                            case ExifInterface.ORIENTATION_ROTATE_90:
//                                rotatedBitmap = rotateImage(img, 90);
//                                break;
//
//                            case ExifInterface.ORIENTATION_ROTATE_180:
//                                rotatedBitmap = rotateImage(img, 180);
//                                break;
//
//                            case ExifInterface.ORIENTATION_ROTATE_270:
//                                rotatedBitmap = rotateImage(img, 270);
//                                break;
//
//                            case ExifInterface.ORIENTATION_NORMAL:
//                            default:
//                                rotatedBitmap = img;
//                        }
//
//                        imgvSelectedProfile.setImageBitmap(rotatedBitmap);
//                    }
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }


//    //촬영&갤러리 파일 전송
//    private void uploadProfilePhoto() {
//
//        File nfile = new File(path);
//
//        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), nfile);
//        MultipartBody.Part part =  MultipartBody.Part.createFormData("my_photo", filename, requestBody);
//
//
//        retrofitClient = RetrofitClient.getInstance();
//        initMyapi = RetrofitClient.getRetrofitInterface();
//        initMyapi.uploadProfilePhoto(part).enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                if (response.isSuccessful()) {
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                String message = t.getMessage();
//                Toast.makeText(ClotheActivity.this, message, Toast.LENGTH_LONG).show();
//
//            }
//        });
//
//    }



    //프로필 다운로드
    private void downloadProfilePicture() {

        retrofitClient = RetrofitClient.getInstance();
        initMyapi = RetrofitClient.getRetrofitInterface();
        initMyapi.downloadProfilePhoto().enqueue(new Callback<ArrayList<BodyPicture>>() {
            @Override
            public void onResponse(Call<ArrayList<BodyPicture>> call, Response<ArrayList<BodyPicture>> response) {
                if (response.isSuccessful()) {
                    bodyPictureList = response.body();
                    bodyPictureAdapter = new BodyPictureAdapter(ClotheActivity.this, bodyPictureList);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ClotheActivity.this, LinearLayoutManager.HORIZONTAL, false);
                    profileRecyclerView.setLayoutManager(layoutManager);
                    profileRecyclerView.setAdapter(bodyPictureAdapter);
                    bodyPictureAdapter.notifyDataSetChanged();
                    Log.d("My Fitting Photo", "Url : " + response);
                }else {
                    String message = "잠시 후 다시 시도해주세요";
                    //방금 추가
                    response.errorBody().close();
                    Toast.makeText(ClotheActivity.this, message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<BodyPicture>> call, Throwable t) {
                String message = t.getMessage();
                Toast.makeText(ClotheActivity.this, message, Toast.LENGTH_LONG).show();

            }
        });

    }

    //찜한옷 다운로드
    private void downloadPreferPicture() {

        retrofitClient = RetrofitClient.getInstance();
        initMyapi = RetrofitClient.getRetrofitInterface();
        initMyapi.downloadPreferCloth().enqueue(new Callback<List<PreferPicture>>() {
            @Override
            public void onResponse(Call<List<PreferPicture>> call, Response<List<PreferPicture>> response) {
                if (response.isSuccessful()) {
                    preferPictureList = response.body();
                    preferPictureAdapter = new PreferPictureAdapter(ClotheActivity.this, preferPictureList);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ClotheActivity.this, LinearLayoutManager.HORIZONTAL, false);
                    preferRecyclerView.setLayoutManager(layoutManager);
                    preferRecyclerView.setAdapter(preferPictureAdapter);

                }else {
                    String message = "잠시 후 다시 시도해주세요";
                    //방금 추가
                    response.errorBody().close();
                    Toast.makeText(ClotheActivity.this, message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<PreferPicture>> call, Throwable t) {
                String message = t.getMessage();
                Toast.makeText(ClotheActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }

//    //사진저장 메세지
//    void takePictureShowDialog() {
//        AlertDialog.Builder msgBuilder = new AlertDialog.Builder(ClotheActivity.this)
//                .setTitle("촬영 완료")
//                .setMessage("사진을 저장하시겠습니까?")
//                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        dialogInterface.cancel();
//                    }
//                })
//                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        uploadProfilePhoto();
////                        BodyPicture bodyPicture = new BodyPicture(filename, path);
////                        bodyPicture = new BodyPicture(filename, path);
////                        bodyPictureAdapter.addItem(bodyPicture);
////                        bodyPictureAdapter.notifyDataSetChanged();
//                        dialogInterface.cancel();
//                    }
//                });
//        msgBuilder.show();
//    }


    //프로필 클릭 이벤트
    public void clickProfilePicture(String bUrl, int myPhotoId) {
        //bUrl = CustomSharedPreferences.getString(context,"bodyUrl");
        this.myPhotoId = myPhotoId;
        this.bodyUrl = bUrl;
        Picasso.get().load(bodyUrl).into(imgvSelectedProfile);
        Log.e("profile", "id: " + myPhotoId);

    }

    //찜목록 클릭 이벤트
    public void clickPreferPicture(String pUrl, int preferId) {
        this.preferId = preferId;
        this.preferUrl = pUrl;
        Picasso.get().load(preferUrl).into(imgvSelectedPrefer);
        Log.e("profile", "id: " + preferId);
    }


}