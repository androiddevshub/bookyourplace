package com.app.bookyourplace.View.Activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.app.bookyourplace.Model.BookingDetails;
import com.app.bookyourplace.Model.ResponseBean.BookingAllResponse;
import com.app.bookyourplace.Network.ApiClient;
import com.app.bookyourplace.Network.NetworkAPI;
import com.app.bookyourplace.R;
import com.app.bookyourplace.Utils.PrefUtils;
import com.app.bookyourplace.View.Adapters.BookingListAdapter;
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

public class BookingListActivity extends AppCompatActivity {

    private RecyclerView recyclerViewShowBookings;
    private PrefUtils prefUtils;
    private LinearLayoutManager layoutManager;
    private ArrayList<BookingDetails> bookingDetailsArrayList;
    private BookingListAdapter bookingListAdapter;
    private ProgressDialog progressDialog;
    private TextView tvBookingDo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_list);

        Toast.makeText(this, "innnnn", Toast.LENGTH_SHORT).show();

        progressDialog = new ProgressDialog(getApplicationContext());
        prefUtils = new PrefUtils(getApplicationContext());
        recyclerViewShowBookings = findViewById(R.id.show_all_booking_recycler);
        recyclerViewShowBookings.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewShowBookings.setLayoutManager(layoutManager);
        bookingDetailsArrayList = new ArrayList<>();
        tvBookingDo = findViewById(R.id.tvDoBooking);

        try {
            getBookingsList();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void getBookingsList() {

        progressDialog.setMessage("Please wait until your data loads");
        progressDialog.show();
        final NetworkAPI networkAPI = ApiClient.getClient().create(NetworkAPI.class);
        HashMap<String, String> session = prefUtils.getUserDetails();
        final String session_id = session.get(PrefUtils.KEY_SESSION);

        final Call<BookingAllResponse> bookingAllResponseCall = networkAPI.getBookings("application/json", session_id);

        bookingAllResponseCall.enqueue(new Callback<BookingAllResponse>() {
            @Override
            public void onResponse(Call<BookingAllResponse> call, Response<BookingAllResponse> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    bookingDetailsArrayList = response.body().getBookingDetailsArrayList();
                    if (bookingDetailsArrayList.size() == 0) {
                        tvBookingDo.setVisibility(View.VISIBLE);
                    } else {
                        bookingListAdapter = new BookingListAdapter(bookingDetailsArrayList, getApplicationContext());
                        recyclerViewShowBookings.setAdapter(bookingListAdapter);
                    }
                } else {
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

    private void showToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

}  