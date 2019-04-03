package com.app.bookyourplace.View.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.app.bookyourplace.Model.BookingDetails;
import com.app.bookyourplace.Model.Hotel;
import com.app.bookyourplace.Model.ResponseBean.CommonResponse;
import com.app.bookyourplace.Network.ApiClient;
import com.app.bookyourplace.Network.NetworkAPI;
import com.app.bookyourplace.R;
import com.app.bookyourplace.Utils.PrefUtils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONObject;

import java.util.HashMap;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingActivity extends AppCompatActivity implements OnMapReadyCallback {

    private BookingDetails bookingDetails;
    private TextView finalBookingId, finalBookingUsername, finalBookingCheckInDate, finalBookingNights, finalBookingCheckOutDate
            , finalBookingGuestCount, finalBookingRoomsCount, finalBookingPriceTotal, finalBookingHotelLocation, finalBookingHotelName;

    private Button finalBookingCancelBtn;
    private GoogleMap maps_bookingfinal;
    private PrefUtils prefUtils;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        getIntent().setExtrasClassLoader(Hotel.class.getClassLoader());
        bookingDetails = getIntent().getParcelableExtra("bookingDetailsMain");

        prefUtils = new PrefUtils(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.maps_bookingfinal);
        mapFragment.getMapAsync(this);


        finalBookingId = findViewById(R.id.finalBookingId);
        finalBookingUsername = findViewById(R.id.finalBookingUsername);
        finalBookingCheckInDate = findViewById(R.id.finalBookingCheckInDate);
        finalBookingNights = findViewById(R.id.finalBookingNights);
        finalBookingCheckOutDate = findViewById(R.id.finalBookingCheckOutDate);
        finalBookingGuestCount = findViewById(R.id.finalBookingGuestCount);
        finalBookingRoomsCount = findViewById(R.id.finalBookingRoomsCount);
        finalBookingPriceTotal = findViewById(R.id.finalBookingPriceTotal);
        finalBookingHotelLocation = findViewById(R.id.finalBookingHotelLocation);
        finalBookingCancelBtn = findViewById(R.id.finalBookingCancelBtn);
        finalBookingHotelName = findViewById(R.id.finalBookingHotelName);

        finalBookingId.setText("Booking ID: "+ bookingDetails.getBookingId());
        finalBookingUsername.setText("Hello! "+ bookingDetails.getBookingUsername());
        finalBookingCheckInDate.setText(bookingDetails.getBookingStartdate());
        finalBookingNights.setText(bookingDetails.getBookingNights()+ " N");
        finalBookingCheckOutDate.setText(bookingDetails.getBookingEnddate());
        finalBookingGuestCount.setText(String.valueOf(Integer.parseInt(bookingDetails.getBookingAdults()) + Integer.parseInt(bookingDetails.getBookingChildren())));
        finalBookingRoomsCount.setText(bookingDetails.getBookingRooms());
        finalBookingPriceTotal.setText(bookingDetails.getBookingCprice());
        finalBookingHotelName.setText(bookingDetails.getBookingHotelName());
        finalBookingHotelLocation.setText(bookingDetails.getBookingHotelLoc());

        finalBookingCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HashMap<String, String> session = prefUtils.getUserDetails();
                final String session_id = session.get(PrefUtils.KEY_SESSION);


                final NetworkAPI networkAPI = ApiClient.getClient().create(NetworkAPI.class);

                Call<CommonResponse> deleteBookingCall = networkAPI.deleteBooking("application/json", session_id, bookingDetails.getId());

                deleteBookingCall.enqueue(new Callback<CommonResponse>() {
                    @Override
                    public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                        if (response.isSuccessful()){
                            Toast.makeText(BookingActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(BookingActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }else {
                            try {
                                JSONObject jObjError = new JSONObject(response.errorBody().string());
                                showToast(jObjError.getString("errors"));
                            } catch (Exception e) {
                                showToast(e.getMessage());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<CommonResponse> call, Throwable t) {

                    }
                });


            }
        });

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        maps_bookingfinal = googleMap;

        LatLng booking = new LatLng(Double.parseDouble(bookingDetails.getBookingHotelLat()), Double.parseDouble(bookingDetails.getBookingHotelLong()));
        maps_bookingfinal.addMarker(new MarkerOptions().position(booking));
        maps_bookingfinal.moveCamera(CameraUpdateFactory.newLatLngZoom(booking, 17f));
    }

    private void showToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
