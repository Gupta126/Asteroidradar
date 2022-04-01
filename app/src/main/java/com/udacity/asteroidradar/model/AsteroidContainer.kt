package com.udacity.asteroidradar.model

import androidx.lifecycle.Transformations.map
import com.squareup.moshi.JsonClass
import com.udacity.asteroidradar.repository.database.DatabaseAsteroid

@JsonClass(generateAdapter = true)
data class AsteroidContainer(val near_earth_objects: Map<String, List<NetworkAsteroid>>)

@JsonClass(generateAdapter = true)
data class NetworkAsteroid(
    val links: Links,
    val id: Int,
    val neo_reference_id: Int,
    val name: String,
    val nasa_jpl_url: String,
    val absolute_magnitude_h: Double,
    val estimated_diameter: Estimated_diameter,
    val is_potentially_hazardous_asteroid: Boolean,
    val close_approach_data: List<Close_approach_data>,
    val is_sentry_object: Boolean
)

@JsonClass(generateAdapter = true)
data class Close_approach_data(
    val close_approach_date: String,
    val close_approach_date_full: String,
    val epoch_date_close_approach: Int,
    val relative_velocity: Relative_velocity,
    val miss_distance: Miss_distance,
    val orbiting_body: String
)

@JsonClass(generateAdapter = true)
data class Feet(
    val estimated_diameter_min: Double,
    val estimated_diameter_max: Double
)

@JsonClass(generateAdapter = true)
data class Estimated_diameter(
    val kilometers: Kilometers,
    val meters: Meters,
    val miles: Miles,
    val feet: Feet
)

@JsonClass(generateAdapter = true)
data class Kilometers(
    val estimated_diameter_min: Double,
    val estimated_diameter_max: Double
)

@JsonClass(generateAdapter = true)
data class Links(
    val self: String
)

@JsonClass(generateAdapter = true)
data class Meters(
    val estimated_diameter_min: Double,
    val estimated_diameter_max: Double
)

@JsonClass(generateAdapter = true)
data class Miles(
    val estimated_diameter_min: Double,
    val estimated_diameter_max: Double
)

@JsonClass(generateAdapter = true)
data class Miss_distance(
    val astronomical: Double,
    val lunar: Double,
    val kilometers: Double,
    val miles: Double
)

@JsonClass(generateAdapter = true)
data class Relative_velocity(
    val kilometers_per_second: Double,
    val kilometers_per_hour: Double,
    val miles_per_hour: Double
)
