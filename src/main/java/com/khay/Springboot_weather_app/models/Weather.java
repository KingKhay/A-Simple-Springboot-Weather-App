package com.khay.Springboot_weather_app.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Weather implements Serializable {

    private String description;
    private float lat;
    private float lon;

    public Weather() {
    }

    public Weather(String description, float lat, float lon) {
        this.description = description;
        this.lat = lat;
        this.lon = lon;
    }

    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    public float getLat() {
        return lat;
    }

    @JsonProperty("lat")
    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLon() {
        return lon;
    }
    @JsonProperty("lon")
    public void setLon(float lon) {
        this.lon = lon;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "description='" + description + '\'' +
                ", lat=" + lat +
                ", lon=" + lon +
                '}';
    }
}
