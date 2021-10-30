package com.example.helphero;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.helphero.databinding.ActivitySosBinding;

public class SOS_Activity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
private ActivitySosBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sos);

        TextView tempWelcome = (TextView)findViewById(R.id.temp_welcome);

        Button resourceButton = (Button)findViewById(R.id.ResourcesButton);
        Button homeButton = (Button)findViewById(R.id.HomeButton);
        Button sosButton = (Button)findViewById(R.id.SOSButton);
        Button profileButton = (Button)findViewById(R.id.ProfileButton);

        sosButton.setEnabled(false);
        sosButton.setBackgroundColor(Color.CYAN);
        sosButton.setTextColor(Color.BLACK);

        resourceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(SOS_Activity.this, ResourceActivity.class));
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(SOS_Activity.this, HomeActivity.class));
            }
        });

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(SOS_Activity.this, ProfileActivity.class));
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_sos);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}