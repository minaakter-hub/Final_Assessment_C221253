package com.example.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class form extends AppCompatActivity {

    private EditText lightEditText, proximityEditText,light1;
    private Button updateButton, deleteButton;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_form);

        // Initialize views
        lightEditText = findViewById(R.id.light);
        light1=findViewById(R.id.light1);
        proximityEditText = findViewById(R.id.proximity);
        updateButton = findViewById(R.id.updateButton);
        deleteButton = findViewById(R.id.deleteButton);

        // Initialize Firebase reference
        databaseReference = FirebaseDatabase.getInstance().getReference("sensordata");

        // Set update button listener
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateSensorData();
            }
        });

        // Set delete button listener
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteSensorData();
            }
        });

        // Adjust layout for system UI insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void updateSensorData() {
        // Get input values
        String lightValue = lightEditText.getText().toString();
        String proximityValue = proximityEditText.getText().toString();

        // Validate inputs
        if (lightValue.isEmpty() || proximityValue.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            // Fetch all nodes and find a matching `lightValue` to update
            databaseReference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (task.isSuccessful() && task.getResult() != null) {
                        boolean isUpdated = false;
                        for (DataSnapshot snapshot : task.getResult().getChildren()) {
                            String dbLightValue = String.valueOf(snapshot.child("lightValue").getValue());
                            if (dbLightValue.equals(lightValue)) {
                                Map<String, Object> updatedData = new HashMap<>();
                                updatedData.put("lightValue", Float.parseFloat(lightValue));
                                updatedData.put("proximityValue", Float.parseFloat(proximityValue));
                                snapshot.getRef().updateChildren(updatedData);
                                Toast.makeText(form.this, "Sensor data updated successfully", Toast.LENGTH_SHORT).show();
                                lightEditText.setText("");
                                proximityEditText.setText("");
                                isUpdated = true;
                                break;
                            }
                        }
                        if (!isUpdated) {
                            Toast.makeText(form.this, "No matching light value found. Adding new data.", Toast.LENGTH_SHORT).show();
                            addNewSensorData(Float.parseFloat(lightValue), Float.parseFloat(proximityValue));
                        }
                    } else {
                        Toast.makeText(form.this, "Failed to retrieve data for update", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid numeric input", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteSensorData() {
        // Get light value to delete
        String lightValueInput = light1.getText().toString();
        Log.d("what",lightValueInput);
        // Validate input
        if (lightValueInput.isEmpty()) {
            Toast.makeText(this, "Please enter the light value to delete", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            // Convert input to float
            float lightValueToDelete = Float.parseFloat(lightValueInput);

            // Retrieve and delete matching data
            databaseReference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (task.isSuccessful() && task.getResult() != null) {
                        boolean isDeleted = false;
                        for (DataSnapshot snapshot : task.getResult().getChildren()) {
                            Float dbLightValue = snapshot.child("lightValue").getValue(Float.class);

                            // Compare float values
                            if (dbLightValue != null && dbLightValue.equals(lightValueToDelete)) {
                                snapshot.getRef().removeValue();
                                Toast.makeText(form.this, "Sensor data deleted successfully", Toast.LENGTH_SHORT).show();
                                lightEditText.setText("");
                                proximityEditText.setText("");
                                isDeleted = true;
                                break;
                            }
                        }

                        if (!isDeleted) {
                            Toast.makeText(form.this, "No matching light value found to delete", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(form.this, "Failed to retrieve data for deletion", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid input. Please enter a valid number.", Toast.LENGTH_SHORT).show();
        }
    }

    private void addNewSensorData(float lightValue, float proximityValue) {
        // Add new data
        String uniqueKey = databaseReference.push().getKey();
        if (uniqueKey != null) {
            Map<String, Object> sensorData = new HashMap<>();
            sensorData.put("lightValue", lightValue);
            sensorData.put("proximityValue", proximityValue);

            databaseReference.child(uniqueKey).setValue(sensorData)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(form.this, "New sensor data added", Toast.LENGTH_SHORT).show();
                                lightEditText.setText("");
                                proximityEditText.setText("");
                            } else {
                                Toast.makeText(form.this, "Failed to add new sensor data", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}
