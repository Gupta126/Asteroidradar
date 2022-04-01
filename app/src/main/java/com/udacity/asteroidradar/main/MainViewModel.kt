package com.udacity.asteroidradar.main

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.*
import com.udacity.asteroidradar.api.getSeventhDay
import com.udacity.asteroidradar.api.getToday
import com.udacity.asteroidradar.model.Asteroid
import com.udacity.asteroidradar.model.PictureOfDay
import com.udacity.asteroidradar.repository.AsteroidradarRepository
import com.udacity.asteroidradar.repository.database.AsteroidDatabase
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import java.lang.Exception

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val database = AsteroidDatabase.getDatabase(application)
    private val asteroidRepository = AsteroidradarRepository(database)

    private val _navigateToDetailFragment = MutableLiveData<Asteroid>()
    val navigateToDetailFragment: LiveData<Asteroid>
        get() = _navigateToDetailFragment

    private var _asteroids = MutableLiveData<List<Asteroid>>()
    val asteroids: LiveData<List<Asteroid>>
        get() = _asteroids

    private val _pictureOfDay = MutableLiveData<PictureOfDay>()
    val pictureOfDay: LiveData<PictureOfDay>
        get() = _pictureOfDay

    private val _displaySnackbarEvent = MutableLiveData<Boolean>()
    val displaySnackbarEvent: LiveData<Boolean>
        get() = _displaySnackbarEvent

    init {
        onViewWeekAsteroidsClicked()
        viewModelScope.launch {
            try {
                asteroidRepository.refreshAsteroidData()
                refreshPictureOfDay()
            } catch (e: Exception) {
                println("Exception refreshing data: $e.message")
                _displaySnackbarEvent.value = true
            }
        }
    }

    private suspend fun refreshPictureOfDay() {
        _pictureOfDay.value = asteroidRepository.getPictureOfDay()
    }

    fun onAsteroidClicked(asteroid: Asteroid) {
        _navigateToDetailFragment.value = asteroid
    }

    @SuppressLint("NullSafeMutableLiveData")
    fun doneNavigating() {
        _navigateToDetailFragment.value = null
    }

    fun doneDisplayingSnackbar() {
        _displaySnackbarEvent.value = false
    }

    fun onViewWeekAsteroidsClicked() {
        viewModelScope.launch {
            database.asteroidDao.getAsteroidsByCloseApproachDate(getToday(), getSeventhDay())
                .collect { asteroids ->
                    _asteroids.value = asteroids
                }
        }
    }

    fun onTodayAsteroidsClicked() {
        viewModelScope.launch {
            database.asteroidDao.getAsteroidsByCloseApproachDate(getToday(), getToday())
                .collect { asteroids ->
                    _asteroids.value = asteroids
                }
        }
    }

    fun onSavedAsteroidsClicked() {
        viewModelScope.launch {
            database.asteroidDao.getAllAsteroids().collect { asteroids ->
                _asteroids.value = asteroids
            }
        }
    }
}