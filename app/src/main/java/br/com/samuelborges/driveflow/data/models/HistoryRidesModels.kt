package br.com.samuelborges.driveflow.data.models

import java.time.LocalDateTime

data class customerHistory(
    val customer_id: String,
    val rides : List<RideHistory>
)

data class RideHistory(
    val id: Number,
    val date: LocalDateTime,
    val origin : String,
    val destination: String,
    val distance:Number,
    val duration:String,
    val driver:Driver,
    val value:Number
)

data class Driver(
    val id: Number,
    val name: String,
)

