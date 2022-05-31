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
import android.net.VpnService;
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

import com.gogooma.turtleproject.BuildConfig;
import com.gogooma.turtleproject.R;
import com.gogooma.turtleproject.network.InitMyapi;
import com.gogooma.turtleproject.network.RetrofitClient;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CameraActivity extends AppCompatActivity {

    private Button buttonGuide, buttonAlbum, buttonSave;
    private ImageView imagevProfile;
    private static Context context;
    ImageButton imgbtnGoMain, imgbtnGoBack;
    File file;
    Uri uri;
    String path, filename;
    Bitmap bmap;
    private RetrofitClient retrofitClient;
    private InitMyapi initMyapi;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        context = this;

        buttonAlbum = findViewById(R.id.button_album);
        buttonGuide = findViewById(R.id.button_camera);
        buttonSave = findViewById(R.id.button_save);
        imagevProfile = findViewById(R.id.imageView_profile);

        imgbtnGoMain = findViewById(R.id.btn_go_main);
        imgbtnGoMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CameraActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


        imgbtnGoBack = findViewById(R.id.btn_go_back);
        imgbtnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //onBackPressed();
                Intent nintent = new Intent(CameraActivity.this, ClotheActivity.class);
                startActivity(nintent);
                finish();
            }
        });

        buttonGuide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), GuideActivity.class);
                startActivity(intent);
            }
        });

        buttonAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getGallery();

            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePictureShowDialog();
            }
        });

    }

    //카메라촬영
    private void takePicture() {
        try {
            file = createFile();
            if (file.exists()) {
                file.delete();
            }

            file.createNewFile();

        } catch (Exception e) {
            e.printStackTrace();
        }
        uri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID, file);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);

        startActivityForResult(intent, 101);

    }

    //카메라 파일 생성
    private File createFile() {
        String filename = "capture.jpg";
        File outFile = new File(getFilesDir(), filename);
        Log.d("Profile", "File path : " + outFile.getAbsolutePath());
        this.path = outFile.getPath();
        Log.d("Profile", "path확인: " + path);
        this.filename = filename;

        return outFile;
    }


    //사진회전방지
    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == 101 && resultCode == RESULT_OK) {


            try {
                bmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                if (bmap != null) {
                    ExifInterface ei = new ExifInterface(path);
                    int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                            ExifInterface.ORIENTATION_UNDEFINED);

                    Bitmap rotatedBitmap = null;
                    switch(orientation) {

                        case ExifInterface.ORIENTATION_ROTATE_90:
                            rotatedBitmap = rotateImage(bmap, 90);
                            break;

                        case ExifInterface.ORIENTATION_ROTATE_180:
                            rotatedBitmap = rotateImage(bmap, 180);
                            break;

                        case ExifInterface.ORIENTATION_ROTATE_270:
                            rotatedBitmap = rotateImage(bmap, 270);
                            break;

                        case ExifInterface.ORIENTATION_NORMAL:
                        default:
                            rotatedBitmap = bmap;
                    }

                    imagevProfile.setImageBitmap(rotatedBitmap);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (requestCode == 102 && resultCode == RESULT_OK) {
            try {
                uri = data.getData();
                Bitmap img = null;
                img = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                this.path = getRealPath(uri);
                this.filename = "gallery.jpg";

                if (img != null) {
                    ExifInterface ei = new ExifInterface(path);
                    int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                            ExifInterface.ORIENTATION_UNDEFINED);

                    Bitmap rotatedBitmap = null;
                    switch(orientation) {

                        case ExifInterface.ORIENTATION_ROTATE_90:
                            rotatedBitmap = rotateImage(img, 90);
                            break;

                        case ExifInterface.ORIENTATION_ROTATE_180:
                            rotatedBitmap = rotateImage(img, 180);
                            break;

                        case ExifInterface.ORIENTATION_ROTATE_270:
                            rotatedBitmap = rotateImage(img, 270);
                            break;

                        case ExifInterface.ORIENTATION_NORMAL:
                        default:
                            rotatedBitmap = img;
                    }

                    imagevProfile.setImageBitmap(rotatedBitmap);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //갤러리연동
    private void getGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
//        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, 102);
    }

    //갤러리 사진 절대경로
    private String getRealPath(Uri uri) {
        String path;
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        if (cursor == null) {
            // Source is Dropbox or other similar local file path
            path = uri.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            path = cursor.getString(idx); cursor.close();
        }
        return path;
    }

    //사진저장 메세지
    void takePictureShowDialog() {
        AlertDialog.Builder msgBuilder = new AlertDialog.Builder(CameraActivity.this)
                .setTitle("촬영 완료")
                .setMessage("사진을 저장하시겠습니까?")
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                })
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        uploadProfilePhoto();
//                        BodyPicture bodyPicture = new BodyPicture(filename, path);
//                        bodyPicture = new BodyPicture(filename, path);
//                        bodyPictureAdapter.addItem(bodyPicture);
//                        bodyPictureAdapter.notifyDataSetChanged();
                        dialogInterface.cancel();
                    }
                });
        msgBuilder.show();
    }

    //촬영&갤러리 파일 전송
    private void uploadProfilePhoto() {

        File nfile = new File(path);

        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), nfile);
        MultipartBody.Part part =  MultipartBody.Part.createFormData("my_photo", filename, requestBody);


        retrofitClient = RetrofitClient.getInstance();
        initMyapi = RetrofitClient.getRetrofitInterface();
        initMyapi.uploadProfilePhoto(part).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                String message = t.getMessage();
                Toast.makeText(CameraActivity.this, message, Toast.LENGTH_LONG).show();

            }
        });

    }

}
