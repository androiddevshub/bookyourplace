package com.app.bookyourplace.View.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.app.bookyourplace.Model.ResponseBean.CommonResponse;
import com.app.bookyourplace.Network.ApiClient;
import com.app.bookyourplace.Network.NetworkAPI;
import com.app.bookyourplace.R;
import com.app.bookyourplace.Utils.PrefUtils;

import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {

    private TextView tvUsername, tvUseremail, tvUsermobile;
    private Button btnLogout;
    private PrefUtils prefUtils;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        prefUtils = new PrefUtils(getContext());
        tvUsername = view.findViewById(R.id.tvProfileUsername);
        tvUseremail = view.findViewById(R.id.tvProfileUseremail);
        tvUsermobile = view.findViewById(R.id.tvProfileUserPhone);
        btnLogout = view.findViewById(R.id.btnLogoutuser);

        HashMap<String, String> session = prefUtils.getUserDetails();
        final String session_id = session.get(PrefUtils.KEY_SESSION);
        final String username = session.get(PrefUtils.KEY_NAME);
        final String useremail = session.get(PrefUtils.KEY_EMAIL);
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

                            Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
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
