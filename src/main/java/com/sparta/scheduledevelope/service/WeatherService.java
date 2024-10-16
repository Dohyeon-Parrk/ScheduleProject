package com.sparta.scheduledevelope.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;

@Service
public class WeatherService {

    private final RestTemplate restTemplate;

    public WeatherService() {
        this.restTemplate = new RestTemplate();
    }
}
