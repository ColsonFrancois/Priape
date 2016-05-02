package com.example.francois.priape.api;


import android.util.Log;

import com.example.francois.priape.Model.Job;
import com.example.francois.priape.Model.User;
import com.example.francois.priape.Model.Work;
import com.example.francois.priape.api.utils.BackendlessCollection;
import com.example.francois.priape.api.utils.BackendlessError;
import com.example.francois.priape.api.utils.LoginCredentials;
import com.example.francois.priape.api.utils.RegisterBody;
import com.example.francois.priape.api.utils.UserLogin;
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
    private static String token;
    private static User currentUser;
    public static void init() {

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();

        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {


                        Request.Builder request = chain.request().newBuilder()
                                .addHeader("application-id", Default.APPLICATION_ID)
                                .addHeader("secret-key", Default.SECRET_KEY)
                                .addHeader("Content-Type", "application/json")
                                .addHeader("application-type", "REST");
                        if (getCurrentUser() != null) {
                            Log.i("LogRequest", token);
                            request.addHeader("user-token", token);
                        }
                        Log.i("LogRequest", getCurrentUser() + "");
                        return chain.proceed(request.build());
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
    }

    public static User getCurrentUser(){return currentUser;}

    public static void login(String username, String password, final Callback.LoginCallback callback) {
        Call<User> loginCall = apiService.login(new LoginCredentials(username, password));
       loginCall.enqueue(new retrofit2.Callback<User>() {
            @Override
            public void onResponse(Call<User> call, retrofit2.Response<User> response) {

                if (response.isSuccessful()) {
                    try{
                        String bodyJson = new Gson().toJson(response.body()).toString();
                        UserLogin userLogin = new UserLogin(bodyJson);
                        currentUser = userLogin.getUser();
                        token = userLogin.getToken();
                        Log.i("TOKEN", token);

                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    callback.success();

                }
                else{
                    BackendlessError backendlessError = BackendlessError.extractFromResponseBody(response.errorBody());
                    callback.error(backendlessError.getCode() + "");
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.i("Backendless", "- " + t.getLocalizedMessage());
                callback.error(t.getLocalizedMessage().toString());
            }
        });
    }

    public static void Register(String email, String name, String password, final Callback.RegisterCallback callback)
    {
        Call<Void> call = apiService.register(new RegisterBody(email, password, name, false));
        call.enqueue(new retrofit2.Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, retrofit2.Response<Void> response) {
                if(response.isSuccessful()){
                    callback.success();
                }else{
                    BackendlessError backendlessError = BackendlessError.extractFromResponseBody(response.errorBody());
                   callback.error(backendlessError.getCode() + "");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                callback.error(t.getLocalizedMessage());
            }
        });

    }
    public static void Logout(final Callback.LougoutCallback callback)
    {
        Call<Void> call = apiService.logout();
        call.enqueue(new retrofit2.Callback<Void>(){

            @Override
            public void onResponse(Call<Void> call, retrofit2.Response<Void> response) {
                if(response.isSuccessful()){
                    callback.success();
                }else{
                    BackendlessError backendlessError = BackendlessError.extractFromResponseBody(response.errorBody());
                    callback.error(backendlessError.getCode() + "");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                    callback.error(t.getLocalizedMessage());
            }
        });
    }



    public static void updateUser()
    {
        Work work = new Work("Tondre pelouse");
        Job job = new Job("Jardinier", null);
        job.addWork(work);
        currentUser.setJob(job);
        Call<User> call = apiService.updateUser(currentUser.getObjectId(), currentUser);
        call.enqueue(new retrofit2.Callback<User>(){

            @Override
            public void onResponse(Call<User> call, retrofit2.Response<User> response) {
                if(response.isSuccessful()){
                    Log.i("Response", response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }


    public  static void GetUsers(String job, String work, final Callback.GetListCallback<User> callback)
    {
       String whereClause = "job.name='"+job+"' AND professional=true AND works.name='"+work+"'";

        Call<BackendlessCollection<User>> call = apiService.GetUsers(0, whereClause);
        call.enqueue(new retrofit2.Callback<BackendlessCollection<User>>(){

            public void onResponse(Call<BackendlessCollection<User>> call, retrofit2.Response<BackendlessCollection<User>> response)
            {
               if(response.isSuccessful()){
                   callback.success(response.body().getData());
               }else{
                   BackendlessError backendlessError = BackendlessError.extractFromResponseBody(response.errorBody());
                   callback.error(backendlessError.getCode()+ "");
               }
            }
            @Override
            public void onFailure(Call<BackendlessCollection<User>> call, Throwable t) {
            }

        });
    }

    public static void getJob(String job, final Callback.JobCallback callback)
    {
        String whereClause = "name='" + job + "'";
        Call<BackendlessCollection<Job>> call = apiService.getJob(0, whereClause);
        call.enqueue(new retrofit2.Callback<BackendlessCollection<Job>>(){

            @Override
            public void onResponse(Call<BackendlessCollection<Job>> call, retrofit2.Response<BackendlessCollection<Job>> response) {
                if (response.isSuccessful()) {
                    if (response.body().getData().size() != 0)
                        callback.success(response.body().getData().get(0));
                    else
                        callback.error(null);
                } else {
                    BackendlessError backendlessError = BackendlessError.extractFromResponseBody(response.errorBody());
                    callback.error(backendlessError.getCode() + "");
                }
            }
            @Override
            public void onFailure(Call<BackendlessCollection<Job>> call, Throwable t) {

            }
        });
    }


}
