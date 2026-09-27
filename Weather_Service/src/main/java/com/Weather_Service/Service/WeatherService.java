package com.Weather_Service.Service;

import com.Weather_Service.Client.FarmClient;
import com.Weather_Service.Client.WeatherClient;
import com.Weather_Service.Dto.*;
import com.Weather_Service.Entity.WeatherEntity;
import com.Weather_Service.Enums.WeatherCondition;
import com.Weather_Service.Mapper.WeatherMapper;
import com.Weather_Service.Repository.WeatherRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class WeatherService {

    private final WeatherRepo weatherRepo;
    private final FarmClient farmClient;
    private final WeatherMapper weatherMapper;
    private final WeatherClient weatherClient;

    public WeatherResponse getCurrentWeather(Long farmId) {

        FarmLocationResponse farm =
                farmClient.getFarmLocation(farmId);

        OpenMeteoCurrentResponse response =
                weatherClient.getCurrentWeather(
                        farm.latitude(),
                        farm.longitude(),
                        "temperature_2m,"
                                + "relative_humidity_2m,"
                                + "precipitation,"
                                + "wind_speed_10m,"
                                + "weather_code"
                );

        OpenMeteoCurrentResponse.Current current =
                response.current();

        WeatherEntity weather =
                new WeatherEntity();

        weather.setFarmId(farmId);
        weather.setTemperature(current.temperature());
        weather.setHumidity(current.humidity());
        weather.setRainProbability(current.precipitation());
        weather.setCondition(
                mapWeatherCondition(current.weatherCode())
        );
        weather.setWindSpeed(current.windSpeed());
        weather.setWeatherDate(LocalDateTime.now());

        WeatherEntity saved =
                weatherRepo.save(weather);

        return weatherMapper.toResponse(saved);
    }

    public List<WeatherForeCastResponse> getForecast(
            Long farmId) {

        FarmLocationResponse farm =
                farmClient.getFarmLocation(farmId);

        OpenMeteoResponse response =
                weatherClient.getForecast(
                        farm.latitude(),
                        farm.longitude(),
                        "temperature_2m,"
                                + "relative_humidity_2m,"
                                + "precipitation_probability,"
                                + "wind_speed_10m,"
                                + "weather_code",
                        3
                );

        OpenMeteoResponse.Hourly hourly =
                response.hourly();

        return IntStream.range(
                        0,
                        hourly.time().size()
                )
                .mapToObj(i ->
                        new WeatherForeCastResponse(
                                farmId,
                                hourly.temperature().get(i),
                                hourly.humidity().get(i),
                                hourly.rainProbability().get(i),
                                mapWeatherCondition(
                                        hourly.weatherCode().get(i)
                                ),
                                hourly.windSpeed().get(i),
                                LocalDateTime.parse(
                                        hourly.time().get(i)
                                )
                        )
                )
                .toList();
    }

    public List<WeatherResponse> getWeatherByFarm(
            Long farmId) {

        farmClient.getFarmLocation(farmId);

        return weatherRepo
                .findByFarmIdOrderByWeatherDateDesc(farmId)
                .stream()
                .map(weatherMapper::toResponse)
                .toList();
    }

    public WeatherResponse refreshWeather(
            Long farmId) {

        FarmLocationResponse farm =
                farmClient.getFarmLocation(farmId);

        OpenMeteoCurrentResponse response =
                weatherClient.getCurrentWeather(
                        farm.latitude(),
                        farm.longitude(),
                        "temperature_2m,"
                                + "relative_humidity_2m,"
                                + "precipitation,"
                                + "wind_speed_10m,"
                                + "weather_code"
                );

        OpenMeteoCurrentResponse.Current current =
                response.current();

        WeatherEntity weather =
                new WeatherEntity();

        weather.setFarmId(farmId);
        weather.setTemperature(current.temperature());
        weather.setHumidity(current.humidity());
        weather.setRainProbability(current.precipitation());
        weather.setCondition(
                mapWeatherCondition(current.weatherCode())
        );
        weather.setWindSpeed(current.windSpeed());
        weather.setWeatherDate(LocalDateTime.now());

        WeatherEntity saved =
                weatherRepo.save(weather);

        return weatherMapper.toResponse(saved);
    }

    public WeatherResponse storeWeather(
            WeatherRequest request) {

        farmClient.getFarmLocation(
                request.farmId()
        );

        WeatherEntity weather =
                weatherMapper.toEntity(request);

        WeatherEntity saved =
                weatherRepo.save(weather);

        return weatherMapper.toResponse(saved);
    }

    public List<WeatherResponse> getPreviousWeather(
            Long farmId) {

        farmClient.getFarmLocation(farmId);

        return weatherRepo
                .findByFarmIdOrderByWeatherDateDesc(farmId)
                .stream()
                .map(weatherMapper::toResponse)
                .toList();
    }

    private WeatherCondition mapWeatherCondition(
            Integer code) {

        return switch (code) {
            case 0 ->
                    WeatherCondition.SUNNY;

            case 1, 2 ->
                    WeatherCondition.PARTLY_CLOUDY;

            case 3 ->
                    WeatherCondition.CLOUDY;

            case 45, 48 ->
                    WeatherCondition.FOG;

            case 51, 53, 55,
                 56, 57,
                 61, 63,
                 66, 67,
                 80, 81 ->
                    WeatherCondition.RAIN;

            case 65, 82 ->
                    WeatherCondition.HEAVY_RAIN;

            case 71, 73, 75, 77 ->
                    WeatherCondition.CLOUDY;

            case 95 ->
                    WeatherCondition.THUNDERSTORM;

            case 96, 99 ->
                    WeatherCondition.STORM;

            default ->
                    WeatherCondition.CLOUDY;
        };
    }
}