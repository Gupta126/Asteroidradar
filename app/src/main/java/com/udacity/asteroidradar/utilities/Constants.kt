package com.udacity.asteroidradar.utilities

import com.udacity.asteroidradar.BuildConfig
import com.udacity.asteroidradar.utilities.Constants.API_KEY

object Constants {
    val DATABASE_NAME: String = "asteroids"
    const val API_QUERY_DATE_FORMAT = "YYYY-MM-dd"
    const val DEFAULT_END_DATE_DAYS = 7
    const val BASE_URL = "https://api.nasa.gov/"
    val API_KEY :String = BuildConfig.API_KEY
    const val IMAGE_MEDIA_TYPE = "image"
    const val REFRESH_ASTEROIDS_WORK_NAME = "AsteroidRefreshDataWorker"
    const val DELETE_ASTEROIDS_WORK_NAME = "AsteroidDeleteDataWorker"
}
