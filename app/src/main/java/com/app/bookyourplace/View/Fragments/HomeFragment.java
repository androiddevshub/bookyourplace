package com.app.bookyourplace.View.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.app.bookyourplace.Model.Hotel;
import com.app.bookyourplace.Model.ResponseBean.HotelAllResponse;
import com.app.bookyourplace.Network.ApiClient;
import com.app.bookyourplace.Network.NetworkAPI;
import com.app.bookyourplace.R;
import com.app.bookyourplace.Utils.PrefUtils;
import com.app.bookyourplace.View.Activities.DashboardActivity;
import com.app.bookyourplace.View.Activities.ShowHotelsActivity;
import com.app.bookyourplace.View.Activities.ShowPlacesActivity;
import com.app.bookyourplace.View.Adapters.HotelListDashBoardAdapter;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerViewShowHotels;
    private PrefUtils prefUtils;
    private LinearLayoutManager layoutManager;
    private ArrayList<Hotel> hotelArrayList;
    private HotelListDashBoardAdapter hotelListDashBoardAdapter;
    private RelativeLayout relHotels;
    private RelativeLayout relPlaces;
    private ImageView dashboardMain, dashboardPlace, dashboardHotel;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        prefUtils = new PrefUtils(getContext());

        relHotels = view.findViewById(R.id.rlexploreHotels);
        relPlaces = view.findViewById(R.id.rlexplorePlaces);
        recyclerViewShowHotels = view.findViewById(R.id.show_5_hotels_recycler);
        recyclerViewShowHotels.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewShowHotels.setLayoutManager(layoutManager);
        hotelArrayList = new ArrayList<>();

        dashboardMain = view.findViewById(R.id.dashBoardMainImg);
        dashboardPlace = view.findViewById(R.id.dashBoardMainPlaceImg);
        dashboardHotel = view.findViewById(R.id.dashBoardMainHotelImg);

        try {

            Picasso.with(getContext())
                    .load("https://res.cloudinary.com/shubjain/image/upload/v1554295119/project/imgageback2.jpg").fit().centerCrop()
                    .error(R.drawable.dummybg)
                    .into(dashboardMain);

        } catch (Exception e){

            e.printStackTrace();
            Picasso.with(getContext())
                    .load(R.drawable.dummybg).fit().centerCrop()
                    .into(dashboardMain);
        }

        try {

            Picasso.with(getContext())
                    .load("https://res.cloudinary.com/shubjain/image/upload/v1551402622/project/places/Chester%20Zoo/21077748_10155622886490912_2377762794705033072_n.jpg").fit().centerCrop()
                    .error(R.drawable.dummybg)
                    .into(dashboardPlace);

        } catch (Exception e){

            e.printStackTrace();
            Picasso.with(getContext())
                    .load(R.drawable.dummybg).fit().centerCrop()
                    .into(dashboardPlace);
        }

        try {

            Picasso.with(getContext())
                    .load("https://res.cloudinary.com/shubjain/image/upload/v1553280057/project/hotels/Chester%20Zoo/9322751.jpg").fit().centerCrop()
                    .error(R.drawable.dummybg)
                    .into(dashboardHotel);

        } catch (Exception e){

            e.printStackTrace();
            Picasso.with(getContext())
                    .load(R.drawable.dummybg).fit().centerCrop()
                    .into(dashboardHotel);
        }

        relPlaces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), ShowPlacesActivity.class);
                startActivity(intent);

            }
        });

        relHotels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), ShowHotelsActivity.class);
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
                    hotelListDashBoardAdapter = new HotelListDashBoardAdapter(hotelArrayList, getContext());
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
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }


}
