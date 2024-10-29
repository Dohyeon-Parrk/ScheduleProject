package com.sparta.scheduledevelope.domain.schedule.service;

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
        this.restTemplate = new RestTemplate(); // RestTemplate 인스턴스 생성
    }

    public String getTodayWeather() {
        URI uri = UriComponentsBuilder.fromUriString("https://f-api.github.io/f-api/weather.json")
                .build()
                .toUri();

        try {
            // RestTemplate을 사용하여 외부 API 호출
            Map<String, Object> response = restTemplate.getForObject(uri, Map.class);

            Map<String, String> todayWeather = (Map<String, String>) response.get("todayWeather");
            return todayWeather != null ? todayWeather.get("summary") : "날씨 정보 없음";

        } catch (Exception e) {
            System.err.println("외부 API 호출 실패: " + e.getMessage());
            return "날씨 정보를 가져올 수 없습니다.";
        }
    }
}
