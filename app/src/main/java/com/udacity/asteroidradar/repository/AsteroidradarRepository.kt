package com.udacity.asteroidradar.repository

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.api.asDatabaseModel
import com.udacity.asteroidradar.api.getSeventhDay
import com.udacity.asteroidradar.api.getToday
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.model.Asteroid
import com.udacity.asteroidradar.model.PictureOfDay
import com.udacity.asteroidradar.repository.database.AsteroidDatabase
import com.udacity.asteroidradar.repository.database.asDomainModel
import com.udacity.asteroidradar.repository.network.Network
import com.udacity.asteroidradar.utilities.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import org.json.JSONObject
import timber.log.Timber

class AsteroidradarRepository(private val database: AsteroidDatabase) {

    @RequiresApi(Build.VERSION_CODES.N)
    suspend fun refreshAsteroidData(
        startDate: String = getToday(),
        endDate: String = getSeventhDay()
    ) {
        withContext(Dispatchers.IO) {
            val asteroidResponseBody: ResponseBody = Network.Neows.getFeed(startDate, endDate)
                .await()
            val asteroidList = parseAsteroidsJsonResult(JSONObject(asteroidResponseBody.string()))
            database.asteroidDao.insertAll(*asteroidList.asDatabaseModel())
        }
    }

    suspend fun getPictureOfDay(): PictureOfDay? {
        var pictureOfDay: PictureOfDay
        withContext(Dispatchers.IO) {
            pictureOfDay = Network.Neows.getPictureOfDayAsync().await()
        }
        if (pictureOfDay.mediaType == Constants.IMAGE_MEDIA_TYPE) {
            return pictureOfDay
        }
        return null
    }

    @RequiresApi(Build.VERSION_CODES.N)
    suspend fun deletePreviousDayAsteroids() {
        withContext(Dispatchers.IO) {
            database.asteroidDao.deletePreviousDayAsteroids(getToday())
        }
    }
}