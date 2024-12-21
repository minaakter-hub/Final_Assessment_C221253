package com.example.fragment;

public class SensorData {
    private float lightValue;
    private float proximityValue;

    // Default constructor required for Firebase
    public SensorData() {
    }

    public SensorData(float lightValue, float proximityValue) {
        this.lightValue = lightValue;
        this.proximityValue = proximityValue;
    }

    public float getLightValue() {
        return lightValue;
    }

    public void setLightValue(float lightValue) {
        this.lightValue = lightValue;
    }

    public float getProximityValue() {
        return proximityValue;
    }

    public void setProximityValue(float proximityValue) {
        this.proximityValue = proximityValue;
    }
}
