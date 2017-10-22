package com.weather.openweather.json.temperature;

public class Weather {
    private Main main;
    private long id;
    private String name;

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Weather temperature is " + main.getTemp() + " Celsius in " + name + ".";
    }
}