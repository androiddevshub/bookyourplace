package com.app.bookyourplace.View.Activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.app.bookyourplace.Model.Hotel;
import com.app.bookyourplace.Model.ResponseBean.HotelAllResponse;
import com.app.bookyourplace.Network.ApiClient;
import com.app.bookyourplace.Network.NetworkAPI;
import com.app.bookyourplace.R;
import com.app.bookyourplace.Utils.PrefUtils;
import com.app.bookyourplace.View.Adapters.HotelListAdapter;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowHotelsActivity extends AppCompatActivity {

    private Toolbar toolbarHotels;
    private RecyclerView recyclerViewShowHotels;
    private PrefUtils prefUtils;
    private LinearLayoutManager layoutManager;
    private ArrayList<Hotel> hotelArrayList;
    private HotelListAdapter hotelListAdapter;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_hotels);

        progressDialog = new ProgressDialog(this);
        toolbarHotels = findViewById(R.id.toolbarShowHotels);
        setSupportActionBar(toolbarHotels);
        toolbarHotels.setTitle("HOTELS");
        prefUtils = new PrefUtils(this);
        recyclerViewShowHotels = findViewById(R.id.show_all_hotels_recycler);
        recyclerViewShowHotels.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(ShowHotelsActivity.this);
        recyclerViewShowHotels.setLayoutManager(layoutManager);
        hotelArrayList = new ArrayList<>();

        try{
            getHotelList();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void getHotelList(){

        progressDialog.setMessage("Loading...");
        progressDialog.show();

        final NetworkAPI networkAPI = ApiClient.getClient().create(NetworkAPI.class);

        HashMap<String, String> session = prefUtils.getUserDetails();
        final String session_id = session.get(PrefUtils.KEY_SESSION);

        final Call<HotelAllResponse> hotelAllResponseCall = networkAPI.allHotels("application/json",session_id);

        hotelAllResponseCall.enqueue(new Callback<HotelAllResponse>() {
            @Override
            public void onResponse(Call<HotelAllResponse> call, Response<HotelAllResponse> response) {

                if (response.isSuccessful()){

                    progressDialog.dismiss();
                    hotelArrayList = response.body().getHotelArrayList();
                    hotelListAdapter = new HotelListAdapter(hotelArrayList,ShowHotelsActivity.this);
                    recyclerViewShowHotels.setAdapter(hotelListAdapter);

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
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
