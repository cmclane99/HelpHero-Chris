package com.example.helphero;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {

    SharedPreferences s;

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

        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Store input from EditText boxes into variables
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String contactName = contactNameEditText.getText().toString();
                String contactNumber = contactNumberEditText.getText().toString();
                String contactRelationship = contactRelationshipEditText.getText().toString();

                // Check to make sure password contains a number special character
                //Pattern pattern = Pattern.compile("[a-zA-Z0-9\\-#\\.\\(\\)\\/%&\\s]");
                Pattern pattern = Pattern.compile("[\\-#\\.\\*\\&\\%\\#\\$\\(\\)\\/%&\\s]");
                Matcher matcher = pattern.matcher(password);
                boolean pwContainsSpecialCharacter = matcher.find();


                // Set up request queue and request the list of users
                RequestQueue queue = Volley.newRequestQueue(SignUpActivity.this);
                String urlUserList = "http://54.86.66.229:8000/api/user-list/";

                JsonArrayRequest requestUserList = new JsonArrayRequest(Request.Method.GET, urlUserList, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Loop through the Json array to see if the username would be a duplicate
                        for (int i = 0 ; i < response.length(); i++) {
                            JSONObject obj = null;
                            try {
                                obj = response.getJSONObject(i);
                                String u = obj.getString("username");
                                if(username.equals(u))
                                {
                                    errorMessageTextView.setText("That username is already taken.");
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SignUpActivity.this, "Something went wrong.",Toast.LENGTH_LONG).show();

                    }
                });
                queue.add(requestUserList);


                // If there are errors display error in the errorMessageTextView
                if (username.equals("") || password.equals("") || contactName.equals("") ||
                        contactNumber.equals("") || contactRelationship.equals(""))
                    errorMessageTextView.setText("Please fill out all fields");

                    // Check to make sure password is within limits
                else if (password.length() < 8 || password.length() > 15)
                    errorMessageTextView.setText("Password must be at least 8 characters and less than 15");

                else if(!pwContainsSpecialCharacter)
                    errorMessageTextView.setText("Password must contain a number or special character - # . ( ) / % & s]");

                else
                {
                    //RequestQueue queue = Volley.newRequestQueue(SignUpActivity.this);
                    String url = "http://54.86.66.229:8000/api/create-user/";

                    JSONObject user = new JSONObject();

                    try{
                        user.put("username", username);
                        user.put("password",password);
                        user.put("EmergencyContactNameOne",contactName);
                        user.put("EmergencyContactRelationOne",contactRelationship);
                        user.put("EmergencyContactPhoneOne", contactNumber);
                    }catch(JSONException e){
                        Toast.makeText(SignUpActivity.this, "ERROR",Toast.LENGTH_SHORT).show();
                    }

                    JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, user, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // If no errors redirect to the homepage
                            startActivity(new Intent(SignUpActivity.this, HomeActivity.class));
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(SignUpActivity.this, "Something went wrong.",Toast.LENGTH_LONG).show();

                        }
                    });
                    queue.add(request);
                }


            }
        });


    }

}