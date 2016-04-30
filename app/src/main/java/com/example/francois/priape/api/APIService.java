package com.example.francois.priape.api;

import com.example.francois.priape.Model.User;
import com.example.francois.priape.api.utils.BackendlessCollection;
import com.example.francois.priape.api.utils.LoginCredentials;
import com.example.francois.priape.api.utils.RegisterBody;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Francois on 03/04/2016.
 */
public interface APIService {
    @POST("users/login")
    Call<ResponseBody> login(@Body LoginCredentials loginApi);

    @GET("data/users")
    Call<BackendlessCollection<User>>GetUsers(@Query("offset") int offset, @Query("where") String where);

    @POST("users/register")
    Call<Void> register(@Body RegisterBody registerBody);

}
