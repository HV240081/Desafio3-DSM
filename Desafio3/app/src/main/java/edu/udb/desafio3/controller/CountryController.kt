package edu.udb.desafio3.controller

import edu.udb.desafio3.model.Country
import edu.udb.desafio3.model.CountryRepository

class CountryController(
    private val countryRepository: CountryRepository
) {

    suspend fun obtenerPaisesPorRegion(region: String): List<Country> {
        return countryRepository.obtenerPaisesPorRegion(region)
    }

    fun obtenerRegiones(): List<String> {
        return listOf("Africa", "America", "Asia", "Europe", "Oceania")
    }
}