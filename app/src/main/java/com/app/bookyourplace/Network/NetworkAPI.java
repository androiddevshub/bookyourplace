package com.app.bookyourplace.Network;

import com.app.bookyourplace.Model.Data;
import com.app.bookyourplace.Model.Data1;
import com.app.bookyourplace.Model.ResponseBean.CommonResponse;
import com.app.bookyourplace.Model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface NetworkAPI {


    @POST("users/sign_in")
    Call<CommonResponse> login(@Header("Content-Type") String type, @Body Data1 data);

    @POST("users")
    Call<CommonResponse> register(@Header("Content-Type") String type, @Body Data data);

    @POST("api/user_verify")
    Call<CommonResponse> verifyAccount(@Header("Content-Type") String type, @Body User user);

    @POST("users/password")
    Call<CommonResponse>  forgotPassword(@Header("Content-Type") String type, @Body User user);

    @PUT("users/password")
    Call<CommonResponse> resetPassword(@Header("Content-Type") String type, @Body Data data);


//    @PUT("users/password")
//    Call<CommonResponse> resetPassword(@Header("Content-Type") String type, @Body Data data);


}
