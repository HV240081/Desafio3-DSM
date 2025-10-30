package edu.udb.desafio3.controller

import edu.udb.desafio3.model.Weather
import edu.udb.desafio3.model.WeatherRepository

class WeatherController(
    private val weatherRepository: WeatherRepository
) {

    suspend fun obtenerClimaParaCapital(capital: String): Weather {
        return weatherRepository.obtenerClimaPorCiudad(capital)
    }
}