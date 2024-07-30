package com.lullulalal.shoppingapp

import retrofit2.http.GET
import retrofit2.http.Query

interface GeocordingAptService {

    @GET("maps/api/geocode/json")
    suspend fun getAddressFromCoordinates(
        @Query("latlng") latlng:String,
        @Query("key") apiKey: String
    ): GeocordingResponse

}