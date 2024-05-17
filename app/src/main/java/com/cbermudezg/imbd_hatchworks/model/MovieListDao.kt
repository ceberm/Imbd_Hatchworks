package com.cbermudezg.imbd_hatchworks.model

import androidx.lifecycle.MutableLiveData
import com.cbermudezg.imbd_hatchworks.model.network.HttpServiceGenerator
import kotlinx.coroutines.flow.flow

/**
 * Data Access Object for Movie List
 */

const val MOVIE = "movie"
val mCurrentMovie = MutableLiveData<Movie>()
class MovieListDao {
    //Get http service to get movies
    private val service = HttpServiceGenerator().createService()
    // A call that needs to be executed
    private val call = service.getMovieList()

    val mCurrentMovie = MutableLiveData<Movie>()

    /**
     * Returns a flow with the list of movies parsed from RetroFit
     */
    private fun getMovieList(): List<Movie>? {
        // execute request to get list of movies
        val response = call.execute()
        //if body is not null
        response.body()?.let {
            return it
        }
        return null
    }

    /**
     * Just emit the response from the retrofit requests to flow
     */
    fun getMovies() = flow {
        emit(getMovieList())
    }
}