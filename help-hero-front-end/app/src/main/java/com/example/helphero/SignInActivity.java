package com.example.helphero;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class SignInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        // Edit text objects for username and password boxes
        EditText usernameEditText = (EditText)findViewById(R.id.username);
        EditText passwordEditText = (EditText)findViewById(R.id.password);

        // Button objects for sign in and sign up buttons
        Button signInButton = (Button)findViewById(R.id.buttonSignIn);
        Button signUpButton = (Button)findViewById(R.id.buttonSignUp);


    }
}