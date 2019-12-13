package com.example.myapplication;

import com.example.myapplication.model.RandomResponse;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface RandomAPI {

    @GET("api")
    Single<RandomResponse> getRandomUser();

}
