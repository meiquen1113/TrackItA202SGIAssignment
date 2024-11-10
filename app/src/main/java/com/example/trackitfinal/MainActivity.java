package com.example.trackitfinal;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    public void goToLoginPage(View view){
        Intent intent = new Intent(this, LoginPage.class);
        startActivity(intent);
    }

    public void goToSignupPage(View view){
        Intent intent = new Intent(this, SignupPage.class);
        startActivity(intent);
    }
}



