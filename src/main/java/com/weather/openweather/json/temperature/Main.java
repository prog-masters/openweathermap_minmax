package com.weather.openweather.json.temperature;

public class Main {
    private float temp;

    public float getTemp() {
        return temp;
    }

    public void setTemp(float temp) {
        this.temp = temp;
    }

    @Override
    public String toString() {
        return "temp=" + temp + ';';
    }
}