package com.example.helphero.ui.resources;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
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
import java.util.Collections;
import java.util.List;

public class ResourcesFragment extends Fragment {

    private ResourcesViewModel resourcesViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        resourcesViewModel = new ViewModelProvider(this).get(ResourcesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_resources, container, false);

        //retreive sharedPreferences data
        String PREFERENCES = "MyPrefs";
        SharedPreferences sharedpreferences = root.getContext().getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);

        //define favorites filename
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

        TextView view1 = (TextView)root.findViewById(R.id.tv1);
        TextView view2 = (TextView)root.findViewById(R.id.tv2);
        TextView view3 = (TextView)root.findViewById(R.id.tv3);
        TextView view4 = (TextView)root.findViewById(R.id.tv4);
        TextView view5 = (TextView)root.findViewById(R.id.tv5);
        TextView view6 = (TextView)root.findViewById(R.id.tv6);
        TextView view7 = (TextView)root.findViewById(R.id.tv7);
        TextView view8 = (TextView)root.findViewById(R.id.tv8);
        TextView view9 = (TextView)root.findViewById(R.id.tv9);
        TextView view10 = (TextView)root.findViewById(R.id.tv10);
        TextView view11 = (TextView)root.findViewById(R.id.tv11);
        TextView view12 = (TextView)root.findViewById(R.id.tv12);
        TextView view13 = (TextView)root.findViewById(R.id.tv13);
        TextView view14 = (TextView)root.findViewById(R.id.tv14);
        TextView view15 = (TextView)root.findViewById(R.id.tv15);

        //collect elements into an array
        CheckBox[] allBoxes = {box1, box2, box3, box4, box5, box6, box7, box8, box9, box10, box11, box12, box13, box14, box15};
        TextView[] allTViews = {view1, view2, view3, view4, view5, view6, view7, view8, view9, view10, view11, view12, view13, view14, view15};

        //retrieve strings.xml elements
        int [] allStringsPermanent = {R.string.AFSP, R.string.SAMHSA, R.string.NIMH, R.string.Healthline, R.string.SPL,
                                        R.string.NAMI, R.string.CDC, R.string.SPTS, R.string.NAASP, R.string.SPRC,
                                        R.string.CTL, R.string.BT1T, R.string.SAVE, R.string.TTP, R.string.ZS};

        //put into array
        ArrayList<Integer> allStrings = new ArrayList<Integer>();
        for(int item: allStringsPermanent){
            allStrings.add(item);
        }

        //prepare saved favorites list
        ArrayList<String> itemTags = new ArrayList<String>();

        try{
            //find file in data
            FileInputStream getFile = root.getContext().openFileInput(filename);

            //read file and create string
            int read = -1;
            StringBuffer buffer = new StringBuffer();
            while ((read = getFile.read()) != -1){
                buffer.append((char)read);
            }

            String fileText = buffer.toString();

            //delimit individual favorites
            String [] tempItems = fileText.split(",");

            //add items to list
            for(String item: tempItems){
                itemTags.add(item);
            }

            getFile.close();
        }
        //catch errors in file reading
        catch(IOException e){
            Toast.makeText(root.getContext(), "Error getting file!", Toast.LENGTH_LONG).show();
        }

        //remove blank entries formed
        while(itemTags.contains("")){
            itemTags.remove("");
        }

        for (int x = 0; x < 15; x++){

            //start with favorited items
            if(!itemTags.isEmpty()){
                //retrieve last favorited index
                int index = Integer.parseInt(itemTags.get(itemTags.size() - 1));

                //display link and check favorite
                allTViews[x].setText(allStrings.get(index));
                allBoxes[x].setChecked(true);

                //remove items from lists
                allStrings.remove(index);
                itemTags.remove(itemTags.size() - 1);
            }

            //use remaining items in allStrings list
            else {
                allTViews[x].setText(allStrings.get(0));
                allStrings.remove(0);
            }

            //activate TextView link
            allTViews[x].setMovementMethod(LinkMovementMethod.getInstance());
        }

        //find saveFavorites button
        Button saveFavorites = (Button) root.findViewById(R.id.saveFav);

        saveFavorites.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //prepare int list and string list of favorites
                ArrayList<Integer> favNums = new ArrayList<Integer>();
                String favNames = "";

                //check for every declared box
                for(int y = 0; y < 15; y++){
                    if (allBoxes[y].isChecked()){
                        //get string from associated TextView
                        String linkAddress = allTViews[y].getText().toString();

                        //determine corresponding number
                        for(int i = 0; i < 15; i++){
                            if (linkAddress.equals(getString(allStringsPermanent[i]))){
                                favNums.add(i);
                                break;
                            }
                        }
                    }
                }

                //sort integer list
                Collections.sort(favNums);

                //add sorted list to string list
                for(int numID: favNums){
                    favNames = favNames + Integer.toString(numID) + ",";
                }

                try {
                    //get file for user's favorites
                    FileOutputStream favFileWrite = root.getContext().openFileOutput(filename, Context.MODE_PRIVATE);

                    //write data to file
                    byte[] byteArray = favNames.getBytes();
                    favFileWrite.write(byteArray);
                    favFileWrite.close();

                    //inform user of success
                    Toast.makeText(root.getContext(), "File change success!", Toast.LENGTH_LONG).show();
                }
                catch(IOException e){
                    //inform user of save failure
                    Toast.makeText(root.getContext(), "File change not possible.", Toast.LENGTH_LONG).show();
                }
            }
        });

        return root;
    }
}
