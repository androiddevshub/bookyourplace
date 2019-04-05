package com.app.bookyourplace.View.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;

import com.app.bookyourplace.Model.ResponseBean.CommonResponse;
import com.app.bookyourplace.Network.ApiClient;
import com.app.bookyourplace.Network.NetworkAPI;
import com.app.bookyourplace.R;
import com.app.bookyourplace.Utils.PrefUtils;

import org.json.JSONObject;

import java.util.HashMap;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {

    private PrefUtils prefUtils;
    private boolean loggedIn = true;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        prefUtils = new PrefUtils(getApplicationContext());

        HashMap<String, String> session = prefUtils.getUserDetails();
        final String session_id = session.get(PrefUtils.KEY_SESSION);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        final NetworkAPI networkAPI = ApiClient.getClient().create(NetworkAPI.class);

        Call<CommonResponse> checkLoginCall = networkAPI.checkLogin("application/json", session_id);

        checkLoginCall.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                    if (response.isSuccessful()){
                        if (response.body().getCode() == 1){

                            progressBar.setVisibility(View.GONE);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {


                                    if (prefUtils.loggedIn()){
                                        startActivity(new Intent(SplashActivity.this,MainActivity.class));
                                        finish();
                                    }else {
                                        startActivity(new Intent(SplashActivity.this,LoginActivity.class));
                                        finish();
                                    }

                                }
                            }, 2000);

                        }else {
                            prefUtils.logoutUser();
                        }
                    }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {

            }
        });
        //checklogin();

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {

                if(prefUtils.loggedIn()){
                    startActivity(new Intent(SplashActivity.this,MainActivity.class));
                    finish();
                }else {
                    startActivity(new Intent(SplashActivity.this,LoginActivity.class));
                    finish();
                }

            }
        }, 3000);
    }
}
