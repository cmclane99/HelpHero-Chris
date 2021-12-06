package com.example.helphero.ui.sos;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.helphero.R;

public class SOSFragment extends Fragment {

    private SOSViewModel SOSViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SOSViewModel = new ViewModelProvider(this).get(SOSViewModel.class);
        View root = inflater.inflate(R.layout.fragment_sos, container, false);

        // buttonCall = findViewById(R.id.AutoDIal911);
        Button buttonCall = (Button)root.findViewById(R.id.AutoDial911);

        buttonCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:911"));
                startActivity(intent);
            }
        });
        
        return root;
    }

}