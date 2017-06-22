package com.ashokslsk.retrofitrecyclerview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.Gson;

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

public class MainActivity extends AppCompatActivity {

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

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        OkHttpClient okClient = new OkHttpClient.Builder()
                .addInterceptor(
                        new Interceptor() { // use interceptor to intercept the response
                            // from server and then add header like @Headers("Cache-Control: max-age=640000")
                            // mean that we want save the response in the cache of app with taille max 64
                            // for more visite http://stackoverflow.com/questions/31321963/how-retrofit-with-okhttp-use-cache-data-when-offline?rq=1
                            //to see all retrofit get post put visite
                            //http://square.github.io/retrofit/
                            @Override
                            public Response intercept(Interceptor.Chain chain) throws IOException {
                                Request request = chain.request().newBuilder()

                                        // we can manipulate the header of the request kima n7ebou

                                        // in this case we receive a response as a JSON
                                        //so we tell the server in the header of our request
                                        // that we send a request for a json
                                        // not a content type text/html
                                        //addHeader("Content-Type", "application/json");
                                        //"Content-Type: application/soap+xml"
                                        //retrofit can consume all type of response jakson/gson/xml/html.....
                                        .addHeader("Accept", "Application/JSON").build();
                                //we can also store our response in cache of app by using
                                // .addHeader("Cache-Control: max-age=640000")
                                // add another header by using  addHeader
                                // but just using Header will change the curent Header by the new one
                                return chain.proceed(request);
                            }
                        }).build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MyApiEndpointInterface service = retrofit.create(MyApiEndpointInterface.class);

        Call<User> call = service.getUsersNamedTom("tom");
        call.enqueue(new Callback<User>() { // run the requst Asynchrone ma3neha mch
            //3al ui ta3 el app ya3ni fi thread e5er
            @Override
            public void onResponse(Call<User> call, retrofit2.Response<User> response) {
                Log.e("MainActivity", "Status Code = " + response.code());

                if (response.isSuccessful()) {
                    // request successful (status code 200, 201)
                    Users = new ArrayList<>();
                    User result = response.body();
                    Log.e("MainActivity", "response = " + new Gson().toJson(result));
                    Users = result.getItems();
                    Log.e("MainActivity", "Items = " + Users.size());
                    Log.e("MainActivity", "Items = " + Users.get(1).getAvatar_url());


                    // This is where data loads
                    mAdapter = new UserAdapter(Users);


                    //attach to recyclerview
                    mLayoutManager = new LinearLayoutManager(getApplicationContext());
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(mAdapter);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });


    }
}
