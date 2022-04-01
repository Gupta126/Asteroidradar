package com.udacity.asteroidradar.job

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.repository.AsteroidradarRepository
import com.udacity.asteroidradar.repository.database.AsteroidDatabase
import retrofit2.HttpException

class RefreshDataJob(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        val database = AsteroidDatabase.getDatabase(applicationContext)
        val repository = AsteroidradarRepository(database)

        return try {
            repository.refreshAsteroidData()
            Result.success()
        } catch (exception: HttpException) {
            Result.retry()
        }
    }
}