package com.example.helphero;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.helphero.databinding.ActivityHomeBinding;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityHomeBinding binding;
    private ListView selfCareListView;
    private ArrayAdapter<String> adapter;
    private String [] listItems = {"Empty task", "Empty task","Empty task","Empty task","Empty task" };
    int listCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        selfCareListView = findViewById(R.id.selfCareListView);
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_multiple_choice,listItems);
        selfCareListView.setAdapter(adapter);

        Button resourceButton = (Button) findViewById(R.id.ResourcesButton);
        Button editCheckListButton = (Button) findViewById(R.id.editCheckListButton);
        Button homeButton = (Button) findViewById(R.id.HomeButton);
        Button sosButton = (Button) findViewById(R.id.SOSButton);
        Button profileButton = (Button) findViewById(R.id.ProfileButton);
        EditText newTask = (EditText) findViewById(R.id.newTask);

        homeButton.setEnabled(false);
        homeButton.setBackgroundColor(Color.CYAN);
        homeButton.setTextColor(Color.BLACK);

        resourceButton.setOnClickListener(new View.OnClickListener() {

        TextView DailyAffirmations = (TextView) findViewById(R.id.DailyAffirmations);

        editCheckListButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(listCount < 5) {
                        listItems[listCount] = newTask.getText().toString();

                        adapter.notifyDataSetChanged();
                        selfCareListView.setItemChecked(listCount,false);
                        listCount++;

                }else {
                    Toast.makeText(HomeActivity.this, "CheckList full, Adding a new task will remove Top item", Toast.LENGTH_SHORT).show();
                    listCount = 0;

                }

            }
        });


        resourceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //takes user to the resources page
                startActivity(new Intent(HomeActivity.this, ResourceActivity.class));
            }
        });

        sosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //takes user to the SOS page
                startActivity(new Intent(HomeActivity.this, SOS_Activity.class));
            }
        });

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //takes user to the Profile page
                startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.top_toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_profile) {
            return true;
        } else if (id == R.id.action_logout) {
            //sends the user back to the sign in page when log out is selected
            //any saved variables will need to be cleared at this step however
            //there is none currently
            startActivity(new Intent(HomeActivity.this, SignInActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

}