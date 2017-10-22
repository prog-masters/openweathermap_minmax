package com.weather;

import com.weather.collector.MinMaxValues;
import com.weather.collector.MultiThreadCollector;
import com.weather.collector.SingleThreadCollector;
import com.weather.httpserver.MinMaxHttpServer;
import com.weather.openweather.CityReaderFromJson;
import com.weather.openweather.json.city.City;

import java.util.List;

/**
 * Created by Szabolcs Filep in 22 October 2017.
 */
class MinMaxWeatherApplication {

    private List<City> hungarianCities;

    private MinMaxValues minMaxValues = new MinMaxValues();

    public static void main(String[] args) {
        MinMaxWeatherApplication app = new MinMaxWeatherApplication();

        app.start();
    }

    private void start() {

        MinMaxHttpServer minMaxHttpServer = new MinMaxHttpServer();
        minMaxHttpServer.start(minMaxValues);

        System.out.println("Start reading cities from file...");
        hungarianCities = new CityReaderFromJson().readCitiesForCountry("hu");

        collectInSingleThread();

        collectInMultiThread();

    }

    private void collectInSingleThread() {
        System.out.println("\n----------------------------------------------");
        System.out.println(" Start requesting temperatures one by one...");
        System.out.println("----------------------------------------------");
        long messaureTime = System.currentTimeMillis();

        new SingleThreadCollector().checkAndCollectTemperatures(hungarianCities, minMaxValues);

        System.out.println("Finished in " + (System.currentTimeMillis() - messaureTime) + "ms.");
    }

    private void collectInMultiThread() {
        System.out.println("\n-------------------------------------------------------------");
        System.out.println(" Start requesting temperatures parallely (" + MultiThreadCollector.PARALLEL_THREADS + " at once)...");
        System.out.println("-------------------------------------------------------------");
        long messaureTime = System.currentTimeMillis();

        new MultiThreadCollector().checkAndCollectTemperatures(hungarianCities, minMaxValues);

        System.out.println("Finished in " + (System.currentTimeMillis() - messaureTime) + "ms.");
    }

}