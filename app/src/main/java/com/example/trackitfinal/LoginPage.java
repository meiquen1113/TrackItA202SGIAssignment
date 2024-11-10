package com.example.trackitfinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.security.Signature;

public class LoginPage extends AppCompatActivity {

    EditText username, password;
    TextView splashMsg;
    Button loginButton;
    DatabaseLogin databaseLogin;
    MD5Hashing hashPassword;
    //Create the bundle to parse data between activity page
    Bundle bundle = new Bundle();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_page);

        username = findViewById((R.id.userNameText));
        password = findViewById(R.id.passwordText);
        loginButton = findViewById((R.id.idBtnSignIn));
        databaseLogin = new DatabaseLogin(this);
        hashPassword = new MD5Hashing();

        // display the splash message
        SmartHandlerAI smartAI = new SmartHandlerAI();
        splashMsg = findViewById(R.id.splashMessage);
        splashMsg.setText(smartAI.getSplashMessage());

        // when login button is click..
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString().trim();
                String pass = password.getText().toString().trim();
                String hashPswd = hashPassword.hashPass(pass);

                // input validation etc..
                if (user.isEmpty() || pass.isEmpty()) {
                    Toast.makeText(LoginPage.this, "Please enter username and password", Toast.LENGTH_SHORT).show();
                } else {
                    String loggedInName = databaseLogin.checkLogin(user, hashPswd);
                    if (loggedInName != "null") {
                        Toast.makeText(LoginPage.this, "Successful login!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginPage.this, ExpenseDashboard.class);
                        //Add data to bundle to parse data to next activity.
                        bundle.putString("USERNAME", loggedInName);
                        //Add the bundle to the intent
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LoginPage.this, "Invalid Username or Password!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public void gotoSignupPage(View view) {
        Intent intent = new Intent(this, SignupPage.class);
        startActivity(intent);
    }

    public void gotoChangePassword1(View view) {
        Intent intent = new Intent(this, ChangePassword.class);
        startActivity(intent);
    }
}
