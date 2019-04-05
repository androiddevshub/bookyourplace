package com.app.bookyourplace.View.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class ResetPasswordActivity extends AppCompatActivity {

    private EditText etOtp, etPassword, etCPassword;
    private Button btnUpdate;
    private String email, otp, password, confirm_password;
    private PrefUtils prefUtils;
    private Intent intent;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpassword);

        prefUtils = new PrefUtils(this);
        progressDialog = new ProgressDialog(this);

        intent = getIntent();

        email = intent.getStringExtra("email");

        Log.e("ResetPasswordActivity", "email: "+ email);

        etOtp = findViewById(R.id.et_otp_resetpassword);
        etPassword = findViewById(R.id.et_reset_password);
        etCPassword = findViewById(R.id.et_reset_cpassword);

        btnUpdate = findViewById(R.id.btn_resetPassword);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                otp = etOtp.getText().toString();
                password = etPassword.getText().toString();
                confirm_password = etCPassword.getText().toString();

                if (otp.isEmpty()){
                    showToast("Please enter OTP");
                }else if (password.isEmpty()){
                    showToast("Please enter password");
                }else if (confirm_password.isEmpty()){
                    showToast("Please enter confirm password");
                }else {
                    if (prefUtils.isNetworkAvailable()){
                        resetPassword();
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

    private void resetPassword(){

        progressDialog.setMessage("Loading...");
        progressDialog.show();

        final NetworkAPI networkAPI = ApiClient.getClient().create(NetworkAPI.class);

        Call<CommonResponse> resetPasswordCall = networkAPI.resetPassword("application/json", new Data(new User(email, otp, password, confirm_password)));

        resetPasswordCall.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                if (response.isSuccessful()){
                    progressDialog.dismiss();
                    showToast(response.body().getMessage());
                    startActivity(new Intent(ResetPasswordActivity.this, LoginActivity.class));
                    finish();
                }else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        showToast(jObjError.getString("errors"));
                        progressDialog.dismiss();
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
