package com.app.bookyourplace.Network;

import com.app.bookyourplace.Model.CreateBooking;
import com.app.bookyourplace.Model.Data;
import com.app.bookyourplace.Model.Data1;
import com.app.bookyourplace.Model.Price;
import com.app.bookyourplace.Model.ResponseBean.BookingAllResponse;
import com.app.bookyourplace.Model.ResponseBean.CommonResponse;
import com.app.bookyourplace.Model.ResponseBean.HotelAllResponse;
import com.app.bookyourplace.Model.ResponseBean.PlaceAllResponse;
import com.app.bookyourplace.Model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface NetworkAPI {


    @POST("users/sign_in")
    Call<CommonResponse> login(@Header("Content-Type") String type, @Body Data1 data);

    @POST("users")
    Call<CommonResponse> register(@Header("Content-Type") String type, @Body Data data);

    @POST("api/user_verify")
    Call<CommonResponse> verifyAccount(@Header("Content-Type") String type, @Body User user);

    @GET("api/user_logged_in")
    Call<CommonResponse> checkLogin(@Header("Content-Type") String type, @Header("session-id") String session_id);

    @POST("users/password")
    Call<CommonResponse>  forgotPassword(@Header("Content-Type") String type, @Body User user);

    @PUT("users/password")
    Call<CommonResponse> resetPassword(@Header("Content-Type") String type, @Body Data data);

    @GET("api/hotels")
    Call<HotelAllResponse> allHotels(@Header("Content-Type") String type, @Header("session-id") String session_id);

    @GET("api/places")
    Call<PlaceAllResponse> allPlaces(@Header("Content-Type") String type, @Header("session-id") String session_id);

    @POST("api/booking_price")
    Call<CommonResponse> filterPrice(@Header("Content-Type") String type, @Header("session-id") String session_id, @Body Price price);

    @POST("api/create_booking")
    Call<CommonResponse> createBooking(@Header("Content-Type") String type, @Header("session-id") String session_id, @Body CreateBooking createBooking);

    @GET("api/bookings")
    Call<BookingAllResponse> getBookings(@Header("Content-Type") String type, @Header("session-id") String session_id);

    @DELETE("api/booking/{id}")
    Call<CommonResponse> deleteBooking(@Header("Content-Type") String type, @Header("session-id") String session_id, @Path("id") int id);

    @DELETE("api/sign_out")
    Call<CommonResponse> logoutUser(@Header("Content-Type") String type, @Header("session-id") String session_id);

//    @PUT("users/password")
//    Call<CommonResponse> resetPassword(@Header("Content-Type") String type, @Body Data data);


}
