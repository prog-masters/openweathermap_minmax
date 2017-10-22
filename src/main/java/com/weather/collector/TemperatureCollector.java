package com.weather.collector;

import com.weather.openweather.json.city.City;

import java.util.List;

public interface TemperatureCollector {

    void checkAndCollectTemperatures(List<City> cities, MinMaxValues minMaxToRefresh);
}
