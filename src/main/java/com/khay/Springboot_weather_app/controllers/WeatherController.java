package com.khay.Springboot_weather_app.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.khay.Springboot_weather_app.models.Weather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;


@Controller
public class WeatherController {

    @Value("${api.key}")
    String apikey;

    @Value("${api.host.url}")
    String hostUrl;

    RestTemplate restTemplate;

    @Autowired
    public WeatherController(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    @RequestMapping("/")
    public String index(){
        return "index";
    }

    @RequestMapping("/weather")
    public String selectCountry(){
        return "weather-form";
    }

    @RequestMapping("/weather-details")
     public String mainWeather(Model model,@RequestParam("city") String city) throws JsonProcessingException {

        UriComponents uriComponents = UriComponentsBuilder
                .newInstance()
                .scheme("http")
                .host(hostUrl)
                .query("q={keyword}&appid={appid}")
                .buildAndExpand(city,apikey);

        String url = uriComponents.toUriString();

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET,null,String.class);
        Weather currentWeather = convertWeather(response);
        model.addAttribute("weather",currentWeather);
        return "main-weather";
    }

    public Weather convertWeather(ResponseEntity<String> response) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.getBody());
        float lon = root.path("coord").path("lon").floatValue();
        float lat = root.path("coord").path("lat").floatValue();
        String description = root.path("weather").get(0).path("description").textValue();
        return new Weather(description,lat,lon);
    }
}
