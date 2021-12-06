package com.example.helphero.ui.sos;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.helphero.R;

import org.json.JSONException;
import org.json.JSONObject;

public class SOSFragment extends Fragment {

    private SOSViewModel SOSViewModel;
    private String username;
    private String password;
    private RequestQueue queue;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SOSViewModel = new ViewModelProvider(this).get(SOSViewModel.class);
        View root = inflater.inflate(R.layout.fragment_sos, container, false);

        String PREFERENCES = "MyPrefs";
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        username = sharedPreferences.getString("username","user");
        password = sharedPreferences.getString("password","password");

        LinearLayout contactsView = (LinearLayout)root.findViewById(R.id.card_linear_layout);

        CardView card1 = (CardView)root.findViewById(R.id.card_view1);
        CardView card2 = (CardView)root.findViewById(R.id.card_view2);
        CardView card3 = (CardView)root.findViewById(R.id.card_view3);

        TextView contactName1 = (TextView)root.findViewById(R.id.contact_name_one);
        TextView contactRelation1 = (TextView)root.findViewById(R.id.contact_relation_one);
        TextView contactNumber1 = (TextView)root.findViewById(R.id.contact_number_one);

        TextView contactName2 = (TextView)root.findViewById(R.id.contact_name_two);
        TextView contactRelation2 = (TextView)root.findViewById(R.id.contact_relation_two);
        TextView contactNumber2 = (TextView)root.findViewById(R.id.contact_number_two);

        TextView contactName3 = (TextView)root.findViewById(R.id.contact_name_three);
        TextView contactRelation3 = (TextView)root.findViewById(R.id.contact_relation_three);
        TextView contactNumber3 = (TextView)root.findViewById(R.id.contact_number_three);

        Button call1 = (Button)root.findViewById(R.id.call_contact_one_button);
        Button call2 = (Button)root.findViewById(R.id.call_contact_two_button);
        Button call3 = (Button)root.findViewById(R.id.call_contact_three_button);

        //get user's contact information
        queue = Volley.newRequestQueue(root.getContext());
        String urlUser = "http://54.86.66.229:8000/api/user-detail/"+username;

        JsonObjectRequest requestUserInfo = new JsonObjectRequest(Request.Method.GET, urlUser,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    contactName1.setText(response.getString("EmergencyContactNameOne"));
                    contactRelation1.setText(response.getString("EmergencyContactRelationOne"));
                    contactNumber1.setText(response.getString("EmergencyContactPhoneOne"));

                    contactName2.setText(response.getString("EmergencyContactNameTwo"));
                    contactRelation2.setText(response.getString("EmergencyContactRelationTwo"));
                    contactNumber2.setText(response.getString("EmergencyContactPhoneTwo"));

                    contactName3.setText(response.getString("EmergencyContactNameThree"));
                    contactRelation3.setText(response.getString("EmergencyContactRelationThree"));
                    contactNumber3.setText(response.getString("EmergencyContactPhoneThree"));

                    if(contactName1.getText().toString().equals(""))
                        contactsView.removeView(card1);
                    if(contactName2.getText().toString().equals(""))
                        contactsView.removeView(card2);
                    if(contactName3.getText().toString().equals(""))
                        contactsView.removeView(card3);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        });
        queue.add(requestUserInfo);

        call1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+contactNumber1.getText().toString()));
                startActivity(intent);
            }
        });

        call2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+contactNumber2.getText().toString()));
                startActivity(intent);
            }
        });

        call3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+contactNumber3.getText().toString()));
                startActivity(intent);
            }
        });

        return root;
    }
}