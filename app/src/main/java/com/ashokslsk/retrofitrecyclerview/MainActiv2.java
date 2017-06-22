package com.ashokslsk.retrofitrecyclerview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Ahmed Attia on 18/05/2017.
 */

public class MainActiv2 extends AppCompatActivity {

    public static final String BASE_URL = "https://api.github.com/";
    private static final String TAG = "MainActivity";

    private RecyclerView recyclerView;
    private UserAdapter mAdapter;
    List<User.ItemsEntity> Users;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //"Accept", "Application/JSON"




       OkHttpClient okHttpClient = new OkHttpClient.Builder()
               .addInterceptor(new Interceptor() {
                   @Override
                   public Response intercept(Chain chain) throws IOException {
                       Request request = chain.request().newBuilder()
                               .addHeader("Accept", "Application/JSON").build();
                   return chain.proceed(request);}
               }).build();


        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();

        MyApiEndpointInterface service = retrofit.create(MyApiEndpointInterface.class);
         Call<User> call = service.getUsersNamedTom("ahmed");

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, retrofit2.Response<User> response) {
                User result=response.body();
                List<User> uesers= new ArrayList<User>();
                Log.e("zzzzzzzzzzzzzz", result.getItems().get(1).getAvatar_url());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });









    }
}
