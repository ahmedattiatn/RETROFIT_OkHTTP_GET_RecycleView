package com.ashokslsk.retrofitrecyclerview;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by ashok.kumar on 17/06/16.
 */
public interface MyApiEndpointInterface {

    @GET("/search/users")
    Call<User> getUsersNamedTom(@Query("q") String name);
     //qUERY TO ADD ? FOR THE SERACH  LIKE THIS
   // https://www.SITE.com/search/users?q=tom
    //@post to post new user
    //@put to update a user

}
