package com.example.helphero.ui.resources;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.helphero.R;
import com.example.helphero.SignInActivity;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ResourcesFragment extends Fragment {

    private ResourcesViewModel resourcesViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        resourcesViewModel = new ViewModelProvider(this).get(ResourcesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_resources, container, false);

        //Prepare to save username for session
        String PREFERENCES = "MyPrefs";
        SharedPreferences sharedpreferences = root.getContext().getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();

        String username = sharedpreferences.getString("username", "user");
        String filename = username + "HHFavorites.txt";

        //find Resource page elements
        CheckBox box1 = (CheckBox)root.findViewById(R.id.check1);
        CheckBox box2 = (CheckBox)root.findViewById(R.id.check2);
        CheckBox box3 = (CheckBox)root.findViewById(R.id.check3);
        CheckBox box4 = (CheckBox)root.findViewById(R.id.check4);
        CheckBox box5 = (CheckBox)root.findViewById(R.id.check5);
        CheckBox box6 = (CheckBox)root.findViewById(R.id.check6);
        CheckBox box7 = (CheckBox)root.findViewById(R.id.check7);
        CheckBox box8 = (CheckBox)root.findViewById(R.id.check8);
        CheckBox box9 = (CheckBox)root.findViewById(R.id.check9);
        CheckBox box10 = (CheckBox)root.findViewById(R.id.check10);
        CheckBox box11 = (CheckBox)root.findViewById(R.id.check11);
        CheckBox box12 = (CheckBox)root.findViewById(R.id.check12);
        CheckBox box13 = (CheckBox)root.findViewById(R.id.check13);
        CheckBox box14 = (CheckBox)root.findViewById(R.id.check14);
        CheckBox box15 = (CheckBox)root.findViewById(R.id.check15);

        //collect elements into an array
        CheckBox[] allBoxes = {box1, box2, box3, box4, box5, box6, box7, box8, box9, box10, box11, box12, box13, box14, box15};
        ArrayList<Integer> allStrings = new ArrayList<Integer>();
        allStrings.add(R.string.AFSP);
        allStrings.add(R.string.SAMHSA);
        allStrings.add(R.string.NIMH);
        allStrings.add(R.string.Healthline);
        allStrings.add(R.string.SPL);
        allStrings.add(R.string.NAMI);
        allStrings.add(R.string.CDC);
        allStrings.add(R.string.SPTS);
        allStrings.add(R.string.NAASP);
        allStrings.add(R.string.SPRC);
        allStrings.add(R.string.CTL);
        allStrings.add(R.string.BT1T);
        allStrings.add(R.string.SAVE);
        allStrings.add(R.string.TTP);
        allStrings.add(R.string.ZS);

        try{
            FileInputStream getFile = root.getContext().openFileInput(filename);

            int read = -1;
            StringBuffer buffer = new StringBuffer();
            while ((read = getFile.read()) != -1){
                buffer.append((char)read);
            }

            String fileText = buffer.toString();
            Toast.makeText(root.getContext(), fileText, Toast.LENGTH_LONG).show();
        }
        catch(IOException e){
            Toast.makeText(root.getContext(), "Error getting file!", Toast.LENGTH_LONG).show();
        }

        for (int x = 0; x < 15; x++){
            allBoxes[x].setText(allStrings.get(x));
        }

        Button saveFavorites = (Button) root.findViewById(R.id.saveFav);

        saveFavorites.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String favNames = "";

                for(CheckBox link: allBoxes){
                    if (link.isChecked()){
                        String linkAddress = link.getText().toString();

                        for(int i = 0; i < 15; i++){
                            if (linkAddress.equals(getString(allStrings.get(i)))){
                                favNames = favNames + Integer.toString(i) + "|";
                            }
                        }
                    }
                }

                try {
                    FileOutputStream favFileWrite = root.getContext().openFileOutput(filename, Context.MODE_PRIVATE);
                    byte[] byteArray = favNames.getBytes();
                    favFileWrite.write(byteArray);
                    favFileWrite.close();
                    Toast.makeText(root.getContext(), "File change success!", Toast.LENGTH_LONG).show();
                }
                catch(IOException e){
                    Toast.makeText(root.getContext(), "File change not possible.", Toast.LENGTH_LONG).show();
                }
            }
        });

        return root;
    }
}
