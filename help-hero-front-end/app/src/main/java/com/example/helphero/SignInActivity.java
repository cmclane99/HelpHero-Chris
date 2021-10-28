package com.example.helphero;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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

        // Create TextView object for errorMessage
        TextView errorMessageTextView = (TextView)findViewById(R.id.errorMessage);

        //create onClick method for signIn button
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                //create request queue and source
                RequestQueue queue = Volley.newRequestQueue(SignInActivity.this);

                //take user input
                String userInput = usernameEditText.getText().toString();

                //if user input is empty, do nothing
                if (userInput.isEmpty()) {
                    Toast.makeText(SignInActivity.this, "Please input a username", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    Toast.makeText(SignInActivity.this, userInput, Toast.LENGTH_SHORT).show();
                }

                //take password input
                String passInput = passwordEditText.getText().toString();

                // If there are errors display error in the errorMessageTextView
                if (userInput.equals("") || passInput.equals(""))
                    errorMessageTextView.setText("Please fill out all fields");
                else
                {
                    //RequestQueue queue = Volley.newRequestQueue(SignUpActivity.this);
                    String loginURL = "http://54.86.66.229:8000/api/login/";

                    JSONObject user = new JSONObject();

                    try{
                        user.put("username", userInput);
                        user.put("password",passInput);
                    }catch(JSONException e){
                        Toast.makeText(SignInActivity.this, "ERROR",Toast.LENGTH_SHORT).show();
                    }

                    JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, loginURL, user, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            //instantiate database value holders
                            String searchName;
                            boolean verifyPass;

                            try {
                                //obtain username from next JSONObject
                                searchName = response.getString("username");

                                //if username found in database
                                if (searchName.equals(userInput)) {

                                    //retreive corresponding password
                                    verifyPass = response.getBoolean("password_verified");

                                    //check password's correctness
                                    if (verifyPass)
                                        //proceed to Home Page
                                        startActivity(new Intent(SignInActivity.this, HomeActivity.class));
                                    else
                                        //notify user of failure
                                        Toast.makeText(SignInActivity.this, "Invalid Password!", Toast.LENGTH_SHORT).show();

                                    //end loop
                                    return;
                                }
                            } catch (JSONException e) {  //catch missing object error
                                Toast.makeText(SignInActivity.this, "No name found!", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            Toast.makeText(SignInActivity.this, "Username not found!", Toast.LENGTH_SHORT).show();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(SignInActivity.this, error.getMessage(),Toast.LENGTH_LONG).show();

                        }
                    });
                    queue.add(request);
                }
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(SignInActivity.this,SignUpActivity.class));
            }
        });
    }
}