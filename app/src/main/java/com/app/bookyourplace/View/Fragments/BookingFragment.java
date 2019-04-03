package com.app.bookyourplace.View.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.app.bookyourplace.Model.BookingDetails;
import com.app.bookyourplace.Model.ResponseBean.BookingAllResponse;
import com.app.bookyourplace.Network.ApiClient;
import com.app.bookyourplace.Network.NetworkAPI;
import com.app.bookyourplace.R;
import com.app.bookyourplace.Utils.PrefUtils;
import com.app.bookyourplace.View.Activities.BookingListActivity;
import com.app.bookyourplace.View.Adapters.BookingListAdapter;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingFragment extends Fragment {

    private RecyclerView recyclerViewShowBookings;
    private PrefUtils prefUtils;
    private LinearLayoutManager layoutManager;
    private ArrayList<BookingDetails> bookingDetailsArrayList;
    private BookingListAdapter bookingListAdapter;
    private ProgressDialog progressDialog;
    private TextView tvBookingDo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_booking, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        progressDialog = new ProgressDialog(getContext());
        prefUtils = new PrefUtils(getContext());
        recyclerViewShowBookings = view.findViewById(R.id.show_all_booking_recycler);
        recyclerViewShowBookings.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerViewShowBookings.setLayoutManager(layoutManager);
        bookingDetailsArrayList = new ArrayList<>();
        tvBookingDo = view.findViewById(R.id.tvDoBooking);

        try{
            getBookingsList();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void getBookingsList(){

        progressDialog.setMessage("Please wait until your data loads");
        progressDialog.show();
        final NetworkAPI networkAPI = ApiClient.getClient().create(NetworkAPI.class);
        HashMap<String, String> session = prefUtils.getUserDetails();
        final String session_id = session.get(PrefUtils.KEY_SESSION);

        final Call<BookingAllResponse> bookingAllResponseCall = networkAPI.getBookings("application/json",session_id);

        bookingAllResponseCall.enqueue(new Callback<BookingAllResponse>() {
            @Override
            public void onResponse(Call<BookingAllResponse> call, Response<BookingAllResponse> response) {
                if (response.isSuccessful()){
                    progressDialog.dismiss();
                    bookingDetailsArrayList = response.body().getBookingDetailsArrayList();
                    if (bookingDetailsArrayList.size() == 0){
                        tvBookingDo.setVisibility(View.VISIBLE);
                    }else {
                        bookingListAdapter = new BookingListAdapter(bookingDetailsArrayList,getContext());
                        recyclerViewShowBookings.setAdapter(bookingListAdapter);
                    }
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
            public void onFailure(Call<BookingAllResponse> call, Throwable t) {

            }
        });
    }

    private void showToast(String msg){
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
