package com.fitnessclub.Networking;

import com.fitnessclub.Model.ProfilePojo;
import com.fitnessclub.Model.SuccessOrFailureResponse;
import com.fitnessclub.Model.UsersPojo;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface GymApi {


    @GET("/fitnessclub/user_registration.php?")
    Call<SuccessOrFailureResponse> register(@Query("fullname") String fullname,
                                            @Query("email") String email,
                                            @Query("pass") String pass,
                                            @Query("phonenumber") String phonenumber,
                                            @Query("gender") String gender,
                                            @Query("type") String type);


    @GET("/fitnessclub/user_login.php?")
    Call<SuccessOrFailureResponse> login(@Query("email") String email, @Query("pass") String password);


    @GET("/fitnessclub/user_profile.php?")
    Call<List<ProfilePojo>> profile(@Query("email") String email);


    @GET("/fitnessclub/trainer_registration.php?")
    Call<SuccessOrFailureResponse> trainerregister(@Query("fullname") String fullname,
                                            @Query("email") String email,
                                            @Query("pass") String pass,
                                            @Query("phone") String phone,
                                            @Query("exp") String exp);


    @GET("/fitnessclub/trainer_login.php?")
    Call<SuccessOrFailureResponse> trainerlogin(@Query("email") String email, @Query("pass") String password);




    @GET("/fitnessclub/trainer_profile.php?")
    Call<List<ProfilePojo>> trainerprofile(@Query("email") String email);


    @GET("/fitnessclub/getuserslist.php")
    Call<List<UsersPojo>> getuserslist();

    @GET("/fitnessclub/gettrainerslist.php")
    Call<List<UsersPojo>> gettrainerslist();
}
