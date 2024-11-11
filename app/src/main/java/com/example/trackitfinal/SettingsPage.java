package com.example.trackitfinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SettingsPage extends AppCompatActivity {

    // creating variables
    private TextView welcome, loginFullname, loginEmail, loginOldpassword, loginNewPassword;
    DatabaseLogin databaseLogin;
    MD5Hashing hashPassword;
    // object declaration for input validation
    InputValidation checkInput = new InputValidation();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // EdgeToEdge.enable(this);
        setContentView(R.layout.activity_settings_page);

        // initializing all our variables.
        loginFullname = findViewById(R.id.nameEditText);
        loginEmail = findViewById(R.id.emailEditText);
        loginOldpassword = findViewById(R.id.oldPasswordEditText);
        loginNewPassword = findViewById(R.id.newPasswordEditText);

        //Get the bundle
        Bundle bundle = getIntent().getExtras();
        //Extract the data…
        String username = bundle.getString("USERNAME");
        // display to the textview
        welcome = findViewById(R.id.UsernameInfoDisplay);
        welcome.setText(username);
        // display & retrive the username & email from sql dbase
        databaseLogin = new DatabaseLogin(this);
        hashPassword = new MD5Hashing();
        // retrieve the full name & email data from the sql dbase
        loginFullname.setText(databaseLogin.getFullname(username));
        loginEmail.setText(databaseLogin.getEmail(username));
    }

    // Update the login profile to sql database.
    public void updateProfile (View view) {

        // below line is to get data from all edit text fields.
        String usernameTxt = welcome.getText().toString();
        String fullnameProfile = loginFullname.getText().toString();
        String emailProfile = loginEmail.getText().toString();

        // validating if the text fields are empty or not.
        if (fullnameProfile.isEmpty() || emailProfile.isEmpty()) {
            Toast.makeText(SettingsPage.this, "Please enter all the data..", Toast.LENGTH_SHORT).show();
            return;
        }
        // input validation to ensure correct full name & email format.
        String validateMessage = checkInput.validFullname(fullnameProfile);
        if ( validateMessage != null) {
            Toast.makeText(SettingsPage.this, validateMessage, Toast.LENGTH_SHORT).show();
            return;
        }
        validateMessage = checkInput.validEmail(emailProfile);
        if ( validateMessage != null) {
            Toast.makeText(SettingsPage.this, validateMessage, Toast.LENGTH_SHORT).show();
            return;
        }
        // update login profile to sqlite dbase and pass all our values to it.
        databaseLogin.updateProfile(usernameTxt, fullnameProfile, emailProfile);
        databaseLogin.close();
        // after adding the data we are displaying a toast message.
        Toast.makeText(SettingsPage.this, "Profile for "+usernameTxt+" has been updated!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, ExpenseDashboard.class);
        startActivity(intent);
    }

    // Update the login password to sql database.
    public void updatePassword (View view) {
        // below line is to get data from all edit text fields.
        String usernameTxt = welcome.getText().toString();
        String oldpassword = loginOldpassword.getText().toString();
        String newpassword = loginNewPassword.getText().toString();
        String hashOldPswd = hashPassword.hashPass(oldpassword);
        String hashNewPswd = hashPassword.hashPass(newpassword);

        // validating if the text fields are empty or not.
        if (oldpassword.isEmpty() || newpassword.isEmpty()) {
            Toast.makeText(SettingsPage.this, "Please enter both old and new password.", Toast.LENGTH_SHORT).show();
            return;
        }
        // input validation to ensure correct password format.
        String validateMessage = checkInput.validPassword(newpassword);
        if ( validateMessage != null) {
            Toast.makeText(SettingsPage.this, validateMessage, Toast.LENGTH_SHORT).show();
            return;
        }
        // validate the username & old password pair is correct before updating the new password.
        String loggedInName = databaseLogin.checkLogin(usernameTxt, hashOldPswd);
        if (loggedInName != "null") {
            // update login profile to sqlite dbase and pass all our values to it.
            databaseLogin.updatePassword(usernameTxt, hashNewPswd);
            databaseLogin.close();
            // after adding the data we are displaying a toast message.
            Toast.makeText(SettingsPage.this, "New Password for "+usernameTxt+" has been updated!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(SettingsPage.this, ExpenseDashboard.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(SettingsPage.this, "Invalid old Password for "+usernameTxt, Toast.LENGTH_SHORT).show();
        }
    }

    public void gotoDashboard3 (View view) {
        Intent intent = new Intent(this, ExpenseDashboard.class);
        startActivity(intent);
    }

    public void goToWelcomePage2 (View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}