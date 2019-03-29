package com.app.bookyourplace.View.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.bookyourplace.Model.Data;
import com.app.bookyourplace.Model.Data1;
import com.app.bookyourplace.Model.Login;
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

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnLogin;
    private String email, password;
    private PrefUtils prefUtils;
    private TextView tvForgotPassword, tvRegisterHere;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.et_email_login);
        etPassword = findViewById(R.id.et_password_login);
        btnLogin = findViewById(R.id.btn_signin);
        tvForgotPassword = findViewById(R.id.tv_forgotPassword);
        tvRegisterHere = findViewById(R.id.tv_registerHere);

        prefUtils = new PrefUtils(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = etEmail.getText().toString();
                password = etPassword.getText().toString();

                if (email.isEmpty()){
                    showToast("Please enter your email");
                }else if (password.isEmpty()){
                    showToast("Please enter your password");
                }else {
                    if (prefUtils.isNetworkAvailable()){
                        gologin();
                    }else {
                        showToast("Please check your internet connection");
                    }
                }
            }
        });

        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
            }
        });

        tvRegisterHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
            }
        });

    }

    private void showToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void gologin(){

        final NetworkAPI networkAPI = ApiClient.getClient().create(NetworkAPI.class);

        Call<CommonResponse> loginResponseCall = networkAPI.login("application/json", new Data1(new Login(email, password)));

        loginResponseCall.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                if (response.isSuccessful()){

                    String name = response.body().getUser().getName();
                    String email = response.body().getUser().getEmail();
                    String id = String.valueOf(response.body().getUser().getId());
                    String phone = response.body().getUser().getPhone();
                    String session_id = response.body().getUser().getSession_id();

                    prefUtils.setLoggedIn(session_id, name, email, "", id, phone);
                    Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                    intent.putExtra("session_id", session_id);
                    startActivity(intent);
                    finish();

                    showToast(response.body().getMessage());
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
                Log.e("Failure","Error" + t.getMessage() );
            }
        });
    }
}
