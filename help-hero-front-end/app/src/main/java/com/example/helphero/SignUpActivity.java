import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@@ -70,11 +74,12 @@ public void onClick(View v) {
                    }

                // Check to make sure password is within limits
                if (password.length() <= 8 || password.length() > 15) {
                    isValid = true;
                if (password.length() < 8 || password.length() >= 15) {
                    errorMessageTextView.setText("Password must be at least 8 characters and less than 15");
                }
                else
                    errorMessageTextView.setText("Password must be at least 8 characters and less than 15");
                    isValid = true;



                // Check to make sure password contains a number special character
@@ -87,45 +92,44 @@ public void onClick(View v) {
                    errorMessageTextView.setText("Password must contain a number or special character - # . ( ) / % & s]");
                }

                    if(isValid ==true)
                    {
                    if(isValid == true) {
                        RequestQueue queue = Volley.newRequestQueue(SignUpActivity.this);
                        String url = "http://54.86.66.229:8000/api/create-user/";
                        String create_userURL = "http://54.86.66.229:8000/api/create-user/";

                        JSONObject user = new JSONObject();

                        try{
                        try {
                            user.put("username", username);
                            user.put("password",password);
                            user.put("EmergencyContactNameOne",contactName);
                            user.put("EmergencyContactRelationOne",contactRelationship);
                            user.put("password", password);
                            user.put("EmergencyContactNameOne", contactName);
                            user.put("EmergencyContactRelationOne", contactRelationship);
                            user.put("EmergencyContactPhoneOne", contactNumber);
                        }catch(JSONException e){
                            Toast.makeText(SignUpActivity.this, "ERROR",Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            Toast.makeText(SignUpActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
                        }

                        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, user, new Response.Listener<JSONObject>() {
                        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, create_userURL, user, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                //Toast.makeText(SignUpActivity.this, response.toString(),Toast.LENGTH_LONG).show();
                                // No errors, proceed to home page
                                Toast.makeText(SignUpActivity.this, "Success!", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(SignUpActivity.this, HomeActivity.class));
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(SignUpActivity.this, "Something went wrong.",Toast.LENGTH_LONG).show();
                                // Error, username already exists in database
                                Toast.makeText(SignUpActivity.this, "Username is already taken!", Toast.LENGTH_LONG).show();

                            }
                        });
                        queue.add(request);

                        // If no errors redirect to the homepage
                        startActivity(new Intent(SignUpActivity.this, HomeActivity.class));
                        queue.add(request);
                    }

            }
        });


    }

}
