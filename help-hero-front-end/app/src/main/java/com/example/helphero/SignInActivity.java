package com.example.helphero;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

        //create onClick method for signIn button
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                //create request queue and source
                RequestQueue queue = Volley.newRequestQueue(SignInActivity.this);
                String url = "http://54.86.66.229:8000/api/create-user/";

                //take user input
                String userInput = usernameEditText.getText().toString();

                //if user input is empty, do nothing
                if (userInput == "")
                    return;

                //take password input
                String passInput = passwordEditText.getText().toString();

                //find matching username in database
                JsonArrayRequest listRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                        new Response.Listener<JSONArray>() {

                            @Override
                            public void onResponse(JSONArray response) {
                                //instantiate database value holders
                                String searchName;
                                String searchPass;

                                for(int i = 0; i < response.length(); i++) {

                                    try {
                                        //obtain username from next JSONObject
                                        JSONObject searchObject = response.getJSONObject(i);
                                        searchName = searchObject.getString("username");

                                        //if username found in database
                                        if (searchName == userInput) {

                                            //retreive corresponding password
                                            searchPass = searchObject.getString("password");

                                            //check password's correctness
                                            if (searchPass == passInput)
                                                //proceed to Home Page
                                                startActivity(new Intent(SignInActivity.this, HomeActivity.class));
                                            else
                                                //notify user of failure
                                                Toast.makeText(SignInActivity.this, "Password does not match username!", Toast.LENGTH_SHORT).show();

                                            //end loop
                                            return;
                                        }
                                    } catch (JSONException e) {  //catch missing object error
                                        Toast.makeText(SignInActivity.this, "No name found!", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                }
                            }
                        },
                        new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(SignInActivity.this, "Error happened!", Toast.LENGTH_SHORT).show();
                            }
                        }
                );
            }
        });

        //create onClick method for signUp button
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                //redirect user to signUp page
                startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
            }
        });
    }
}