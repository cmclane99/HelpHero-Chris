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
        int [] allStrings = {R.string.AFSP, R.string.SAMHSA, R.string.NIMH, R.string.Healthline, R.string.SPL,
                R.string.NAMI, R.string.CDC, R.string.SPTS, R.string.NAASP, R.string.SPRC, R.string.CTL,
                R.string.BT1T, R.string.SAVE, R.string.TTP, R.string.ZS};

        for (int x = 0; x < 15; x++){
            allBoxes[x].setText(allStrings[x]);
        }

        Button saveFavorites = (Button) root.findViewById(R.id.saveFav);

        saveFavorites.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String filename = username + "HHFavorites.txt";
                String favNames = "";

                for(CheckBox link: allBoxes){
                    if (link.isChecked()){
                        String linkAddress = link.getText().toString();

                        favNames = favNames + linkAddress + "|";
                    }
                }


            }
        });

        return root;
    }
}
