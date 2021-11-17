package com.example.helphero.ui.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.ui.AppBarConfiguration;

import com.example.helphero.MainActivity;
import com.example.helphero.R;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    private ListView selfCareListView;
    private ArrayAdapter<String> adapter;
    private String [] listItems = {"Empty task", "Empty task","Empty task","Empty task","Empty task" };
    int listCount = 0;
    int intCount = 0;
    int intCurrent;
    String[] textData;
    SharedPreferences.Editor editor;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        String PREFERENCES = "MyPrefs";
        SharedPreferences sharedPreferences = root.getContext().getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        String username = sharedPreferences.getString("username","user");
        String password = sharedPreferences.getString("password","password");
        intCurrent = sharedPreferences.getInt("affirmationIndex",0);

        selfCareListView = root.findViewById(R.id.selfCareListView);
        adapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_list_item_multiple_choice,listItems);
        selfCareListView.setAdapter(adapter);

        Button editCheckListButton = (Button) root.findViewById(R.id.editCheckListButton);
        EditText newTask = (EditText) root.findViewById(R.id.newTask);
        TextView DailyAffirmations = (TextView) root.findViewById(R.id.DailyAffirmations);

        setDailyAffirmations(DailyAffirmations);

        editCheckListButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(listCount < 5) {
                    listItems[listCount] = newTask.getText().toString();

                    adapter.notifyDataSetChanged();
                    selfCareListView.setItemChecked(listCount,false);
                    listCount++;

                }else {
                    Toast.makeText(v.getContext(),
                            "CheckList full, Adding a new task will remove Top item", Toast.LENGTH_SHORT).show();
                    listCount = 0;

                }

            }
        });

        editor.apply();
        return root;
    }

    private void setDailyAffirmations(TextView DailyAffirmations) {
        InputStream streamCountLines = this.getResources().openRawResource(R.raw.affirmations);
        BufferedReader readerCountLines = new BufferedReader(new InputStreamReader(streamCountLines));

        try {
            while (readerCountLines.readLine() != null) {
                intCount++;
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        InputStream inputStream = this.getResources().openRawResource(R.raw.affirmations);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        textData = new String[intCount];

        try {
            for (int i = 0; i < intCount; i++) {
                textData[i] = bufferedReader.readLine();
            }
            DailyAffirmations.setText( textData[intCurrent]);
        } catch (Exception f) {
            f.printStackTrace();
        }

        intCurrent++;
        editor.putInt("affirmationIndex",intCurrent);

    }
}