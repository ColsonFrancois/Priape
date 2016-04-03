package com.example.francois.priape.api;


import android.util.Log;

import com.example.francois.priape.api.utils.LoginCredentials;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Francois on 13/03/2016.
 */
public class API {

    private  static  APIService apiService;
    private static SimpleDateFormat dateFormatter= new SimpleDateFormat("dd/MM/yyyy HH:mm");

    public static void init() {

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();

        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {

                        if (chain.request().method().equals("DELETE")) {

                            Request.Builder request = chain.request().newBuilder()
                                    .addHeader("application-id", Default.APPLICATION_ID)
                                    .addHeader("secret-key", Default.SECRET_KEY)
                                    .addHeader("application-type", "REST");

                            return chain.proceed(request.build());
                        } else {

                            Request.Builder request = chain.request().newBuilder()
                                    .addHeader("application-id", Default.APPLICATION_ID)
                                    .addHeader("secret-key", Default.SECRET_KEY)
                                    .addHeader("Content-Type", "application/json")
                                    .addHeader("application-type", "REST");

                            return chain.proceed(request.build());
                        }
                    }
                }).build();

        GsonBuilder builder = new GsonBuilder();

        builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
            @Override
            public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                return new Date(json.getAsJsonPrimitive().getAsLong());
            }
        });

        Gson gson = builder.setDateFormat(dateFormatter.toPattern()).create();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Default.SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient)
                .build();

        apiService = retrofit.create(APIService.class);
        Log.i("test", gson.toString());
    }


    public static void login(String username, String password, final Callback.LoginCallback callback) {

        Call<ResponseBody> loginCall = apiService.login(new LoginCredentials(username, password));
        loginCall.enqueue(new retrofit2.Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {

                if (response.isSuccessful()) {

                   /*
                    treatments = response.body().getTreatments();
                    Log.i("LogUser",response.toString());
                    Log.i("LogUser",response.raw().message());*/

                    callback.success();

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i("Backendless", "- " + t.getLocalizedMessage());
                callback.error(t.getLocalizedMessage().toString());
            }
        });
    }


}
