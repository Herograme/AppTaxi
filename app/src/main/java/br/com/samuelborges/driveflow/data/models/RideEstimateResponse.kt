package br.com.samuelborges.driveflow.data.models

data class RideEstimateResponse(
    val origin : Location,
    val destination : Location,
    val duration: String,
    val distance: Int,
    val price : Double,
    val options: List<RideOption>,
    val routeResponse: Any

)


data class Location(
    val latitude: Double,
    val longitude: Double,
)

data class RideOption(
    val id: Int,
    val name:String,
    val description: String,
    val vehicle: String,
    val review: Review,
    val value: Double
)

data class Review(
    val rating: Double,
    val comment: String
)