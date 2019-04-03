package com.app.bookyourplace.View.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app.bookyourplace.Model.Data;
import com.app.bookyourplace.Model.ResponseBean.CommonResponse;
import com.app.bookyourplace.Model.User;
import com.app.bookyourplace.Network.ApiClient;
import com.app.bookyourplace.Network.NetworkAPI;
import com.app.bookyourplace.R;
import com.app.bookyourplace.Utils.PrefUtils;

import org.json.JSONObject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifyAccountActivity extends AppCompatActivity {

    private EditText etOtp;
    private Button btnVerify;
    private String otp, email;
    private PrefUtils prefUtils;
    private Intent intent;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);

        progressDialog = new ProgressDialog(this);

        intent = getIntent();
        email = intent.getStringExtra("email");

        prefUtils = new PrefUtils(this);

        etOtp = findViewById(R.id.et_otp_verifyA);
        btnVerify = findViewById(R.id.btn_verifyA);

        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otp = etOtp.getText().toString();

                if (otp.isEmpty()){
                    showToast("Please enter OTP");
                }else {
                    if (prefUtils.isNetworkAvailable()){
                        verifyOTP();
                    }else {
                        showToast("Please check your internet connection");
                    }
                }
            }
        });
    }

    private void showToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void verifyOTP(){

        progressDialog.setMessage("Loading...");
        progressDialog.show();

        final NetworkAPI networkAPI = ApiClient.getClient().create(NetworkAPI.class);

        Call<CommonResponse> verifyAccountCall = networkAPI.verifyAccount("application/json", new User(email, otp));

        verifyAccountCall.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {

                if (response.isSuccessful()){
                    progressDialog.dismiss();
                    showToast(response.body().getMessage());
                    startActivity(new Intent(VerifyAccountActivity.this, LoginActivity.class));
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
}
