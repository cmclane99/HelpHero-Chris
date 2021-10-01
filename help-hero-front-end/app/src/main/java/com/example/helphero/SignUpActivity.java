package com.example.helphero;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Create EditText objects to grab all the input fields
        EditText usernameEditText = (EditText)findViewById(R.id.username);
        EditText passwordEditText = (EditText)findViewById(R.id.password);
        EditText contactNameEditText = (EditText)findViewById(R.id.emergencyContactName);
        EditText contactNumberEditText = (EditText)findViewById(R.id.emergencyContactNumber);
        EditText contactRelationshipEditText = (EditText)findViewById(R.id.emergencyContactRelationship);

        // Create TextView object for errorMessage
        TextView errorMessageTextView = (TextView)findViewById(R.id.errorMessage);

        // Create button object to handle the onClick
        Button finishButton = (Button)findViewById(R.id.finishButton);

        // Store input from EditText boxes into variables
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String contactName = contactNameEditText.getText().toString();
        String contactNumber = contactNumberEditText.getText().toString();
        String contactRelationship = contactRelationshipEditText.getText().toString();

        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check for input errors when when button is clicked

                // If there are errors display error in the errorMessageTextView

                // If no errors redirect to the homepage
            }
        });


    }
}