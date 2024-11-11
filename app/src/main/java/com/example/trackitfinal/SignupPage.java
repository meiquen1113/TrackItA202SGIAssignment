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

public class SignupPage extends AppCompatActivity {

    EditText username, password, fullname, email;
    Button signupButton;
    DatabaseLogin databaseLogin;
    MD5Hashing hashPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup_page);

        username = findViewById(R.id.EntTxtUsername);
        password = findViewById(R.id.EntTxtPassword);
        fullname = findViewById(R.id.EntFullname);
        email = findViewById(R.id.EntEmail);
        signupButton = findViewById(R.id.BtnSignUp);

        databaseLogin = new DatabaseLogin(this);
        hashPassword = new MD5Hashing();
        InputValidation checkInput = new InputValidation();

        signupButton.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String user = username.getText().toString().trim();
                String pass = password.getText().toString().trim();
                String name = fullname.getText().toString().trim();
                String mail = email.getText().toString().trim();
                Boolean userExist = databaseLogin.checkUser(user);
                String hashPswd = hashPassword.hashPass(pass);

                if (user.isEmpty() || pass.isEmpty() || name.isEmpty() || mail.isEmpty()) {
                    Toast.makeText(SignupPage.this, "Please enter all the fields!", Toast.LENGTH_SHORT).show();
                } else {
                    // input validation to ensure correct username, password, fullname & email format.
                    String validateMessage = checkInput.validUsername(user);
                    if ( validateMessage != null) {
                        Toast.makeText(SignupPage.this, validateMessage, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    validateMessage = checkInput.validPassword(pass);
                    if ( validateMessage != null) {
                        Toast.makeText(SignupPage.this, validateMessage, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    validateMessage = checkInput.validFullname(name);
                    if ( validateMessage != null) {
                        Toast.makeText(SignupPage.this, validateMessage, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    validateMessage = checkInput.validEmail(mail);
                    if ( validateMessage != null) {
                        Toast.makeText(SignupPage.this, validateMessage, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    // check if username has been registered before.
                    if (userExist) {
                        Toast.makeText(SignupPage.this, "Username already exists!", Toast.LENGTH_SHORT).show();
                    } else {
                        // save the hash password together with other info to the sql dbase.
                        databaseLogin.insertData(user, hashPswd, name, mail);
                        Toast.makeText(SignupPage.this, "Registration for " + name + " Successful!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SignupPage.this, LoginPage.class);
                        startActivity(intent);
                    }

                }
            }
        }));
    }

    public void gotoLoginPage1(View view) {
        Intent intent = new Intent(this, LoginPage.class);
        startActivity(intent);
    }
}