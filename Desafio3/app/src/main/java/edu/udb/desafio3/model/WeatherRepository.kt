package edu.udb.desafio3.model

import edu.udb.desafio3.network.RetrofitClient
import edu.udb.desafio3.network.WeatherApiService
import edu.udb.desafio3.util.Constants

class WeatherRepository {
    private val weatherApiService: WeatherApiService = RetrofitClient.weatherApiService

    suspend fun obtenerClimaPorCiudad(ciudad: String): Weather {
        return weatherApiService.obtenerClimaActual(
            claveApi = Constants.WEATHER_API_KEY,
            ciudad = ciudad
        )
    }
}