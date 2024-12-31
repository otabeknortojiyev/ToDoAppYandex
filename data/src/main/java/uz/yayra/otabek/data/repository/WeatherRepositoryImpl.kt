package uz.yayra.otabek.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import uz.yayra.otabek.common.weather.Coordinates4Response
import uz.yayra.otabek.common.weather.CoordinatesResponse
import uz.yayra.otabek.data.local.LocalStorage
import uz.yayra.otabek.data.network.WeatherApi
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(private val localStorage: LocalStorage, private val api: WeatherApi) :
  WeatherRepository {
  override suspend fun getCurrent(
    lat: String,
    lon: String,
  ): Result<CoordinatesResponse> = withContext(Dispatchers.IO) {
    val response = api.current(lat, lon, localStorage.appid) // ответ с сервера
    if (response.isSuccessful && response.body() != null) {
      return@withContext Result.success(response.body()) as Result<CoordinatesResponse>
    } else if (response.errorBody() != null) {
      return@withContext Result.failure(Exception("Ошибка с сервера"))
    } else {
      return@withContext Result.failure(Throwable(response.message()))
    }
  }

  override suspend fun getForecast(
    lat: String,
    lon: String
  ): Result<Coordinates4Response> = withContext(Dispatchers.IO) {
    val response = api.forecast(lat, lon, localStorage.appid)
    if (response.isSuccessful && response.body() != null) {
      return@withContext Result.success(response.body()) as Result<Coordinates4Response>
    } else if (response.errorBody() != null) {
      return@withContext Result.failure(Exception("Ошибка с сервера"))
    } else {
      return@withContext Result.failure(Throwable(response.message()))
    }
  }


  override fun getTheme(): Boolean = localStorage.isDark


}