package com.app.bookyourplace.View.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.app.bookyourplace.Model.Hotel;
import com.app.bookyourplace.Model.ResponseBean.HotelAllResponse;
import com.app.bookyourplace.Network.ApiClient;
import com.app.bookyourplace.Network.NetworkAPI;
import com.app.bookyourplace.R;
import com.app.bookyourplace.Utils.PrefUtils;
import com.app.bookyourplace.View.Adapters.HotelListAdapter;
import com.app.bookyourplace.View.Adapters.HotelListDashBoardAdapter;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class DashboardActivity extends AppCompatActivity {


    private RecyclerView recyclerViewShowHotels;
    private PrefUtils prefUtils;
    private LinearLayoutManager layoutManager;
    private ArrayList<Hotel> hotelArrayList;
    private HotelListDashBoardAdapter hotelListDashBoardAdapter;
    private RelativeLayout relHotels;
    private RelativeLayout relPlaces;
    private ImageView dashboardMain, dashboardPlace, dashboardHotel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home);


        prefUtils = new PrefUtils(getApplicationContext());

        relHotels = findViewById(R.id.rlexploreHotels);
        relPlaces = findViewById(R.id.rlexplorePlaces);
        recyclerViewShowHotels = findViewById(R.id.show_5_hotels_recycler);
        recyclerViewShowHotels.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewShowHotels.setLayoutManager(layoutManager);
        hotelArrayList = new ArrayList<>();

        dashboardMain = findViewById(R.id.dashBoardMainImg);
        dashboardPlace = findViewById(R.id.dashBoardMainPlaceImg);
        dashboardHotel = findViewById(R.id.dashBoardMainHotelImg);

        try {

            Picasso.with(getApplicationContext())
                    .load("https://res.cloudinary.com/shubjain/image/upload/v1554295119/project/imgageback2.jpg").fit().centerCrop()
                    .error(R.drawable.dummybg)
                    .into(dashboardMain);

        } catch (Exception e){

            e.printStackTrace();
            Picasso.with(getApplicationContext())
                    .load(R.drawable.dummybg).fit().centerCrop()
                    .into(dashboardMain);
        }

        try {

            Picasso.with(getApplicationContext())
                    .load("https://res.cloudinary.com/shubjain/image/upload/v1551402622/project/places/Chester%20Zoo/21077748_10155622886490912_2377762794705033072_n.jpg").fit().centerCrop()
                    .error(R.drawable.dummybg)
                    .into(dashboardPlace);

        } catch (Exception e){

            e.printStackTrace();
            Picasso.with(getApplicationContext())
                    .load(R.drawable.dummybg).fit().centerCrop()
                    .into(dashboardPlace);
        }

        try {

            Picasso.with(getApplicationContext())
                    .load("https://res.cloudinary.com/shubjain/image/upload/v1553280057/project/hotels/Chester%20Zoo/9322751.jpg").fit().centerCrop()
                    .error(R.drawable.dummybg)
                    .into(dashboardHotel);

        } catch (Exception e){

            e.printStackTrace();
            Picasso.with(getApplicationContext())
                    .load(R.drawable.dummybg).fit().centerCrop()
                    .into(dashboardHotel);
        }

        relPlaces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), ShowPlacesActivity.class);
                startActivity(intent);

            }
        });

        relHotels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), ShowHotelsActivity.class);
                startActivity(intent);

            }
        });

        try{
            get5HotelList();
        }catch (Exception e){
            e.printStackTrace();
        }



    }

    private void get5HotelList(){


        final NetworkAPI networkAPI = ApiClient.getClient().create(NetworkAPI.class);

        HashMap<String, String> session = prefUtils.getUserDetails();
        final String session_id = session.get(PrefUtils.KEY_SESSION);

        final Call<HotelAllResponse> hotelAllResponseCall = networkAPI.allHotels("application/json",session_id);

        hotelAllResponseCall.enqueue(new Callback<HotelAllResponse>() {
            @Override
            public void onResponse(Call<HotelAllResponse> call, Response<HotelAllResponse> response) {

                if (response.isSuccessful()){

                    hotelArrayList = response.body().getHotelArrayList();
                    hotelListDashBoardAdapter = new HotelListDashBoardAdapter(hotelArrayList, getApplicationContext());
                    recyclerViewShowHotels.setAdapter(hotelListDashBoardAdapter);

                }else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        showToast(jObjError.getString("errors"));
                        prefUtils.logoutUser();
                    } catch (Exception e) {
                        showToast(e.getMessage());
                    }
                }

            }

            @Override
            public void onFailure(Call<HotelAllResponse> call, Throwable t) {

            }
        });
    }

    private void showToast(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
