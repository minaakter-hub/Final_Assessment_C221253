package com.example.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class SecondFragment extends Fragment {
    FloatingActionButton fab;
    private SensorDataAdapter adapter;
    private final List<SensorData1> sensorDataList = new ArrayList<>();

    public SecondFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_second, container, false);
        RecyclerView recyclerView=view.findViewById(R.id.savedata);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new SensorDataAdapter(sensorDataList);
        recyclerView.setAdapter(adapter);
//        fab=view.findViewById(R.id.floating_action_button);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getActivity(),"Add button Selected",Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(getActivity(),form.class));
//            }
//        });
        loadSensorData();

        return view;
    }
    private void loadSensorData() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("sensordata");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                sensorDataList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    SensorData1 data = dataSnapshot.getValue(SensorData1.class);
                    if (data != null) {
                        sensorDataList.add(data);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
            }
        });
    }
}