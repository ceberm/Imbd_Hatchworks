package com.cbermudezg.imbd_hatchworks.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cbermudezg.imbd_hatchworks.model.Movie
import com.cbermudezg.imbd_hatchworks.model.MovieListDao
import com.cbermudezg.imbd_hatchworks.model.mCurrentMovie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * View Model of the main activity that has the DAO object
 * and is referenced from the UI to get the list of movies
 */
class MainViewModel : ViewModel() {
    // Instantiate DAO for movies
    private val repository =  MovieListDao()
    // We must Initialize the state flow with empty list
    private val _moviesShared = MutableStateFlow<List<Movie>>(ArrayList())
    // The UI will collect from here the new movies value
    val movies: StateFlow<List<Movie>> get() = _moviesShared.asStateFlow()

    /**
     * fetch movies from retrofit using flows
     */
    fun fetchMovies() {
        viewModelScope.launch {
            repository.getMovies()
                .flowOn(Dispatchers.IO)
                .catch { e ->
                    // log exception with a formatted message
                    Timber.e(e)
                }
                .collect { list ->
                    // ask if the returned list is not null
                    list?.let {
                        _moviesShared.value = it
                        Timber.i("Query Success! Movies Retrieved")
                    }
                }
        }
    }

    fun getMovieByIndex(index: Int) {
        if (_moviesShared.value.isNotEmpty())
            mCurrentMovie.value = _moviesShared.value[index]
    }

}