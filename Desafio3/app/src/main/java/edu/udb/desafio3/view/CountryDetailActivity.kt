package edu.udb.desafio3.view

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import edu.udb.desafio3.R
import edu.udb.desafio3.controller.WeatherController
import edu.udb.desafio3.model.Country
import edu.udb.desafio3.model.Weather
import edu.udb.desafio3.model.WeatherRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CountryDetailActivity : AppCompatActivity() {

    private lateinit var nombrePais: TextView
    private lateinit var capitalPais: TextView
    private lateinit var regionPais: TextView
    private lateinit var poblacionPais: TextView
    private lateinit var monedaPais: TextView
    private lateinit var idiomaPais: TextView
    private lateinit var temperaturaClima: TextView
    private lateinit var condicionClima: TextView
    private lateinit var vientoClima: TextView
    private lateinit var humedadClima: TextView
    private lateinit var imageWeatherIcon: ImageView
    private lateinit var imageCountryFlag: ImageView
    private lateinit var progressBar: ProgressBar

    private val weatherController = WeatherController(WeatherRepository())

    private lateinit var pais: Country

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_country_detail)

        // SOLUCIÓN COMPATIBLE CON API 24
        pais = obtenerCountryDesdeIntent()

        configurarVistas()
        cargarDatosPais()
        cargarDatosClima()
    }

    @Suppress("DEPRECATION")
    private fun obtenerCountryDesdeIntent(): Country {
        return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra("COUNTRY", Country::class.java)
        } else {
            intent.getSerializableExtra("COUNTRY")
        } as? Country ?: throw IllegalStateException("Country data not found or invalid")
    }

    private fun configurarVistas() {
        nombrePais = findViewById(R.id.textCountryName)
        capitalPais = findViewById(R.id.textCapital)
        regionPais = findViewById(R.id.textRegion)
        poblacionPais = findViewById(R.id.textPopulation)
        monedaPais = findViewById(R.id.textCurrency)
        idiomaPais = findViewById(R.id.textLanguage)
        temperaturaClima = findViewById(R.id.textTemperature)
        condicionClima = findViewById(R.id.textCondition)
        vientoClima = findViewById(R.id.textWind)
        humedadClima = findViewById(R.id.textHumidity)
        imageWeatherIcon = findViewById(R.id.imageWeatherIcon)
        imageCountryFlag = findViewById(R.id.imageCountryFlag)
        progressBar = findViewById(R.id.progressBar)
    }

    private fun cargarDatosPais() {
        Picasso.get()
            .load(pais.flags.png)
            .resize(120, 80)
            .centerInside()
            .into(imageCountryFlag)

        nombrePais.text = "${pais.name.official} (${pais.name.common})"
        capitalPais.text = "${getString(R.string.label_capital)} ${pais.capital?.firstOrNull() ?: getString(R.string.no_capital)}"
        regionPais.text = "${getString(R.string.label_region)} ${pais.region} - ${pais.subregion ?: getString(R.string.no_region)}"
        poblacionPais.text = "${getString(R.string.label_population)} ${pais.population}"

        val monedas = pais.currencies?.values?.joinToString {
            "${it.name} (${it.symbol ?: getString(R.string.no_symbol)})"
        } ?: getString(R.string.no_currency)
        monedaPais.text = "${getString(R.string.label_currency)} $monedas"

        val idiomas = pais.languages?.values?.joinToString() ?: getString(R.string.no_language)
        idiomaPais.text = "${getString(R.string.label_language)} $idiomas"
    }

    private fun cargarDatosClima() {
        progressBar.visibility = ProgressBar.VISIBLE

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val capital = pais.capital?.firstOrNull()
                if (capital != null) {
                    val clima = withContext(Dispatchers.IO) {
                        weatherController.obtenerClimaParaCapital(capital)
                    }
                    actualizarUIClima(clima)
                } else {
                    mostrarError(getString(R.string.error_no_capital_weather))
                }
            } catch (e: Exception) {
                mostrarError("${getString(R.string.error_loading_weather)}: ${e.message}")
            } finally {
                progressBar.visibility = ProgressBar.GONE
            }
        }
    }

    private fun actualizarUIClima(clima: Weather) {
        temperaturaClima.text = "${getString(R.string.label_temperature)} ${clima.current.temp_c}°C (${clima.current.temp_f}°F)"
        condicionClima.text = "${getString(R.string.label_condition)} ${clima.current.condition.text}"
        vientoClima.text = "${getString(R.string.label_wind)} ${clima.current.wind_kph} kph"
        humedadClima.text = "${getString(R.string.label_humidity)} ${clima.current.humidity}%"

        val iconUrl = "https:${clima.current.condition.icon}"
        Picasso.get()
            .load(iconUrl)
            .into(imageWeatherIcon)
    }

    private fun mostrarError(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }
}