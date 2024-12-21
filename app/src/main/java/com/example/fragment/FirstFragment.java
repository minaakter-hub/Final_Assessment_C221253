package com.example.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class FirstFragment extends Fragment {

TextView step,temp,camera,location;
FloatingActionButton fab;
    public FirstFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_first, container, false);
        step=view.findViewById(R.id.data);
        temp=view.findViewById(R.id.direction);
        camera=view.findViewById(R.id.camera);
        location=view.findViewById(R.id.loaction);
        fab=view.findViewById(R.id.floating_action_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Add button Selected",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(),form.class));
            }
        });
        SharedPreferences sharedPreferences2 = requireActivity().getSharedPreferences("ProfileData", Context.MODE_PRIVATE); // Use requireActivity()
        String profileImageUriString = sharedPreferences2.getString("profileImageUri", null);
        // Set image to ImageView
        if (profileImageUriString != null && !profileImageUriString.isEmpty()) {
            try {
                Uri profileImageUri = Uri.parse(profileImageUriString);
                ImageView profileImageView = view.findViewById(R.id.img);
                profileImageView.setImageURI(profileImageUri);
            } catch (Exception e) {
                // Handle exception, e.g., display default image or error message
                ImageView profileImageView = view.findViewById(R.id.img);
                profileImageView.setImageResource(R.drawable.camera); // Default image
                Log.d("Dashboard", "Error loading profile image: " + e.getMessage());
            }
        } else {
            ImageView profileImageView = view.findViewById(R.id.img);
            profileImageView.setImageResource(R.drawable.camera); // Default image
        }
        temp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),tempactivity.class));
            }
        });
        step.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),stepactivity.class));
            }
        });
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),camera.class));
            }
        });
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),mapactivity.class));
            }
        });



        return view;
    }
}