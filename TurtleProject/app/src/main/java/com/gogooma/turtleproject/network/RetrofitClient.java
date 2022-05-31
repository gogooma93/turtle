package com.gogooma.turtleproject.network;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private  static RetrofitClient instance = null;
    private static InitMyapi initMyapi;
    //서버 base 주소
    private  static String baseUrl = "http://3.36.39.226:8700";
    //private static String baseUrl = "http://192.168.0.43:8200";
    private static String authToken;

    public void RetrofitConnection(String authToken) {
        this.authToken = authToken;
    }

    private RetrofitClient() {
//        //로그 보기 위해 interceptor
//        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//        OkHttpClient client = new OkHttpClient.Builder()
//                .retryOnConnectionFailure(true)  //unexpected end on stream 때문에 추가
//                .build();

        //authToken = CustomSharedPreferences.getString(context,"access");
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //interceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(200, TimeUnit.SECONDS)
                .readTimeout(200, TimeUnit.SECONDS)
                .writeTimeout(200, TimeUnit.SECONDS)
                .addInterceptor(new Interceptor() {
                    @NonNull
                    @Override
                    public Response intercept(@NonNull Chain chain) throws IOException {
                        Request newRequest = chain.request().newBuilder()
                                .addHeader("Authorization", "Bearer "+ authToken)
                                .build();
                        return chain.proceed(newRequest);
                    }
                })
                //.addNetworkInterceptor(interceptor)
                .build();



        //Retrofit 설정
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient) //로그기능 추가 원래 client 에서 수정함
                .build();

        initMyapi = retrofit.create(InitMyapi.class);
    }


    public static RetrofitClient getInstance() {
        if (instance == null) {
            instance = new RetrofitClient();
        }
        return instance;
    }

    public static InitMyapi getRetrofitInterface() {

        return initMyapi;
    }
}

