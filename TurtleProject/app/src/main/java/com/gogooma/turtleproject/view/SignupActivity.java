package com.gogooma.turtleproject.view;

import android.app.Activity;
import android.content.AsyncQueryHandler;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.AsyncTaskLoader;

import com.gogooma.turtleproject.R;
import com.gogooma.turtleproject.model.LoginResponse;
import com.gogooma.turtleproject.model.SignupResponse;
import com.gogooma.turtleproject.network.CustomSharedPreferences;
import com.gogooma.turtleproject.network.InitMyapi;
import com.gogooma.turtleproject.network.RetrofitClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {


    ImageButton btnGoBack;
    Button btnSignup, btnPwCheck;
    EditText etSignupId, etSignupPw1, etSignupPw2, etSignupName;
    String username, email, password1, password2;
    private RetrofitClient retrofitClient;
    private InitMyapi initMyapi;
    LinearLayout signupLayout;
    private Context mContext;
    registDB rdb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //가입항목
        etSignupId = findViewById(R.id.et_signup_id);
        etSignupPw1 = findViewById(R.id.et_signup_pw1);
        etSignupPw2 = findViewById(R.id.et_signup_pw2);
        etSignupName = findViewById(R.id.et_signup_name);

        signupLayout = findViewById(R.id.signup_parent);

        signupLayout.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                hideKeyboard();
                return false;
            }
        });


        btnGoBack = findViewById(R.id.btn_go_back);
        btnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnPwCheck = findViewById(R.id.btn_pw_check);
        btnPwCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etSignupPw1.getText().toString().equals(etSignupPw2.getText().toString())) {
                    btnPwCheck.setText("일치합니다");
                    etSignupPw2.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));
                } else {
                    btnPwCheck.setText("비밀번호가 다릅니다");
                    etSignupPw2.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                }
            }
        });

        btnSignup = findViewById(R.id.btn_signup);
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = etSignupName.getText().toString();
                email = etSignupId.getText().toString();
                password1 = etSignupPw1.getText().toString();
                password2 = etSignupPw2.getText().toString();

                rdb = new registDB();
                rdb.execute();


                //Signup();
            }
        });
    }

//    public void Signup() {
//
//        username = etSignupName.getText().toString();
//        email = etSignupId.getText().toString();
//        password1 = etSignupPw1.getText().toString();
//        password2 = etSignupPw2.getText().toString();
//
//        retrofitClient = RetrofitClient.getInstance();
//        initMyapi = RetrofitClient.getRetrofitInterface();
//
//        initMyapi.getSignupResponse(username, email, password1, password2).enqueue(new Callback<SignupResponse>() {
//            @Override
//            public void onResponse(Call<SignupResponse> call, Response<SignupResponse> response) {
//                Log.d("Retrofit", "Data fetch SUCCESS");
//                System.out.println(response);
//
//
//                if (response.isSuccessful()) {
////                    SignupResponse result = response.body();;
////                    String username = result.getNewUser().getUsername();
////                    Log.e("회원가입 성공 ", "USERNAME : " + username);
//                    signupShowDialog();
//
//                } else {
//                        Log.e("회원가입 실패", "FAIL" );
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<SignupResponse> call, Throwable t) {
//                Log.e("연결실패", "오류");
//            }
//        });
//
//    }

    void signupShowDialog() {
        username = etSignupName.getText().toString();
        email = etSignupId.getText().toString();
        password1 = etSignupPw1.getText().toString();

        AlertDialog.Builder msgBuilder = new AlertDialog.Builder(SignupActivity.this)
                .setTitle(username +"님")
                .setMessage("회원가입이 완료되었습니다!")
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Toast.makeText(SignupActivity.this, "로그인화면으로 이동합니다", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
        msgBuilder.show();
    }

    void hideKeyboard()
    {
        InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    //AsyncTask 사용
    class registDB extends AsyncTask<Void, Integer, String> {
        @Override
        protected String doInBackground(Void... unused) {

            /* 인풋 파라메터값 생성 */
            String param = "email=" + email + "&password1=" + password1 + "&password2=" + password2 + "&username=" + username + "";
            String response = null;
            String success = null; //main클래스에 선언하고 onPostExecute가 끝나면 무언가 로그인창으로 넘어간다던지 하게끔 코딩
            try {
                /* 서버연결 */
                URL url = new URL(
                        "http://3.36.39.226:8700/accounts/register/");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.connect();

                /* 안드로이드 -> 서버 파라메터값 전달 */
                OutputStream outs = conn.getOutputStream();
                outs.write(param.getBytes("UTF-8"));
                Log.e("param", "전송: " + param);
                outs.flush();
                outs.close();

                /* 서버 -> 안드로이드 파라메터값 전달 */
                String res;
                InputStream is = null;
                ByteArrayOutputStream baos = null;

                int responseCode = conn.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    is = conn.getInputStream();
                    baos = new ByteArrayOutputStream();
                    byte[] byteBuffer = new byte[1024];
                    byte[] byteData = null;
                    int nLength = 0;
                    while ((nLength = is.read(byteBuffer, 0, byteBuffer.length)) != -1) {
                        baos.write(byteBuffer, 0, nLength);
                    }

                    byteData = baos.toByteArray();
                    res = new String(byteData);
                    JSONObject responseJSON = new JSONObject(res);
                    success = (String) responseJSON.get("success");

                    Log.i("RECV DATA", "DATA response = " + success);
                }


            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return success;
        }
        protected void onPostExecute(String result) {
            //Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_LONG).show();
            signupShowDialog();
        }

    }
}
