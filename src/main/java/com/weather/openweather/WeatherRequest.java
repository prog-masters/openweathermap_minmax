package com.weather.openweather;

import com.google.gson.Gson;
import com.weather.openweather.json.city.City;
import com.weather.openweather.json.temperature.Weather;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;

import java.io.ByteArrayOutputStream;
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
            String url = generateUrl();

            long startTime = System.currentTimeMillis();

            Response response = Request.Get(url).execute();
            HttpResponse httpResponse = response.returnResponse();
            int httpStatusCode = httpResponse.getStatusLine().getStatusCode();
            switch (httpStatusCode) {
                case 200:
                    return processResponseNormally(startTime, httpResponse);
                case 429:
                    return printErrorAndExit("Too many request to OpenWeatherMap api! You have to wait or register another api key.");
                default:
                    return printErrorAndExit("OpenWeatherMap api returned " + httpStatusCode + " http code. ");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    private String generateUrl() {
        String url;
        url = URL.replace("[API_KEY]", API_KEY);
        url = url.replace("[CITY_ID]", city.getId());
        return url;
    }

    private Weather processResponseNormally(long startTime, HttpResponse httpResponse) throws IOException {
        ByteArrayOutputStream outstream = new ByteArrayOutputStream();
        httpResponse.getEntity().writeTo(outstream);
        String responseBodyContent = new String(outstream.toByteArray());
        System.out.println(responseBodyContent);

        long finishedTime = System.currentTimeMillis();

        Weather weather = new Gson().fromJson(responseBodyContent, Weather.class);

        System.out.println(weather + " Response time: " + (finishedTime - startTime) + "ms. ");
        return weather;
    }

    private Weather printErrorAndExit(String x) {
        System.out.println(x);
        System.exit(-1);
        return null;
    }

}
