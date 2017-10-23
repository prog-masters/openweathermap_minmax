package com.weather.collector;

import com.weather.openweather.json.city.City;

import java.util.List;

public class SingleThreadCollector implements TemperatureCollector {

    @Override
    public void checkAndCollectTemperatures(List<City> cities, MinMaxValues minMaxToRefresh) {

        for (City city : cities) {
            new TemperatureCollectorTask(city, minMaxToRefresh).run();
        }
        System.out.println("RESULT: \n" + minMaxToRefresh);

    }
}
