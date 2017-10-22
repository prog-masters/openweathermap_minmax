package com.weather.openweather;

import com.google.gson.Gson;
import com.weather.openweather.json.city.City;
import com.weather.openweather.json.temperature.Weather;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;

import java.io.IOException;

public class WeatherRequest {

    private final static String URL = "http://api.openweathermap.org/data/2.5/weather?id=[CITY_ID]&units=metric&appid=[API_KEY]";
    private final static String API_KEY = "<Your API key comes here>";
    private final City city;

    public WeatherRequest(City city) {
        this.city = city;
    }

    public Weather getResponse() {
        try {
            String url = getUrl();

            long startTime = System.currentTimeMillis();

            Response response = Request.Get(url).execute();
            int httpStatusCode = response.returnResponse().getStatusLine().getStatusCode();
            if (httpStatusCode == 200) {

                Content content = response.returnContent();
                String responseBodyContent = content.toString();
                System.out.println(responseBodyContent);

                long finishedTime = System.currentTimeMillis();

                Weather weather = new Gson().fromJson(responseBodyContent, Weather.class);

                System.out.println(weather + " Response time: " + (finishedTime - startTime) + "ms. ");

                return weather;
            } else if (httpStatusCode == 429) {
                System.out.println("Too many request to OpenWeatherMap api! You have to wait.");
                System.exit(-1);
                return null;
            } else {
                System.out.println("OpenWeatherMap api returned " + httpStatusCode + " http code. ");
                System.exit(-1);
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getUrl() {
        String url = "";
        url = URL.replace("[API_KEY]", API_KEY);
        url = url.replace("[CITY_ID]", city.getId());
        return url;
    }

    ;

}
