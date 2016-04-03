package com.example.francois.priape.api;

import com.example.francois.priape.api.utils.LoginCredentials;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Francois on 03/04/2016.
 */
public interface APIService {
    @POST("users/login")
    Call<ResponseBody> login(@Body LoginCredentials loginApi);

}
