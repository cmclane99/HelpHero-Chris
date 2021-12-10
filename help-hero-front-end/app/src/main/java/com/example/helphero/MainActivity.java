package com.example.helphero;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.helphero.ui.home.HomeFragment;
import com.example.helphero.ui.profile.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private String username;
    private String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String PREFERENCES = "MyPrefs";
        sharedPreferences = getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        username = sharedPreferences.getString("username","");
        password = sharedPreferences.getString("password","");

        if(username.equals("")||password.equals(""))
            startActivity(new Intent(MainActivity.this, SignInActivity.class));
        else
        {
            setContentView(R.layout.activity_main);
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);


            BottomNavigationView navView = findViewById(R.id.nav_view);
            // Passing each menu ID as a set of Ids because each
            // menu should be considered as top level destinations.
            AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.navigation_resources, R.id.navigation_home, R.id.navigation_sos,
                    R.id.navigation_profile).build();
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
            NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
            NavigationUI.setupWithNavController(navView, navController);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            //sends the user back to the sign in page when log out is selected
            //any saved variables will need to be cleared at this step however
            //there is none currently
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("username","");
            editor.putString("password","");
            editor.apply();

            startActivity(new Intent(MainActivity.this, SignInActivity.class));

        }

        return super.onOptionsItemSelected(item);
    }

}