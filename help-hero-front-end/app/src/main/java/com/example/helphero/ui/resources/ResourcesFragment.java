package com.example.helphero.ui.resources;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.helphero.R;

public class ResourcesFragment extends Fragment {

    private ResourcesViewModel resourcesViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        resourcesViewModel = new ViewModelProvider(this).get(ResourcesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_resources, container, false);

        TextView linkDisplay = (TextView)root.findViewById(R.id.ResourceList);
        linkDisplay.setMovementMethod(LinkMovementMethod.getInstance());

        return root;
    }
}
