package com.weather.openweather;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.weather.openweather.json.city.City;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CityReaderFromJson {

    private final String fileName = "json_files/current.city.list.json";

    public List<City> readCitiesForCountry(String country) {

        Gson gson = new Gson();

        try (JsonReader jsonReader = gson.newJsonReader(Files.newBufferedReader(Paths.get((fileName))))) {

            List<City> cities = new ArrayList<>();

            jsonReader.beginArray();
            int cityCount = 0;
            while (jsonReader.peek() != JsonToken.END_ARRAY) {
                City city = gson.fromJson(jsonReader, City.class);
                cityCount++;
                if (country.equals(city.getCountry().toLowerCase().trim())) {
                    cities.add(city);
                }
            }
            jsonReader.endArray();

            System.out.println(cities.size() + " cities were found for '" + country + "' country out of " + cityCount + " cities.");

            return cities;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
