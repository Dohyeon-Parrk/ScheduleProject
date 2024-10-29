package com.sparta.scheduledevelope.external.api.weather.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.scheduledevelope.external.api.weather.client.WeatherClient;
import com.sparta.scheduledevelope.external.api.weather.dto.WeatherResponseDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class WeatherController {

	private final WeatherClient weatherClient;

	@GetMapping("/api/weather")
	public ResponseEntity<List<WeatherResponseDto>> getWeather(){
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(weatherClient.getWeather());
	}
}
