package com.example.trackitfinal;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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

public class ChangePassword extends AppCompatActivity {

    EditText usernameET, emailET, passwordET;
    Button changePassButton;
    DatabaseLogin databaseLogin;
    MD5Hashing hashPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // EdgeToEdge.enable(this);
        setContentView(R.layout.activity_change_password);

        usernameET = findViewById((R.id.EntUsernameChgPswd));
        emailET = findViewById(R.id.EntEmailChgPswd);
        passwordET = findViewById(R.id.EntPasswordChgPswd);
        changePassButton = findViewById((R.id.BtnChangePswd));
        databaseLogin = new DatabaseLogin(this);
        hashPassword = new MD5Hashing();
        InputValidation checkInput = new InputValidation();

        changePassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = usernameET.getText().toString().trim();
                String pass = passwordET.getText().toString().trim();
                String email = emailET.getText().toString().trim();
                String hashPswd = hashPassword.hashPass(pass);

                if (user.isEmpty() || email.isEmpty()) {
                    Toast.makeText(ChangePassword.this, "Please enter username and email", Toast.LENGTH_SHORT).show();
                } else {
                    // input validation to ensure correct password format.
                    String validateMessage = checkInput.validPassword(pass);
                    if ( validateMessage != null) {
                        Toast.makeText(ChangePassword.this, validateMessage, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    // validate username & email pair is correct in sql dbase before updating the password.
                    String profileName = databaseLogin.checkProfile(user, email);
                    if (profileName != "null") {
                        databaseLogin.updatePassword(user,hashPswd);
                        Toast.makeText(ChangePassword.this, "Password successfully changed for " + profileName, Toast.LENGTH_LONG).show();
                        //Intent intent = new Intent(ChangePassword.this, ExpenseDashboard.class);
                        //startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(ChangePassword.this, "ERROR! Invalid Username or email!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    public void gotoLoginPage3 (View view){
        Intent intent = new Intent(this, LoginPage.class);
        startActivity(intent);
    }
}