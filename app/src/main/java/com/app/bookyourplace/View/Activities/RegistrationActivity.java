package com.app.bookyourplace.View.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.bookyourplace.Model.Data;
import com.app.bookyourplace.Model.ResponseBean.CommonResponse;
import com.app.bookyourplace.Model.User;
import com.app.bookyourplace.Network.ApiClient;
import com.app.bookyourplace.Network.NetworkAPI;
import com.app.bookyourplace.R;
import com.app.bookyourplace.Utils.PrefUtils;
import com.google.gson.Gson;

import org.json.JSONObject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity {

    private EditText etName, etEmail, etPhone, etPassword;
    private Button btnSignUp;
    private String name, email, phone, password;
    private PrefUtils prefUtils;
    private CheckBox termCheckBox;
    private TextView tvLoginHere;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        prefUtils = new PrefUtils(this);

        etName = findViewById(R.id.et_name_signup);
        etEmail = findViewById(R.id.et_email_signup);
        etPhone = findViewById(R.id.et_phone_signup);
        etPassword = findViewById(R.id.et_password_signup);
        termCheckBox = findViewById(R.id.termsCheckBox);
        btnSignUp = findViewById(R.id.btn_signup);
        tvLoginHere = findViewById(R.id.tv_loginHere);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = etName.getText().toString();
                email = etEmail.getText().toString();
                phone = etPhone.getText().toString();
                password = etPassword.getText().toString();

                if (name.isEmpty()){
                    showToast("Please enter your name");
                }else if (email.isEmpty()){
                    showToast("Please enter your email");
                }else if (phone.isEmpty()){
                    showToast("Please enter your phone");
                }else if (password.isEmpty()){
                    showToast("Please enter your phone");
                }else if (!termCheckBox.isChecked()){
                    showToast("Please check terms and conditions");
                }else {
                    if (prefUtils.isNetworkAvailable()){
                        goRegister();
                    }else {
                        showToast("Please check your internet connection");
                    }
                }
            }
        });

        tvLoginHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
            }
        });


    }

    private void goRegister(){

        final NetworkAPI networkAPI = ApiClient.getClient().create(NetworkAPI.class);

        Call<CommonResponse> registerResponseCall = networkAPI.register("application/json", new Data(new User(name,email, phone, password, password)));

        registerResponseCall.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                if (response.isSuccessful()){
                    showToast(response.body().getMessage());
                    Intent intent = new Intent(RegistrationActivity.this, VerifyAccountActivity.class);
                    intent.putExtra("email", email);
                    startActivity(intent);
                }else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Gson gson = new Gson();
                        showToast(jObjError.getJSONArray("errors").toString());
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

    private void showToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
