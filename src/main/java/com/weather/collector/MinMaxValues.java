package com.weather.collector;


import com.weather.openweather.json.temperature.Weather;

public class MinMaxValues {

    private Weather minTempWeather;
    private Weather maxTempWeather;

    public Weather getMinTempWeather() {
        return minTempWeather;
    }

    public void setMinTempWeather(Weather minTempWeather) {
        this.minTempWeather = minTempWeather;
    }

    public Weather getMaxTempWeather() {
        return maxTempWeather;
    }

    public void setMaxTempWeather(Weather maxTempWeather) {
        this.maxTempWeather = maxTempWeather;
    }

    @Override
    public String toString() {
        return "Minimum: " + minTempWeather + "\n" +
                "Maximum: " + maxTempWeather + "\n";
    }
}
