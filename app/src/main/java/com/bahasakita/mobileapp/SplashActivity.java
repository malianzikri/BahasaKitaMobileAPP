package com.bahasakita.mobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // setting timer untuk 1 detik
        Handler handler = new Handler();
        handler.postDelayed(new Runnable(){
            @Override
            public void run() {
                //merubah activity ke activity lain
                Intent gotoLogIn = new Intent(SplashActivity.this, SignInActivity.class);
                startActivity(gotoLogIn);
                finish();
            }
        },1000);
    }
}
