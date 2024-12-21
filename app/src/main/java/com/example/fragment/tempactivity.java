package com.example.fragment;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class tempactivity extends AppCompatActivity implements SensorEventListener {

    private TextView pressure, light;
    private Button saveButton;
    private float currentLightValue = 0f;
    private float currentProximityValue = 0f;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tempactivity);

        pressure = findViewById(R.id.app);
        light = findViewById(R.id.light);
        saveButton = findViewById(R.id.button);

        // Initialize Firebase Database Reference
        databaseReference = FirebaseDatabase.getInstance().getReference("sensordata");

        // Initialize Sensor Manager and Sensors
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        if (sensorManager != null) {
            // Register Proximity Sensor
            Sensor proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
            if (proximitySensor != null) {
                sensorManager.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
            } else {
                pressure.setText("Proximity sensor not available");
            }

            // Register Light Sensor
            Sensor lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
            if (lightSensor != null) {
                sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
            } else {
                light.setText("Light sensor not available");
            }
        }

        saveButton.setOnClickListener(v -> saveSensorDataToFirebase());

        // Apply Edge-to-Edge insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
            currentLightValue = event.values[0];
            light.setText("Light: " + currentLightValue);
        }
        if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
            currentProximityValue = event.values[0];
            pressure.setText("Proximity: " + currentProximityValue);
        }
    }

    private void saveSensorDataToFirebase() {
        if (databaseReference != null) {
            // Use a unique key (e.g., timestamp) for each data entry
            String key = String.valueOf(System.currentTimeMillis());

            SensorData sensorData = new SensorData(currentLightValue, currentProximityValue);
            databaseReference.child(key).setValue(sensorData)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(tempactivity.this, "Sensor data saved to Firebase", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(tempactivity.this, "Data saving failed. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(tempactivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        } else {
            Toast.makeText(this, "Firebase Database not initialized", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Handle sensor accuracy changes if needed
    }
}
