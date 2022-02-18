package com.technologies.androidassessment.flight_list

import com.google.gson.Gson
import com.technologies.androidassessment.core.data.entity.Airport
import com.technologies.androidassessment.core.extension.fromJson
import io.reactivex.Single
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.HttpException
import retrofit2.Response

val mockAirports: List<Airport> = Gson().fromJson(
    "[{\"airportCode\":\"AAA\",\"internationalAirport\":false,\"domesticAirport\":false,\"regionalAirport\":false,\"onlineIndicator\":false,\"eticketableAirport\":false,\"location\":{\"aboveSeaLevel\":-99999,\"latitude\":17.25,\"latitudeRadius\":-0.3040,\"longitude\":145.30,\"longitudeRadius\":-2.5395,\"latitudeDirection\":\"S\",\"longitudeDirection\":\"W\"},\"airportName\":\"Anaa\",\"city\":{\"cityCode\":\"AAA\",\"cityName\":\"Anaa\",\"timeZoneName\":\"Pacific/Tahiti\"},\"state\":{},\"country\":{\"countryCode\":\"PF\",\"countryName\":\"French Polynesia\"},\"region\":{\"regionCode\":\"SP\",\"regionName\":\"South Pacific\"}},{\"airportCode\":\"AAB\",\"internationalAirport\":false,\"domesticAirport\":false,\"regionalAirport\":false,\"onlineIndicator\":false,\"eticketableAirport\":false,\"location\":{\"aboveSeaLevel\":-99999,\"latitude\":26.45,\"latitudeRadius\":-0.4669,\"longitude\":141.00,\"longitudeRadius\":2.4609,\"latitudeDirection\":\"S\",\"longitudeDirection\":\"E\"},\"airportName\":\"Arrabury\",\"city\":{\"cityCode\":\"AAB\",\"cityName\":\"Arrabury\",\"timeZoneName\":\"Australia/Brisbane\"},\"state\":{\"stateCode\":\"QLD\",\"stateName\":\"Queensland\"},\"country\":{\"countryCode\":\"AU\",\"countryName\":\"Australia\"},\"region\":{\"regionCode\":\"AU\",\"regionName\":\"Australia\"}},{\"airportCode\":\"AAC\",\"internationalAirport\":false,\"domesticAirport\":false,\"regionalAirport\":false,\"onlineIndicator\":true,\"eticketableAirport\":false,\"location\":{\"aboveSeaLevel\":-99999,\"latitude\":31.10,\"latitudeRadius\":0.5440,\"longitude\":33.50,\"longitudeRadius\":0.5905,\"latitudeDirection\":\"N\",\"longitudeDirection\":\"E\"},\"airportName\":\"Al Arish\",\"city\":{\"cityCode\":\"AAC\",\"cityName\":\"Al Arish\",\"timeZoneName\":\"Africa/Cairo\"},\"state\":{},\"country\":{\"countryCode\":\"EG\",\"countryName\":\"Egypt\"},\"region\":{\"regionCode\":\"AF\",\"regionName\":\"Africa\"}},{\"airportCode\":\"AAE\",\"internationalAirport\":false,\"domesticAirport\":false,\"regionalAirport\":false,\"onlineIndicator\":false,\"eticketableAirport\":false,\"location\":{\"aboveSeaLevel\":16,\"latitude\":36.49,\"latitudeRadius\":0.6426,\"longitude\":7.48,\"longitudeRadius\":0.1361,\"latitudeDirection\":\"N\",\"longitudeDirection\":\"E\"},\"airportName\":\"Annaba\",\"city\":{\"cityCode\":\"AAE\",\"cityName\":\"Annaba\",\"timeZoneName\":\"Africa/Algiers\"},\"state\":{},\"country\":{\"countryCode\":\"DZ\",\"countryName\":\"Algeria\"},\"region\":{\"regionCode\":\"AF\",\"regionName\":\"Africa\"}},{\"airportCode\":\"AAH\",\"internationalAirport\":false,\"domesticAirport\":false,\"regionalAirport\":false,\"onlineIndicator\":false,\"eticketableAirport\":false,\"location\":{\"aboveSeaLevel\":1000,\"latitude\":50.46,\"latitudeRadius\":0.8860,\"longitude\":6.06,\"longitudeRadius\":0.1065,\"latitudeDirection\":\"N\",\"longitudeDirection\":\"E\"},\"airportName\":\"Aachen\",\"city\":{\"cityCode\":\"AAH\",\"cityName\":\"Aachen\",\"timeZoneName\":\"Europe/Berlin\"},\"state\":{},\"country\":{\"countryCode\":\"DE\",\"countryName\":\"Germany\"},\"region\":{\"regionCode\":\"EU\",\"regionName\":\"Europe\"}}]"
)

fun getAirports(isEmpty: Boolean): Single<List<Airport>> {
    return if (isEmpty) {
        Single.just(emptyList<Airport>())
    } else {
        Single.just(mockAirports)
    }
}

val mockExceptionBodyRaw: String = "{\"error\":[\"Something went wrong\"]}"

fun getMockException(): Single<List<Airport>> {
    val errorBody =
        mockExceptionBodyRaw.toResponseBody("application/json".toMediaTypeOrNull())
    return Single.error(
        HttpException(
            Response.error<ResponseBody>(
                500, errorBody
            )
        )
    )
}