package com.example.francois.priape.api;


import android.util.Log;

import com.example.francois.priape.Model.Comment;
import com.example.francois.priape.Model.User;
import com.example.francois.priape.Model.Work;
import com.example.francois.priape.api.utils.BackendlessCollection;
import com.example.francois.priape.api.utils.BackendlessError;
import com.example.francois.priape.api.utils.BackendlessMessage;
import com.example.francois.priape.api.utils.LoginCredentials;
import com.example.francois.priape.api.utils.RegisterBody;
import com.example.francois.priape.api.utils.UserLogin;
import com.example.francois.priape.session.Singleton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
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
    public static void setCurrentUser(User user){
        currentUser = user;
    }
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
                        /*currentUser.setUserToken(userLogin.getToken());*/
                        Singleton.getInstance().setUser(currentUser);
                        token = userLogin.getToken();

                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    callback.success(currentUser);

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

    public static void getUser(final String TOKEN, String objectId, final Callback.LoginCallback callback)
    {
        String whereClause = "objectId='"+objectId+"'";
        Call<BackendlessCollection<User>> call = apiService.GetUser(0, whereClause);
        call.enqueue(new retrofit2.Callback<BackendlessCollection<User>>() {
            @Override
            public void onResponse(Call<BackendlessCollection<User>> call, retrofit2.Response<BackendlessCollection<User>> response) {
                if(response.isSuccessful())
                {
                    try {
                        List<User> list = new ArrayList<User>(response.body().getData());
                        callback.success(list.get(0));
                        currentUser = list.get(0);
                        /*currentUser.setUserToken(userLogin.getToken());*/
                        Singleton.getInstance().setUser(currentUser);
                        token = TOKEN;

                    }catch (Exception e)
                    {

                    }
                }
            }

            @Override
            public void onFailure(Call<BackendlessCollection<User>> call, Throwable t) {

            }
        });
    }


    public static void newComment(Comment comment, final Callback.NewCommentCallback callback)
    {
        Call<Comment> call = apiService.newComment(comment);
        call.enqueue(new retrofit2.Callback<Comment>(){

            @Override
            public void onResponse(Call<Comment> call, retrofit2.Response<Comment> response) {
                callback.success();
            }

            @Override
            public void onFailure(Call<Comment> call, Throwable t) {

            }
        });
    }
    public static void Register(String email, String name, String password,String picture, final Callback.RegisterCallback callback)
    {
        Call<Void> call = apiService.register(new RegisterBody(email, password, name,false, picture));
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
/*        GeoPoint location = new GeoPoint(50.524705, 4.114619);
        currentUser.setLocation(location);*/
        Work work = new Work("tondre el pelouso");
        work.setObjectId("2BB694EC-50FB-D6CE-FF8F-60E382415100");
        currentUser.removeWork();
        currentUser.addwork(work);
        Log.i("test", currentUser.getWorks().get(0).getName());
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

    public static void getComments(String professional, final Callback.GetListCallback<Comment> callback){
        String whereClause = "professional='"+professional+"'";
        Call<BackendlessCollection<Comment>> call = apiService.getComments(0, whereClause);
        call.enqueue(new retrofit2.Callback<BackendlessCollection<Comment>>(){

            @Override
            public void onResponse(Call<BackendlessCollection<Comment>> call, retrofit2.Response<BackendlessCollection<Comment>> response) {
                    if(response.isSuccessful()){
                        callback.success(response.body().getData());
                    } else{
                        BackendlessError backendlessError = BackendlessError.extractFromResponseBody(response.errorBody());
                        callback.error(backendlessError.getCode()+ "");
                    }

            }

            @Override
            public void onFailure(Call<BackendlessCollection<Comment>> call, Throwable t) {

            }
        });
    }

    public  static void GetUsers(Double lat, Double lon,String job, String km, String work, final Callback.GetListCallback<User> callback)
    {
        String whereClause = "distance( "+lat+", "+lon+", location.latitude, location.longitude ) < km("+km+")"+" AND job='"+job+"' AND professional=true AND works.name='"+work+"'";
        String sortClause = "distance( 50.484512, 4.254985, location.latitude, location.longitude )";
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
        String whereClause = "job='" + job + "'";
        Call<BackendlessCollection<Work>> call = apiService.getJob(0, whereClause);
        call.enqueue(new retrofit2.Callback<BackendlessCollection<Work>>(){


            @Override
            public void onResponse(Call<BackendlessCollection<Work>> call, retrofit2.Response<BackendlessCollection<Work>> response) {
                if (response.isSuccessful()) {
                    if (response.body().getData().size() != 0)
                        callback.success(response.body().getData());
                    else
                        callback.error(null);
                } else {
                    BackendlessError backendlessError = BackendlessError.extractFromResponseBody(response.errorBody());
                    callback.error(backendlessError.getCode() + "");
                }
            }

            @Override
            public void onFailure(Call<BackendlessCollection<Work>> call, Throwable t) {

            }
        });
    }
    public static void searchProfessional(String name, final Callback.GetListCallback<User> callback)
    {
        String whereClause = "name LIKE '"+name+"' AND professional=true";
        Call<BackendlessCollection<User>> call = apiService.GetUsers(0, whereClause);
        call.enqueue(new retrofit2.Callback<BackendlessCollection<User>>() {
            @Override
            public void onResponse(Call<BackendlessCollection<User>> call, retrofit2.Response<BackendlessCollection<User>> response) {
                if(response.isSuccessful()) {
                    callback.success(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<BackendlessCollection<User>> call, Throwable t) {

            }
        });
    }

    public static void uploadFile(File fileUri, final Callback.UploadFileCallback callback)
    {
        File  file = new File(fileUri.getPath());

        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);

        MultipartBody.Part body = MultipartBody.Part.createFormData("picture", file.getName(), requestBody);

        String descriptionString = "Hello, this is description speaking";
        RequestBody description = RequestBody.create(MediaType.parse("multipart/form-data"), descriptionString);

        Call<ResponseBody> call = apiService.upload(description, body, file.getName());
        call.enqueue(new retrofit2.Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                if (response.isSuccessful()) {

                        String body = response.body().toString();

                    try {
                        callback.success(BackendlessMessage.extractFromResponseBody(response.body().string()).getFileURL());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                } else {
                    Log.i("debug", "-> " + response.raw().toString());

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i("Backendless", "- " + t.getLocalizedMessage());
                callback.error(t.getLocalizedMessage());
            }
        });
    }

    public static void recoveryPassword(String mail,final Callback.RecoveryCallback callback){
        Call<Void> call = apiService.recoveryPassword(mail);

        call.enqueue(new retrofit2.Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, retrofit2.Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.i("Message",response.toString());
                    callback.success();

                } else {
                    BackendlessError backendLessError = BackendlessError.extractFromResponseBody(response.errorBody());
                    callback.error(backendLessError.getCode() + "");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.i("Backendless", "- " + t.getLocalizedMessage());
                callback.error(t.getLocalizedMessage());
            }
        });
    }

}
