package com.weather.collector;

import com.weather.openweather.WeatherRequest;
import com.weather.openweather.json.city.City;
import com.weather.openweather.json.temperature.Weather;

public class TempMinMaxCollectorTask implements Runnable {

    private final City city;
    private final MinMaxValues minMax;

    public TempMinMaxCollectorTask(City city, MinMaxValues minMax) {
        this.city = city;
        this.minMax = minMax;
    }

    @Override
    public void run() {
        Weather w = new WeatherRequest(city).getResponse();
        Float thisCityTemp = w.getMain().getTemp();

        isMinOrMax(w, thisCityTemp);
    }

    private synchronized void isMinOrMax(Weather w, Float thisCityTemp) {
        if (minMax.getMinTempWeather() == null) {
            minMax.setMinTempWeather(w);
        }
        if (minMax.getMaxTempWeather() == null) {
            minMax.setMaxTempWeather(w);
        }

        Float minTemp = minMax.getMinTempWeather().getMain().getTemp();
        Float maxTemp = minMax.getMaxTempWeather().getMain().getTemp();

        if (thisCityTemp.compareTo(minTemp) < 0) {
            minMax.setMinTempWeather(w);
            System.out.println("New MINIMUM temperature!!!");
        }

        if (thisCityTemp.compareTo(maxTemp)> 0) {
            minMax.setMaxTempWeather(w);
            System.out.println("New MAXIMUM temperature!!!");
        }
    }

}
