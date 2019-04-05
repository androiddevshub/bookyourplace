package com.app.bookyourplace.View.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.app.bookyourplace.Model.ResponseBean.CommonResponse;
import com.app.bookyourplace.Network.ApiClient;
import com.app.bookyourplace.Network.NetworkAPI;
import com.app.bookyourplace.R;
import com.app.bookyourplace.Utils.PrefUtils;

import java.util.HashMap;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {


    private TextView tvUsername, tvUseremail, tvUsermobile;
    private Button btnLogout;
    private PrefUtils prefUtils;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_profile);

        prefUtils = new PrefUtils(getApplicationContext());
        tvUsername = findViewById(R.id.tvProfileUsername);
        tvUseremail = findViewById(R.id.tvProfileUseremail);
        tvUsermobile = findViewById(R.id.tvProfileUserPhone);
        btnLogout = findViewById(R.id.btnLogoutuser);

        HashMap<String, String> session = prefUtils.getUserDetails();
        final String session_id = session.get(PrefUtils.KEY_SESSION);
        final String username = session.get(PrefUtils.KEY_EMAIL);
        final String useremail = session.get(PrefUtils.KEY_NAME);
        final String usermobile = session.get(PrefUtils.KEY_PHONE);

        tvUsername.setText(username);
        tvUseremail.setText(useremail);
        tvUsermobile.setText(usermobile);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final NetworkAPI networkAPI = ApiClient.getClient().create(NetworkAPI.class);

                Call<CommonResponse> logoutUserCall = networkAPI.logoutUser("application/json", session_id);

                logoutUserCall.enqueue(new Callback<CommonResponse>() {
                    @Override
                    public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {

                        if (response.isSuccessful()){

                            Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            prefUtils.logoutUser();
                        }

                    }

                    @Override
                    public void onFailure(Call<CommonResponse> call, Throwable t) {

                    }
                });



            }
        });

    }
}
