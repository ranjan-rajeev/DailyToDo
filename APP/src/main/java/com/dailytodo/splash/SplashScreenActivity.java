package com.dailytodo.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.dailytodo.R;
import com.dailytodo.Utility.PrefManager;
import com.dailytodo.home.HomeActivity;
import com.dailytodo.login.LoginActivity;

public class SplashScreenActivity extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 3000;
    PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        prefManager = new PrefManager(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (prefManager.getUser() != null) {
                   startActivity(new Intent(SplashScreenActivity.this, HomeActivity.class));
                    //startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
                } else {
                    startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
                }
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
