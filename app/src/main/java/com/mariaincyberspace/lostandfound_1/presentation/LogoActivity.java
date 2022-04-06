package com.mariaincyberspace.lostandfound_1.presentation;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mariaincyberspace.lostandfound_1.R;
import com.mariaincyberspace.lostandfound_1.presentation.auth.LoginActivity;

public class LogoActivity extends AppCompatActivity {

    private static final int SPLASH_SCREEN_TIME_OUT = 3000;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_logo);


        new Handler().postDelayed(() -> {
            FirebaseUser currentUser = auth.getCurrentUser();
            Intent i = (currentUser != null) ? new Intent(LogoActivity.this, MainActivity.class)
                    : new Intent(LogoActivity.this, LoginActivity.class);
            startActivity(i);
            finish();
        }, SPLASH_SCREEN_TIME_OUT);
    }
}