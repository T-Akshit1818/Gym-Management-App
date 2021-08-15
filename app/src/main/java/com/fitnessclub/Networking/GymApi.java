package com.fitnessclub.Networking;

import com.fitnessclub.Model.MessagePojo;
import com.fitnessclub.Model.ProfilePojo;
import com.fitnessclub.Model.SuccessOrFailureResponse;
import com.fitnessclub.Model.TrainersPojo;
import com.fitnessclub.Model.UsersPojo;
import com.fitnessclub.Model.WotoutPojo;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

public interface GymApi {


    @GET("/fitnessclub/user_registration.php?")
    Call<SuccessOrFailureResponse> register(@Query("fullname") String fullname,
                                            @Query("email") String email,
                                            @Query("pass") String pass,
                                            @Query("phonenumber") String phonenumber,
                                            @Query("gender") String gender
    );


    @GET("/fitnessclub/user_login.php?")
    Call<SuccessOrFailureResponse> login(@Query("email") String email, @Query("pass") String password);


    @GET("/fitnessclub/user_profile.php?")
    Call<List<ProfilePojo>> profile(@Query("email") String email);

    @GET("/fitnessclub/updateprofile.php?")
    Call<SuccessOrFailureResponse> updateprofile(@Query("fullname") String fullname,
                                                 @Query("email") String email,
                                                 @Query("pass") String pass,
                                                 @Query("phonenumber") String phonenumber);


    @GET("/fitnessclub/trainer_registration.php?")
    Call<SuccessOrFailureResponse> trainerregister(@Query("fullname") String fullname,
                                                   @Query("email") String email,
                                                   @Query("pass") String pass,
                                                   @Query("phone") String phone,
                                                   @Query("exp") String exp);


    @GET("/fitnessclub/trainer_update.php?")
    Call<SuccessOrFailureResponse> trainer_update(@Query("fullname") String fullname,
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
    Call<List<TrainersPojo>> gettrainerslist();

    @GET("/fitnessclub/getmytraininglist.php")
    Call<List<WotoutPojo>> getmytraininglist(@Query("email") String email);

    @GET("/fitnessclub/gettraining.php")
    Call<List<WotoutPojo>> gettraining();

    @Multipart
    @POST("/fitnessclub/addtraining.php")
    Call<SuccessOrFailureResponse> addtraining(
            @Part MultipartBody.Part file,
            @PartMap Map<String, String> partMap
    );


    @GET("/fitnessclub/deletetrainer.php")
    Call<SuccessOrFailureResponse> deletetrainer(@Query("id") String id);

    @GET("/fitnessclub/deleteuser.php")
    Call<SuccessOrFailureResponse> deleteuser(@Query("id") String id);

    @GET("/fitnessclub/deletetrainingcontent.php")
    Call<SuccessOrFailureResponse> deletetrainingcontent(@Query("id") String id);


    @GET("/fitnessclub/edittrainingcontent.php?")
    Call<SuccessOrFailureResponse> edittrainingcontent(@Query("type") String type,
                                                       @Query("level") String level,
                                                       @Query("tim") String tim,
                                                       @Query("about") String about,
                                                       @Query("gender") String gender,
                                                       @Query("tid") String tid
    );

    @GET("/fitnessclub/booktrainer.php?")
    Call<SuccessOrFailureResponse> booktrainer(@Query("tid") String tid,
                                               @Query("dat") String dat,
                                               @Query("message") String message,
                                               @Query("name") String name,
                                               @Query("email") String email
    );

    @GET("/fitnessclub/usermybookings.php?")
    Call<List<BookingsPojo>> usermybookings(@Query("email") String email);

    @GET("/fitnessclub/trainermybookings.php?")
    Call<List<BookingsPojo>> trainermybookings(@Query("email") String email);

    @GET("/fitnessclub/confirmbooking.php")
    Call<SuccessOrFailureResponse> confirmbooking(@Query("id") String id);

    @GET("/fitnessclub/getUserChat.php?")
    Call<List<MessagePojo>> getUserChat(@Query("frm") String from,
                                        @Query("eto") String to);

    @GET("/fitnessclub/sendMessage.php?")
    Call<SuccessOrFailureResponse> sendMessage(@Query("frm") String frm,
                                               @Query("eto") String eto,
                                               @Query("msg") String msg);

    @GET("/fitnessclub/trainerchat.php?")
    Call<List<MessagePojo>> trainerchat(@Query("email") String email);

    @GET("/fitnessclub/in.php?")
    Call<SuccessOrFailureResponse> in(@Query("email") String email,
                                      @Query("dat") String dat,
                                      @Query("in") String in);

    @GET("/fitnessclub/out.php?")
    Call<SuccessOrFailureResponse> out(@Query("email") String email,
                                       @Query("dat") String dat,
                                       @Query("in") String in,
                                       @Query("out") String out
    );

}
