package edu.udb.desafio3.model

data class Weather(
    val location: Location,
    val current: CurrentWeather
)

data class Location(
    val name: String,
    val country: String
)

data class CurrentWeather(
    val temp_c: Double,
    val temp_f: Double,
    val condition: Condition,
    val wind_kph: Double,
    val wind_mph: Double,
    val humidity: Int,
    val feelslike_c: Double
)

data class Condition(
    val text: String,
    val icon: String
)