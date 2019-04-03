package com.app.bookyourplace.View.Activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.app.bookyourplace.Model.Hotel;
import com.app.bookyourplace.Model.Place;
import com.app.bookyourplace.Model.ResponseBean.HotelAllResponse;
import com.app.bookyourplace.Model.ResponseBean.PlaceAllResponse;
import com.app.bookyourplace.Network.ApiClient;
import com.app.bookyourplace.Network.NetworkAPI;
import com.app.bookyourplace.R;
import com.app.bookyourplace.Utils.PrefUtils;
import com.app.bookyourplace.View.Adapters.HotelListAdapter;
import com.app.bookyourplace.View.Adapters.PlaceListAdapter;

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

public class ShowPlacesActivity extends AppCompatActivity {

    private Toolbar toolbarPlaces;
    private RecyclerView recyclerViewShowPlaces;
    private PrefUtils prefUtils;
    private LinearLayoutManager layoutManager;
    private ArrayList<Place> placeArrayList;
    private PlaceListAdapter placeListAdapter;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_places);

        progressDialog = new ProgressDialog(this);
        toolbarPlaces = findViewById(R.id.toolbarShowPlaces);
        setSupportActionBar(toolbarPlaces);
        toolbarPlaces.setTitle("HISTORICAL PLACES");
        prefUtils = new PrefUtils(this);
        recyclerViewShowPlaces = findViewById(R.id.show_all_places_recycler);
        recyclerViewShowPlaces.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(ShowPlacesActivity.this);
        recyclerViewShowPlaces.setLayoutManager(layoutManager);
        placeArrayList = new ArrayList<>();

        try{
            getPlaceList();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void getPlaceList(){

        progressDialog.setMessage("Loading...");
        progressDialog.show();

        final NetworkAPI networkAPI = ApiClient.getClient().create(NetworkAPI.class);

        HashMap<String, String> session = prefUtils.getUserDetails();
        final String session_id = session.get(PrefUtils.KEY_SESSION);

        final Call<PlaceAllResponse> placeAllResponseCall = networkAPI.allPlaces("application/json",session_id);

        placeAllResponseCall.enqueue(new Callback<PlaceAllResponse>() {
            @Override
            public void onResponse(Call<PlaceAllResponse> call, Response<PlaceAllResponse> response) {

                if (response.isSuccessful()){

                    progressDialog.dismiss();
                    placeArrayList = response.body().getHotelArrayList();
                    placeListAdapter = new PlaceListAdapter(placeArrayList,ShowPlacesActivity.this);
                    recyclerViewShowPlaces.setAdapter(placeListAdapter);

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
            public void onFailure(Call<PlaceAllResponse> call, Throwable t) {

            }
        });

    }

    private void showToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
