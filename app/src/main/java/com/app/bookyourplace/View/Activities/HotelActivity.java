package com.app.bookyourplace.View.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.bookyourplace.Model.CreateBooking;
import com.app.bookyourplace.Model.Data1;
import com.app.bookyourplace.Model.Hotel;
import com.app.bookyourplace.Model.Login;
import com.app.bookyourplace.Model.Price;
import com.app.bookyourplace.Model.ResponseBean.CommonResponse;
import com.app.bookyourplace.Network.ApiClient;
import com.app.bookyourplace.Network.NetworkAPI;
import com.app.bookyourplace.R;
import com.app.bookyourplace.Utils.PrefUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HotelActivity extends AppCompatActivity{

    private Hotel hotel;
    private TextView hotelName;
    private TextView hotelLocation;
    private TextView hotelRating;
    private ImageView hotelImageMain, icon_in_room, icon_dec_room, icon_in_adults, icon_dec_adults,
            icon_in_children, icon_dec_children;
    private TextView tvCheckInDate, tvCheckoutDate, tvStayNights;
    private LinearLayout checkInLayout, checkOutLayout;
    private Calendar checkInCal, checkOutCal;
    private int checkinDate, checkinMonth, checkinYear, Days = 0;
    private int day1, day2, month1, month2, year1, year2;
    private String checkInDayFinal, checkOutDayFinal;
    private PrefUtils prefUtils;
    private String countRoomsStr, countAdultsStr, countChildrenStr, calculatedPrice;
    private int countRooms, countAdults, countChildren;
    private TextView tvcountRooms, tvcountAdults, tvcountChildren, tvtotalCountRAC, tvTotalPrice, tvBookingFor;
    private Button bookingBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel);

        prefUtils = new PrefUtils(this);
        getIntent().setExtrasClassLoader(Hotel.class.getClassLoader());
        hotel = getIntent().getParcelableExtra("hotelData");
        hotelImageMain = findViewById(R.id.imgHotelMain);
        hotelName = findViewById(R.id.tvAHotelName);
        hotelLocation = findViewById(R.id.tvAHotelLocation);
        hotelRating = findViewById(R.id.tvAHotelRating);
        checkInLayout = findViewById(R.id.layoutCheckInDate);
        checkOutLayout = findViewById(R.id.layoutCheckOutDate);
        tvCheckInDate = findViewById(R.id.checkInDateTv);
        tvCheckoutDate = findViewById(R.id.checkOutDateTv);
        tvStayNights = findViewById(R.id.stayNightsTv);
        icon_in_room = findViewById(R.id.icon_increment_room);
        icon_dec_room = findViewById(R.id.icon_decrement_room);
        icon_in_adults = findViewById(R.id.icon_increment_adult);
        icon_dec_adults = findViewById(R.id.icon_decrement_adult);
        icon_in_children = findViewById(R.id.icon_increment_child);
        icon_dec_children = findViewById(R.id.icon_decrement_child);
        tvcountRooms = findViewById(R.id.count_room);
        tvcountAdults = findViewById(R.id.count_adult);
        tvcountChildren = findViewById(R.id.count_child);
        tvtotalCountRAC = findViewById(R.id.totalCountRAC);
        tvTotalPrice = findViewById(R.id.totalPrice);
        tvBookingFor = findViewById(R.id.bookingForTv);
        bookingBtn = findViewById(R.id.btnBooking);

        checkInCal = Calendar.getInstance();
        checkinDate = checkInCal.get(Calendar.DAY_OF_MONTH);
        checkinMonth = checkInCal.get(Calendar.MONTH);
        checkinYear = checkInCal.get(Calendar.YEAR);
        checkinMonth = checkinMonth + 1;
        String todayDate = checkinDate+"/"+checkinMonth+"/"+checkinYear;

        day2 = day2 +1;

        HashMap<String, String> session = prefUtils.getUserDetails();
        final String nameuser = session.get(PrefUtils.KEY_NAME);


        try {

            Picasso.with(this)
                    .load(hotel.getHotelImagesH().getImg5()).fit().centerCrop()
                    .error(R.drawable.dummybg)
                    .into(hotelImageMain);

        } catch (Exception e){

            e.printStackTrace();
            Picasso.with(this)
                    .load(R.drawable.dummybg).fit().centerCrop()
                    .into(hotelImageMain);
        }

        hotelName.setText(hotel.getHotelName());
        hotelLocation.setText(hotel.getHotelLocation());
        hotelRating.setText(hotel.getHotelRating());
        tvTotalPrice.setText("£"+hotel.getHotelPrice());
        tvBookingFor.setText(nameuser);


        checkInLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog checkInDatePicker = new DatePickerDialog(HotelActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        year1 = year;
                        month1 = month;
                        day1 = dayOfMonth;


                        month = month + 1;

                        String inDate = dayOfMonth+"/"+month+"/"+year;

                        try {
                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", java.util.Locale.ENGLISH);
                            Date myDate = sdf.parse(inDate);
                            sdf.applyPattern("EEE, d MMM");
                            checkInDayFinal = sdf.format(myDate);
                            tvCheckInDate.setText(checkInDayFinal);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                },year1, month1, day1);
                checkInDatePicker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                checkInDatePicker.show();


            }
        });

        checkOutLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog checkOutDatePicker = new DatePickerDialog(HotelActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        year2 = year;
                        month2 = month;
                        day2 = dayOfMonth;

                        month = month + 1;

                        String outDate = dayOfMonth+"/"+month+"/"+year;
                        try {
                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", java.util.Locale.ENGLISH);
                            Date myDate = sdf.parse(outDate);
                            sdf.applyPattern("EEE, d MMM");
                            checkOutDayFinal = sdf.format(myDate);
                            tvCheckoutDate.setText(checkOutDayFinal);
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                        showDiff();
                    }
                },year2, month2, day2);
                checkOutDatePicker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

                checkOutDatePicker.show();

                showDiff();

            }
        });

        icon_in_room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (tvcountAdults.getText().toString() == "1"){
                   showToast("Please increase the number of adults");
               }else {
                   icon_in_adults.setEnabled(true);
                   countRoomsStr = tvcountRooms.getText().toString();
                   countRooms = Integer.parseInt(countRoomsStr);
                   countRooms = countRooms + 1;
                   tvcountRooms.setText(String.valueOf(countRooms));
//                Toast.makeText(HotelActivity.this, "clicked", Toast.LENGTH_SHORT).show();
                   showCountRAC();
                   calculatePrice();
               }
            }
        });

        icon_dec_room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countRoomsStr = tvcountRooms.getText().toString();
                countRooms = Integer.parseInt(countRoomsStr);
                countRooms = countRooms - 1;
                if (countRooms == 0){
                    tvcountRooms.setText(String.valueOf(1));
                }else {
                    tvcountRooms.setText(String.valueOf(countRooms));
                }
//                Toast.makeText(HotelActivity.this, "clicked", Toast.LENGTH_SHORT).show();
                showCountRAC();
                calculatePrice();
            }
        });


        icon_in_adults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                icon_in_room.setEnabled(true);
                countAdultsStr = tvcountAdults.getText().toString();
                countAdults = Integer.parseInt(countAdultsStr);
                countAdults = countAdults + 1;
                tvcountAdults.setText(String.valueOf(countAdults));
//                Toast.makeText(HotelActivity.this, "clicked", Toast.LENGTH_SHORT).show();
                showCountRAC();
                calculatePrice();
            }
        });

        icon_dec_adults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                countAdultsStr = tvcountAdults.getText().toString();
                countAdults = Integer.parseInt(countAdultsStr);
                countAdults = countAdults - 1;
                if (countAdults == 0){
                    tvcountAdults.setText(String.valueOf(1));
                }else {
                    tvcountAdults.setText(String.valueOf(countAdults));
                }
//                Toast.makeText(HotelActivity.this, "clicked", Toast.LENGTH_SHORT).show();
                showCountRAC();
                calculatePrice();

            }
        });

        icon_in_children.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                countChildrenStr = tvcountChildren.getText().toString();
                countChildren = Integer.parseInt(countChildrenStr);
                countChildren = countChildren + 1;
                tvcountChildren.setText(String.valueOf(countChildren));
//                Toast.makeText(HotelActivity.this, "clicked", Toast.LENGTH_SHORT).show();
                showCountRAC();
                calculatePrice();

            }
        });

        icon_dec_children.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                countChildrenStr = tvcountChildren.getText().toString();
                countChildren = Integer.parseInt(countChildrenStr);
                countChildren = countChildren - 1;
                if (countChildren == 0){
                    tvcountChildren.setText(String.valueOf(0));
                }else {
                    tvcountChildren.setText(String.valueOf(countChildren));
                }
//                Toast.makeText(HotelActivity.this, "clicked", Toast.LENGTH_SHORT).show();
                showCountRAC();
                calculatePrice();
            }
        });


        bookingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doBooking();
            }
        });

    }



    private void showDiff(){

        if(month2 == month1){
            if(day2>day1){
                Days = day2 - day1;
            }
        }
        else if(month2>month1){
            if(day2>day1){
                int m = month2-month1;
                Days = m*30 + (day2 - day1);
            }
        }
        else {
//            Toast.makeText(this, "Check-In and Check-Out dates can't be same", Toast.LENGTH_LONG).show();
        }
        if (Days == 0){
            Toast.makeText(this, "Please select valid Check-Out date", Toast.LENGTH_LONG).show();
        }
        else if (Days == 1){
            tvStayNights.setText(String.valueOf(Days) + " NIGHT");
        }else {
            tvStayNights.setText(String.valueOf(Days) + " NIGHTS");
        }
        calculatePrice();
    }

    private void showCountRAC(){

        countRoomsStr = tvcountRooms.getText().toString();
        countAdultsStr = tvcountAdults.getText().toString();
        countChildrenStr = tvcountChildren.getText().toString();

        if(countRoomsStr == "1" && countAdultsStr == "1" && countChildrenStr == "0"){
            tvtotalCountRAC.setText(countRoomsStr + " Room, "+ countAdultsStr+ "Adult," + countChildrenStr + " Child");
        }
        else if (countRoomsStr == "1" && countAdultsStr == "1" && countChildrenStr == "1"){
            tvtotalCountRAC.setText(countRoomsStr + " Room,"+ countAdultsStr+ " Adult," + countChildrenStr + " Child");
        }else {
            tvtotalCountRAC.setText(countRoomsStr + " Rooms,"+ countAdultsStr+ " Adults," + countChildrenStr + " Children");
        }
    }

    private void calculatePrice(){

        final NetworkAPI networkAPI = ApiClient.getClient().create(NetworkAPI.class);

        HashMap<String, String> session = prefUtils.getUserDetails();
        final String session_id = session.get(PrefUtils.KEY_SESSION);

        countRoomsStr = tvcountRooms.getText().toString();
        countAdultsStr = tvcountAdults.getText().toString();
        countChildrenStr = tvcountChildren.getText().toString();

        Log.e("Details", "Rooms:" + countRoomsStr+ " Adults: " + countAdultsStr+ " Children: " + countChildrenStr);

        Call<CommonResponse> filterPriceResponseCall = networkAPI.filterPrice("application/json",
                session_id, new Price(Days, Integer.parseInt(countRoomsStr)
                        ,Integer.parseInt(countAdultsStr),Integer.parseInt(countChildrenStr)
                        ,hotel.getId()));

        filterPriceResponseCall.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {

                String regex = "\\d+";

                if (response.isSuccessful()){
                    if (response.body().getCode() == 1){
                        icon_in_adults.setEnabled(false);
                        Toast.makeText(HotelActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    }else if(response.body().getCode() == 2){
                        icon_in_room.setEnabled(false);
                        Toast.makeText(HotelActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    }
                    else if (response.body().getCode() == 0){
                        calculatedPrice = response.body().getMessage();
                        tvTotalPrice.setText("£"+ calculatedPrice);
                    }

                }

            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {

            }
        });
    }

    private void doBooking(){

        HashMap<String, String> session = prefUtils.getUserDetails();
        final String session_id = session.get(PrefUtils.KEY_SESSION);
        final String useremail = session.get(PrefUtils.KEY_EMAIL);
        final String username = session.get(PrefUtils.KEY_NAME);

        String bookingId = "BYPA"+((int)(Math.random()*9000)+1000);

        String guests = String.valueOf(Integer.parseInt(countAdultsStr) + Integer.parseInt(countChildrenStr));

        final NetworkAPI networkAPI = ApiClient.getClient().create(NetworkAPI.class);

        countRoomsStr = tvcountRooms.getText().toString();
        countAdultsStr = tvcountAdults.getText().toString();
        countChildrenStr = tvcountChildren.getText().toString();

        Log.e("Details", "Rooms:" + countRoomsStr+ " Adults: " + countAdultsStr+ " Children: " + countChildrenStr);

        CreateBooking createBooking = new CreateBooking(String.valueOf(hotel.getId()),
                bookingId,
                checkInDayFinal,
                checkOutDayFinal,
                String.valueOf(Days),
                calculatedPrice,
                countAdultsStr,
                countChildrenStr,
                countRoomsStr,
                hotel.getHotelName(),
                hotel.getHotelLocation(),
                hotel.getHotelLat(),
                hotel.getHotelLong(),
                hotel.getHotelImagesH().getImg3(),
                useremail,
                username);

        Call<CommonResponse> bookingCreateResponseCall = networkAPI.createBooking("application/json", session_id, createBooking);

        bookingCreateResponseCall.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {

                if (response.isSuccessful()){
                    Toast.makeText(HotelActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(HotelActivity.this, BookedActivity.class);
//                    intent.putExtra("bookingId", bookingId);
//                    intent.putExtra("bookingUserName", username);
//                    intent.putExtra("bookingUserEmail", useremail);
//                    intent.putExtra("bookingCheckInDate", checkInDayFinal);
//                    intent.putExtra("bookingCheckOutDate", checkOutDayFinal);
//                    intent.putExtra("bookingNights", String.valueOf(Days));
//                    intent.putExtra("bookingRooms", countRoomsStr);
//                    intent.putExtra("bookingGuests", guests);
//                    intent.putExtra("bookingPrice", calculatedPrice);
//                    intent.putExtra("bookingHotelName", calculatedPrice);
//                    intent.putExtra("bookingHotelLocation", calculatedPrice);
//                    intent.putExtra("bookingHotelLat", calculatedPrice);
//                    intent.putExtra("bookingHotelLong", calculatedPrice);
//                    startActivity(intent);
//                    finish();
                    Intent intent = new Intent(HotelActivity.this, BookedActivity.class);
                    intent.putExtra("bookingData",createBooking);
                    intent.putExtra("bookingId",response.body().getId());
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
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

    private void showToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}
