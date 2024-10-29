package com.sparta.scheduledevelope.external.api.weather.client;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.sparta.scheduledevelope.external.api.weather.dto.WeatherResponseDto;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class WeatherClient {

	private final RestTemplate restTemplate;

	public List<WeatherResponseDto> getWeather(){
		String url = "https://f-api.github.io/f-api/weather.json";

		ResponseEntity<List<WeatherResponseDto>> response = restTemplate.exchange(
			url,
			HttpMethod.GET,
			null,
			new ParameterizedTypeReference<>() {
			}
		);

		if (!response.getStatusCode().is2xxSuccessful()) {
			System.out.println("Error: " + response.getStatusCode());
		}

		return response.getBody();
	}
}