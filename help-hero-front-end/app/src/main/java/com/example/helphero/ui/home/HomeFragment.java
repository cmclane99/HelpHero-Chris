package com.example.helphero.ui.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

import com.example.helphero.Database;
import com.example.helphero.MainActivity;
import com.example.helphero.R;
import com.example.helphero.TaskModel;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    private ListView selfCareListView;


    int intCount = 0;
    int intCurrent;
    String[] textData;
    SharedPreferences.Editor editor;

    ArrayAdapter taskArrayAdapter;
    Database database;



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

        Button editCheckListButton = (Button) root.findViewById(R.id.editCheckListButton);
        EditText newTask = (EditText) root.findViewById(R.id.newTask);
        TextView DailyAffirmations = (TextView) root.findViewById(R.id.DailyAffirmations);

        setDailyAffirmations(DailyAffirmations);

        database = new Database(getActivity());
        taskArrayAdapter = new ArrayAdapter<TaskModel>(getActivity(), android.R.layout.simple_list_item_1, database.getAll());
        selfCareListView.setAdapter(taskArrayAdapter);

        editCheckListButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                TaskModel taskModel = new TaskModel();

                taskModel.setTaskTitle(newTask.getText().toString());
                Toast.makeText(getActivity(), taskModel.toString(), Toast.LENGTH_SHORT).show();
                database.addOne(taskModel);


                taskArrayAdapter = new ArrayAdapter<TaskModel>(getActivity(), android.R.layout.simple_list_item_1, database.getAll());
                selfCareListView.setAdapter(taskArrayAdapter);



            }
        });

        selfCareListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TaskModel clickedTask = (TaskModel) adapterView.getItemAtPosition(i);
                database.deleteOne(clickedTask);
                Toast.makeText(getActivity(), "DELETED: " + clickedTask.toString(), Toast.LENGTH_SHORT).show();
                taskArrayAdapter = new ArrayAdapter<TaskModel>(getActivity(), android.R.layout.simple_list_item_1, database.getAll());
                selfCareListView.setAdapter(taskArrayAdapter);
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