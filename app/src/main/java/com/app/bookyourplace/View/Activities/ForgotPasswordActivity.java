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

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText etEmailFP;
    private Button btnSend;
    private String email;
    private PrefUtils prefUtils;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);
        progressDialog = new ProgressDialog(this);

        prefUtils = new PrefUtils(this);
        etEmailFP = findViewById(R.id.et_email_forgotP);
        btnSend = findViewById(R.id.btn_sendForgotPOTP);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = etEmailFP.getText().toString();

                if (email.isEmpty()){
                    showToast("Please enter your email");
                }else {
                    if (prefUtils.isNetworkAvailable()){
                        sendOTP();
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

    private void sendOTP(){

        progressDialog.setMessage("Loading...");
        progressDialog.show();
        final NetworkAPI networkAPI = ApiClient.getClient().create(NetworkAPI.class);

        Call<CommonResponse> forgotPasswordCall = networkAPI.forgotPassword("application/json", new User(email));

        forgotPasswordCall.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                if (response.isSuccessful()){
                    progressDialog.dismiss();
                    showToast(response.body().getMessage());
                    Intent intent = new Intent(ForgotPasswordActivity.this, ResetPasswordActivity.class);
                    intent.putExtra("email", email);
                    startActivity(intent);
                }else{
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
