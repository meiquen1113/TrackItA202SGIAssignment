package com.example.trackitfinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SettingsLoginPage extends AppCompatActivity {

    EditText username, password;
    Button loginButton;
    DatabaseLogin databaseLogin;
    MD5Hashing hashPassword;

    //Create the bundle to parse data between activity page
    Bundle bundle = new Bundle();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_settings_login_page);

        username = findViewById((R.id.userNameText1));
        password = findViewById(R.id.passwordText1);
        loginButton = findViewById((R.id.idBtnSignIn1));
        databaseLogin = new DatabaseLogin(this);
        hashPassword = new MD5Hashing();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString().trim();
                String pass = password.getText().toString().trim();
                String hashPswd = hashPassword.hashPass(pass);

                if (user.isEmpty() || pass.isEmpty()) {
                    Toast.makeText(SettingsLoginPage.this, "Please enter username and password", Toast.LENGTH_SHORT).show();
                } else {
                    String loggedInName = databaseLogin.checkLogin(user, hashPswd);
                    if (loggedInName != "null") {
                        Toast.makeText(SettingsLoginPage.this, "Successful login!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SettingsLoginPage.this, SettingsPage.class);
                        //Add data to bundle
                        bundle.putString("USERNAME", user);
                        //Add the bundle to the intent
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(SettingsLoginPage.this, "Invalid Username or Password!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public void gotoDashboard6(View view) {
        Intent intent = new Intent(this, ExpenseDashboard.class);
        startActivity(intent);
    }


}