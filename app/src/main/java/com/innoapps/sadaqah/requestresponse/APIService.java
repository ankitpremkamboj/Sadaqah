package com.innoapps.sadaqah.requestresponse;


import com.innoapps.sadaqah.screens.editprofile.model.EditProfileModel;
import com.innoapps.sadaqah.screens.login.model.LoginModel;
import com.innoapps.sadaqah.screens.navigation.model.UserDetailModel;
import com.innoapps.sadaqah.screens.signup.model.SignUpModel;
import com.innoapps.sadaqah.screens.taboption.homefragment.model.HomeModel;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by Braintech on 18-May-16.
 */
public interface APIService {

    //----------------------------------------------------------------Login------------------------------------------------------------------------------------------------------------//
    @POST("donationlist")
    @FormUrlEncoded
    Call<HomeModel> homeList(@Field("user_id") String userID);

    // Registration
    @POST("userregistration")
    @Multipart
    Call<SignUpModel> signUp(@Part("firstname") RequestBody fname, @Part("email") RequestBody email, @Part("password") RequestBody password, @Part MultipartBody.Part imageFile);

    //Login
    @POST("userlogin")
    @FormUrlEncoded
    Call<LoginModel> login(@Field("email") String email, @Field("password") String password);

    //User Profile
    @POST("getuserdetails")
    Call<LoginModel> profile(@Header("user_id") String user_id);


    //User Profile
    @POST("getuserdetails")
    @FormUrlEncoded
    Call<UserDetailModel> getUserDetails(@Field("user_id") String userID);


    // Update profile
    @POST("profileupdate")
    @Multipart
    Call<EditProfileModel> updateProfile(@Part("firstname") RequestBody fname, @Part("email") RequestBody email, @Part("password") RequestBody password, @Part MultipartBody.Part imageFile,@Part("user_id") RequestBody userID);



}
