package com.example.helphero;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.helphero.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        TextView tempWelcome = (TextView)findViewById(R.id.temp_welcome);

        Button resourceButton = (Button)findViewById(R.id.ResourcesButton);
        Button homeButton = (Button)findViewById(R.id.HomeButton);
        Button sosButton = (Button)findViewById(R.id.SOSButton);
        Button profileButton = (Button)findViewById(R.id.ProfileButton);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}