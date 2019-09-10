package com.example.android.competion.Interfaces;

import com.example.android.competion.models.Example;
import com.example.android.competion.models.Example2;
import com.example.android.competion.models.Example3;
import com.example.android.competion.models.Example4;
import com.example.android.competion.models.ListDist;
import com.example.android.competion.models.User;
import com.example.android.competion.models.bestScoreVitesseList;
import com.example.android.competion.models.bestScoredistanceList;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface Api {

    String BASE_URL = "http://10.54.234.198:3001/users/"; //192.168.1.103 //10.54.234.69  // 10.54.234.69   //myip server ; fele5er 10.54.234.198
    //GetNameByEmail
    @GET("GetNameByEmail/{email}")
    public Call<JsonObject> GetNameByEmail(@Path("email") String email);



    @FormUrlEncoded
    @POST("register")
    public Call<User> Registration( @Field("name") String name,
                                    @Field("email") String email,
                                    @Field("password") String password,
                                    @Field("password2")String password2,
                                    @Field("gender") String gender,
                                    @Field("poids") String poids,
                                    @Field("birthday") String birthday,
                                    @Field("imageProfile") String imageProfile);

    @FormUrlEncoded
    @POST("login")
    public Call<JSONObject> LogUser(@Field("email") String email, @Field("password") String password) ;

    @GET("getInfoUserByEmail/{email}")
    public Call<Example2> GetUserInfoByEmail(@Path("email") String email);

    @GET("fetchGameByDateByEmail/{email}")
    public Call<Example> fetchGameByDateByEmail(@Path("email") String email);

    @GET("CalculTotalDistancesByEmail/{email}")
    public Call<JsonObject> CalculTotalDistancesByEmail(@Path("email") String email);

    @GET("CalculTotalTodayByEmail/{email}")
    public Call<Example4> CalculTotalTodayByEmail(@Path("email") String email);

    @GET("CalculTotalthisweekByEmail/{email}")
    public Call<Example4> CalculTotalthisweekByEmail(@Path("email") String email);

    @GET("CalculTotalthismonthByEmail/{email}")
    public Call<Example4> CalculTotalthismonthByEmail(@Path("email") String email);

    //method for leaderboard :
    @GET("BestScoreDistanceMobile")
    public Call<bestScoredistanceList> BestScoreDistanceMobile();

    @GET("BestScoreVitesseMobile")
    public Call<bestScoreVitesseList> BestScoreVitesseMobile ();


    @Headers("Content-Type: application/json")
    @POST("updateUserMobile/{id}")
    public Call<JSONObject> UpdateProfile(@Path("id") String _id, @QueryMap HashMap<String, String> hash ) ;



}