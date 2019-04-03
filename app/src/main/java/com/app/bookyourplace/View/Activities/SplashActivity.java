package com.app.bookyourplace.View.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.app.bookyourplace.R;
import com.app.bookyourplace.Utils.PrefUtils;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private PrefUtils prefUtils;
    private boolean loggedIn = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        prefUtils = new PrefUtils(getApplicationContext());

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
