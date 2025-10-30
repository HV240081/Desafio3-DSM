package edu.udb.desafio3.model

import java.io.Serializable

data class Country(
    val name: Name,
    val capital: List<String>?,
    val region: String,
    val subregion: String?,
    val population: Long,
    val flags: Flags,
    val latlng: List<Double>?,
    val currencies: Map<String, Currency>?,
    val languages: Map<String, String>?,
    val cca2: String
) : Serializable

data class Name(
    val common: String,
    val official: String
) : Serializable

data class Flags(
    val png: String,
    val svg: String
) : Serializable

data class Currency(
    val name: String,
    val symbol: String?
) : Serializable