package com.app.bookyourplace.View.Activities;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.bookyourplace.Model.BookingDetails;
import com.app.bookyourplace.Model.CreateBooking;
import com.app.bookyourplace.Model.Data1;
import com.app.bookyourplace.Model.Hotel;
import com.app.bookyourplace.Model.Login;
import com.app.bookyourplace.Model.Place;
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

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookedActivity extends AppCompatActivity implements OnMapReadyCallback {

    private CreateBooking createBooking;
    private TextView bookedTvId, bookedTvUsername, bookedTvCheckInDate, bookedTvNights, bookedTvCheckOutDate
            , bookedTvHotelName, bookedTvGuestCount, bookedTvRoomsCount, bookedTvPrice, bookedTvHotelLocation
            , bookedTvPricePayable, bookedTvUseremail;
    private RelativeLayout rlAssistance;
    private Button btnCancelBooking;
    private GoogleMap mMapHotel;
    private PrefUtils prefUtils;
    private int bookingId;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booked);

        prefUtils = new PrefUtils(this);

        getIntent().setExtrasClassLoader(CreateBooking.class.getClassLoader());
        createBooking = getIntent().getParcelableExtra("bookingData");
        bookingId = getIntent().getIntExtra("bookingId",0);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.maps_hotel);
        mapFragment.getMapAsync(this);


        bookedTvId = findViewById(R.id.bookedTvId);
        bookedTvUsername = findViewById(R.id.bookedTvUsername);
        bookedTvCheckInDate = findViewById(R.id.bookedTvCheckInDate);
        bookedTvNights = findViewById(R.id.bookedTvNights);
        bookedTvCheckOutDate = findViewById(R.id.bookedTvCheckOutDate);
        bookedTvHotelName = findViewById(R.id.bookedTvHotelName);
        bookedTvGuestCount = findViewById(R.id.bookedTvGuestCount);
        bookedTvRoomsCount = findViewById(R.id.bookedTvRoomsCount);
        bookedTvPrice = findViewById(R.id.bookedTvPrice);
        bookedTvHotelLocation = findViewById(R.id.bookedTvHotelLocation);
        rlAssistance = findViewById(R.id.rlAssistance);
        btnCancelBooking= findViewById(R.id.btnCancelBooking);
        bookedTvUseremail = findViewById(R.id.bookedTvUseremail);
        bookedTvPricePayable = findViewById(R.id.bookedTvPricePayable);

        bookedTvId.setText("Booking ID: "+ createBooking.getBookingId());
        bookedTvUsername.setText(createBooking.getName());
        bookedTvCheckInDate.setText(createBooking.getStartdate());
        bookedTvNights.setText(createBooking.getNights()+ " N");
        bookedTvCheckOutDate.setText(createBooking.getEnddate());
        bookedTvHotelName.setText(createBooking.getHotelName());
        bookedTvGuestCount.setText(String.valueOf(Integer.parseInt(createBooking.getAdults()) + Integer.parseInt(createBooking.getChlidren())));
        bookedTvRoomsCount.setText(createBooking.getRooms());
        bookedTvPrice.setText("£"+createBooking.getCprice());
        bookedTvHotelLocation.setText(createBooking.getHotelLoc());
        bookedTvPricePayable.setText("£"+ createBooking.getCprice());
        bookedTvUseremail.setText("Your booking details are sent to "+ createBooking.getEmail());

        rlAssistance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(BookedActivity.this, "Please send your query at mathiyugan1267@gmail.com", Toast.LENGTH_LONG).show();
            }
        });

        btnCancelBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HashMap<String, String> session = prefUtils.getUserDetails();
                final String session_id = session.get(PrefUtils.KEY_SESSION);


                final NetworkAPI networkAPI = ApiClient.getClient().create(NetworkAPI.class);

                Call<CommonResponse> deleteBookingCall = networkAPI.deleteBooking("application/json", session_id, bookingId);

                deleteBookingCall.enqueue(new Callback<CommonResponse>() {
                    @Override
                    public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                        if (response.isSuccessful()){
                            Toast.makeText(BookedActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(BookedActivity.this, MainActivity.class);
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

    private void showToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMapHotel = googleMap;


        try {
            Geocoder geocoder = new Geocoder(this);
            List<Address> addresses;
            addresses = geocoder.getFromLocationName(createBooking.getHotelName(), 1);
            if(addresses.size() > 0) {
                double latitude= addresses.get(0).getLatitude();
                double longitude= addresses.get(0).getLongitude();
                LatLng place = new LatLng(latitude, longitude);
                mMapHotel.addMarker(new MarkerOptions().position(place).title(createBooking.getHotelName()));
                mMapHotel.moveCamera(CameraUpdateFactory.newLatLngZoom(place, 17f));
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // Add a marker in Sydney and move the camera
//        LatLng place = new LatLng(Double.parseDouble(mPlace.getPlaceLat()), Double.parseDouble(mPlace.getPlaceLong()));
//        mMapPlace.addMarker(new MarkerOptions().position(place));
//        mMapPlace.moveCamera(CameraUpdateFactory.newLatLngZoom(place, 17f));
    }
}
