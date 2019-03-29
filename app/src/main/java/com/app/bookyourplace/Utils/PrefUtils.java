package com.app.bookyourplace.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import com.app.bookyourplace.View.Activities.LoginActivity;
import java.util.HashMap;
import androidx.appcompat.app.AppCompatActivity;

public class PrefUtils {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context ctx;

    public static final String KEY_SESSION = "session_id";
    public static final String KEY_EMAIL = "user_email";
    public static final String KEY_NAME = "entnetwrk@gmail.com";
    public static final String KEY_PHONE = "user_password";
    public static final String KEY_USERID = "user_userid";
    public static final String KEY_PROFILEPIC = "user_profilepic";

    public PrefUtils(Context ctx){
        this.ctx = ctx;
        pref = ctx.getSharedPreferences("myapp", Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public void setLoggedIn(String session_id, String email, String name, String profilepic, String userid, String phone ){
        editor.putBoolean("loggedInMode",true);
        // Storing email in pref
        editor.putString(KEY_SESSION, session_id);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_USERID, userid);
        editor.putString(KEY_PHONE, phone);
        editor.putString(KEY_PROFILEPIC, profilepic);
        editor.apply();
    }

    public boolean loggedIn(){
        return pref.getBoolean("loggedInMode", false);
    }

    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<>();
        // user session_id
        user.put(KEY_SESSION, pref.getString(KEY_SESSION, null));
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null ));
        user.put(KEY_PHONE, pref.getString(KEY_PHONE, null ));
        user.put(KEY_PROFILEPIC, pref.getString(KEY_PROFILEPIC, null));
        user.put(KEY_USERID, pref.getString(KEY_USERID, null));

        // return user
        return user;
    }

    public void updateName(String name){
        editor.putString(KEY_NAME, name);
        editor.commit();
    }

    public void updatePhone(String phone){
        editor.putString(KEY_PHONE, phone);
        editor.commit();
    }

    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(ctx, LoginActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        ctx.startActivity(i);
    }

    public void fullscreen(AppCompatActivity appCompatActivity){
        View decorView = appCompatActivity.getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }

    public void dismissKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (null != activity.getCurrentFocus())
            imm.hideSoftInputFromWindow(activity.getCurrentFocus()
                    .getApplicationWindowToken(), 0);
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
