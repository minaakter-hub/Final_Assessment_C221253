package com.example.fragment;
public class SensorData1 {
    private float lightValue;
    private float proximityValue;

    // Default constructor for Firebase
    public SensorData1() {
    }

    public SensorData1(float lightValue, float proximityValue) {
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
