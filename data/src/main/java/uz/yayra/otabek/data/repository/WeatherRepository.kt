package uz.yayra.otabek.data.repository

import uz.yayra.otabek.common.weather.Coordinates4Response
import uz.yayra.otabek.common.weather.CoordinatesResponse

interface WeatherRepository {
  suspend fun getCurrent(lat: String, lon: String): Result<CoordinatesResponse>
  suspend fun getForecast(lat: String, lon: String): Result<Coordinates4Response>
  fun getTheme(): Boolean
}