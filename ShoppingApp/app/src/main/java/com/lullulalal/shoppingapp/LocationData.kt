package com.lullulalal.shoppingapp

data class LocationData(
    val latitude:Double,
    val longitude:Double,
)

data class GeocordingResponse(
    val results:List<GeocordingResult>,
    val status: String,
)

data class GeocordingResult (
    val formatted_address: String,
)