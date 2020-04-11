package com.taskk.demo.interfaces;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {
    String BASE_URL = "https://api.themoviedb.org/3/movie/";

    String apiKey = "6beecd1a224a8d74d2a7a72b6621ef6b";

    @GET("top_rated")
    Call<ResponseBody> getTopRated(@Query("api_key") String apikey, @Query("page") int page );

    @GET("upcoming")
    Call<ResponseBody> getUpcoming(@Query("api_key") String apikey, @Query("page") int page );


}
