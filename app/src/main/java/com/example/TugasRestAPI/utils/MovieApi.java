package com.example.TugasRestAPI.utils;

import com.example.TugasRestAPI.response.PopularMovieResponses;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieApi {
    // popular movie
    @GET("movie/popular")
    Call<PopularMovieResponses> getPopularMovie(@Query("api_key") String key,
                                                @Query("page") int page);
}
