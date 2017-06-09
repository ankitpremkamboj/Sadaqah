package com.innoapps.sadaqah.requestresponse;



import com.innoapps.sadaqah.screens.taboption.homefragment.model.HomeModel;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by Braintech on 18-May-16.
 */
public interface APIService {

    //----------------------------------------------------------------Login------------------------------------------------------------------------------------------------------------//
    @GET("api.php")
    @FormUrlEncoded
    Call<HomeModel> login(@Field("action") String action, @Field("user_name") String username, @Field("password") String password, @Field("device_token") String token, @Field("device_key") String deviceKey);

}
