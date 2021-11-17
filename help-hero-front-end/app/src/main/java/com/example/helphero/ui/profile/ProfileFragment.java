package com.example.helphero.ui.profile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.helphero.MainActivity;
import com.example.helphero.R;
import com.example.helphero.SignInActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;
    private String username;
    private String password;
    private RequestQueue queue;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        String PREFERENCES = "MyPrefs";
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        username = sharedPreferences.getString("username","user");
        password = sharedPreferences.getString("password","password");

        //edit text boxes
        EditText contactName1 = (EditText)root.findViewById(R.id.contact_name_one);
        EditText contactRelation1 = (EditText)root.findViewById(R.id.contact_relation_one);
        EditText contactNumber1 = (EditText)root.findViewById(R.id.contact_number_one);

        EditText contactName2 = (EditText)root.findViewById(R.id.contact_name_two);
        EditText contactRelation2 = (EditText)root.findViewById(R.id.contact_relation_two);
        EditText contactNumber2 = (EditText)root.findViewById(R.id.contact_number_two);

        EditText contactName3 = (EditText)root.findViewById(R.id.contact_name_three);
        EditText contactRelation3 = (EditText)root.findViewById(R.id.contact_relation_three);
        EditText contactNumber3 = (EditText)root.findViewById(R.id.contact_number_three);

        //checkboxes
        CheckBox checkBox1 = (CheckBox)root.findViewById(R.id.checkbox1);
        CheckBox checkBox2 = (CheckBox)root.findViewById(R.id.checkbox2);
        CheckBox checkBox3 = (CheckBox)root.findViewById(R.id.checkbox3);

        //edit buttons
        Button editButton1 = (Button)root.findViewById(R.id.edit_contact_one_button);
        Button editButton2 = (Button)root.findViewById(R.id.edit_contact_two_button);
        Button editButton3 = (Button)root.findViewById(R.id.edit_contact_three_button);

        //confirm buttons
        Button confirmButton1 = (Button)root.findViewById(R.id.confirm_button1);
        Button confirmButton2 = (Button)root.findViewById(R.id.confirm_button2);
        Button confirmButton3 = (Button)root.findViewById(R.id.confirm_button3);

        //delete buttons
        Button deleteButton = (Button)root.findViewById(R.id.delete_button);
        Button deleteProfileButton = (Button)root.findViewById(R.id.delete_profile_button);

        //error text views
        TextView editError1 = (TextView)root.findViewById(R.id.error_edit_one);
        TextView editError2 = (TextView)root.findViewById(R.id.error_edit_two);
        TextView editError3 = (TextView)root.findViewById(R.id.error_edit_three);
        TextView deleteError = (TextView)root.findViewById(R.id.error_text_view);

        //username edit text and button
        EditText displayUsername = (EditText)root.findViewById(R.id.display_username);
        Button editUsername = (Button)root.findViewById(R.id.edit_username);

        //get user's contact information
        queue = Volley.newRequestQueue(root.getContext());
        String urlUser = "http://54.86.66.229:8000/api/user-detail/"+username;

        JsonObjectRequest requestUserInfo = new JsonObjectRequest(Request.Method.GET, urlUser,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    displayUsername.setText(response.getString("username"));

                    contactName1.setText(response.getString("EmergencyContactNameOne"));
                    contactRelation1.setText(response.getString("EmergencyContactRelationOne"));
                    contactNumber1.setText(response.getString("EmergencyContactPhoneOne"));

                    contactName2.setText(response.getString("EmergencyContactNameTwo"));
                    contactRelation2.setText(response.getString("EmergencyContactRelationTwo"));
                    contactNumber2.setText(response.getString("EmergencyContactPhoneTwo"));

                    contactName3.setText(response.getString("EmergencyContactNameThree"));
                    contactRelation3.setText(response.getString("EmergencyContactRelationThree"));
                    contactNumber3.setText(response.getString("EmergencyContactPhoneThree"));

                    //if contact card is empty change edit to add and disable check boxes
                    if(contactName1.getText().toString().equals(""))
                    {
                        editButton1.setText("Add Contact");
                        checkBox1.setEnabled(false);
                    }
                    if(contactName2.getText().toString().equals(""))
                    {
                        editButton2.setText("Add Contact");
                        checkBox2.setEnabled(false);
                    }
                    if(contactName3.getText().toString().equals(""))
                    {
                        editButton3.setText("Add Contact");
                        checkBox3.setEnabled(false);
                    }
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

        //change username button changes user name in database
        editUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newUsername = displayUsername.getText().toString();

                String urlChangeUsername = "http://54.86.66.229:8000/api/change-username/"+username;

                JSONObject user = new JSONObject();

                try{
                    user.put("username",newUsername);
                    user.put("password",password);
                    user.put("EmergencyContactNameOne",contactName1.getText().toString());
                    user.put("EmergencyContactRelationOne",contactRelation1.getText().toString());
                    user.put("EmergencyContactPhoneOne",contactNumber1.getText().toString());
                    user.put("EmergencyContactNameTwo",contactName2.getText().toString());
                    user.put("EmergencyContactRelationTwo",contactRelation2.getText().toString());
                    user.put("EmergencyContactPhoneTwo",contactNumber2.getText().toString());
                    user.put("EmergencyContactNameThree",contactName3.getText().toString());
                    user.put("EmergencyContactRelationThree",contactRelation3.getText().toString());
                    user.put("EmergencyContactPhoneThree",contactNumber3.getText().toString());

                }catch(JSONException e){

                }

                JsonObjectRequest requestUpdateContacts = new JsonObjectRequest(Request.Method.POST,
                        urlChangeUsername, user, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("username",newUsername);
                        editor.apply();
                        username = newUsername;
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {


                    }
                });
                queue.add(requestUpdateContacts);
            }
        });

        //when contact edit button is clicked
        editButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setEditButton(editButton1,confirmButton1,contactName1,contactRelation1,
                        contactNumber1);
                checkBox1.setEnabled(false);

            }
        });

        //when contact edit button is clicked
        editButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setEditButton(editButton2,confirmButton2,contactName2,contactRelation2,
                        contactNumber2);
                checkBox2.setEnabled(false);
            }
        });

        //when contact edit button is clicked
        editButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setEditButton(editButton3,confirmButton3,contactName3,contactRelation3,
                        contactNumber3);
                checkBox3.setEnabled(false);
            }
        });

        //when contact 1 confirm button is clicked
        confirmButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmAction(editButton1,confirmButton1,contactName1,contactRelation1,
                        contactNumber1,editError1,checkBox1,1);
            }
        });

        //when contact 2 confirm button is clicked
        confirmButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmAction(editButton2,confirmButton2,contactName2,contactRelation2,
                        contactNumber2,editError2,checkBox2,2);
            }
        });

        //when contact 3 confirm button is clicked
        confirmButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmAction(editButton3,confirmButton3,contactName3,contactRelation3,
                        contactNumber3,editError3,checkBox3,3);
            }
        });

        //display delete contacts button when at least one contact is selected
        checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                displayDeleteButton(checkBox1,checkBox2,checkBox3,deleteButton);
            }
        });

        checkBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                displayDeleteButton(checkBox1,checkBox2,checkBox3,deleteButton);
            }
        });

        checkBox3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                displayDeleteButton(checkBox1,checkBox2,checkBox3,deleteButton);
            }
        });

        //when delete button is clicked
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //count active checkboxes
                int activeBoxes = 0;
                if(checkBox1.isEnabled())
                    activeBoxes++;
                if(checkBox2.isEnabled())
                    activeBoxes++;
                if(checkBox3.isEnabled())
                    activeBoxes++;

                //count checked checkboxes
                int checkedBoxes = 0;
                if(checkBox1.isChecked())
                    checkedBoxes++;
                if(checkBox2.isChecked())
                    checkedBoxes++;
                if(checkBox3.isChecked())
                    checkedBoxes++;

                //make sure user has at least one contact before deleting checked contacts
                if((activeBoxes-checkedBoxes)<1)
                    deleteError.setText("You must have at least one emergency contact.");
                else
                {
                    deleteError.setText("");
                    //delete data from backend
                    if(checkBox1.isChecked())
                    {
                        deleteContact(1, contactName1, contactRelation1, contactNumber1, editButton1);
                        checkBox1.setChecked(false);
                        checkBox1.setEnabled(false);
                    }
                    if(checkBox2.isChecked())
                    {
                        deleteContact(2, contactName2, contactRelation2, contactNumber2, editButton2);
                        checkBox2.setChecked(false);
                        checkBox2.setEnabled(false);
                    }
                    if(checkBox3.isChecked())
                    {
                        deleteContact(3, contactName3, contactRelation3, contactNumber3, editButton3);
                        checkBox3.setChecked(false);
                        checkBox3.setEnabled(false);
                    }
                }
            }
        });

        //when delete profile button is clicked remove user from database & go back to sign in page
        deleteProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String urlDeleteUser = "http://54.86.66.229:8000/api/delete-user/"+username;

                StringRequest requestDeleteUser = new StringRequest(Request.Method.POST, urlDeleteUser,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                startActivity(new Intent(root.getContext(),
                                        SignInActivity.class));
                            }
                        },null);

                queue.add(requestDeleteUser);
            }
        });
        return root;
    }

    //method to make delete button appear and disappear
    public void displayDeleteButton(CheckBox check1, CheckBox check2, CheckBox check3,
                                    Button delete)
    {
        //if at least one checkbox is checked display the delete button else make invisible
        if(check1.isChecked()||check2.isChecked()||check3.isChecked())
        {
            delete.setEnabled(true);
            delete.setVisibility(View.VISIBLE);
        }
        else if(!check1.isChecked() && !check2.isChecked() && !check3.isChecked())
        {
            delete.setEnabled(false);
            delete.setVisibility(View.INVISIBLE);
        }
    }

    //method to set changes when the edit button is clicked
    public void setEditButton(Button editButton, Button confirmButton, EditText contactName,
                              EditText contactRelation, EditText contactNumber)
    {
        //enable edit text boxes to be changed
        contactName.setEnabled(true);
        contactRelation.setEnabled(true);
        contactNumber.setEnabled(true);

        //enable confirm change button
        confirmButton.setVisibility(View.VISIBLE);
        confirmButton.setEnabled(true);

        //disable edit button
        editButton.setEnabled(false);
    }

    //method to set changes when the confirm button is clicked
    public void confirmAction(Button editButton, Button confirmButton, EditText contactName,
                              EditText contactRelation, EditText contactNumber,
                              TextView editError, CheckBox checkBox,int contact)
    {
        //display an error if all fields are not entered
        if((contactName.getText().toString().equals("")) ||
                (contactNumber.getText().toString().equals(""))||
                (contactRelation.getText().toString().equals("")))
        {
            editError.setText("Please fill out all fields.");
        }
        else
        {
            //send changes to backend
            String urlEditContacts = "http://54.86.66.229:8000/api/edit-contacts/"+username+"/";

            JSONObject user = new JSONObject();

            try{
                if(contact==1)
                {
                    user.put("EmergencyContactNameOne", contactName.getText().toString());
                    user.put("EmergencyContactRelationOne", contactRelation.getText().toString());
                    user.put("EmergencyContactPhoneOne", contactNumber.getText().toString());
                }
                else if(contact==2)
                {
                    user.put("EmergencyContactNameTwo", contactName.getText().toString());
                    user.put("EmergencyContactRelationTwo", contactRelation.getText().toString());
                    user.put("EmergencyContactPhoneTwo", contactNumber.getText().toString());
                }
                else if(contact==3)
                {
                    user.put("EmergencyContactNameThree", contactName.getText().toString());
                    user.put("EmergencyContactRelationThree", contactRelation.getText().toString());
                    user.put("EmergencyContactPhoneThree", contactNumber.getText().toString());
                }

            }catch(JSONException e){

            }

            JsonObjectRequest requestUpdateContacts = new JsonObjectRequest(Request.Method.PUT,
                    urlEditContacts, user, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {


                }
            });
            queue.add(requestUpdateContacts);

            //return buttons and edit text to normal
            editError.setText("");
            editButton.setEnabled(true);
            confirmButton.setEnabled(false);
            confirmButton.setVisibility(View.INVISIBLE);
            contactName.setEnabled(false);
            contactRelation.setEnabled(false);
            contactNumber.setEnabled(false);

            editButton.setText("Edit");
            checkBox.setEnabled(true);
        }

    }

    //method to delete contact from database
    public void deleteContact(int contact,EditText contactName, EditText contactRelation,
                              EditText contactNumber,Button editButton)
    {
        String urlDeleteContact = "http://54.86.66.229:8000/api/edit-contacts/"+username+"/";

        JSONObject user = new JSONObject();

        try{
            // Set contact to empty string
            if(contact==1)
            {
                user.put("EmergencyContactNameOne", "");
                user.put("EmergencyContactRelationOne", "");
                user.put("EmergencyContactPhoneOne", "");
            }
            else if(contact==2)
            {
                user.put("EmergencyContactNameTwo", "");
                user.put("EmergencyContactRelationTwo", "");
                user.put("EmergencyContactPhoneTwo", "");
            }
            else if(contact==3)
            {
                user.put("EmergencyContactNameThree", "");
                user.put("EmergencyContactRelationThree", "");
                user.put("EmergencyContactPhoneThree", "");
            }
        }catch(JSONException e){

        }

        JsonObjectRequest requestDeleteContact = new JsonObjectRequest(Request.Method.PUT,
                urlDeleteContact, user, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                contactName.setText("");
                contactRelation.setText("");
                contactNumber.setText("");

                editButton.setText("Add Contact");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(requestDeleteContact);
    }
}