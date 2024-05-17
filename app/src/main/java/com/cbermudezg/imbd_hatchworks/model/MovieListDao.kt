package com.tobi.imdb.model

import com.tobi.imdb.model.network.HttpServiceGenerator
import kotlinx.coroutines.flow.flow

/**
 * Data Access Object for Movie List
 */
class MovieListDao {
    //Get http service to get movies
    private val service = HttpServiceGenerator().createService()
    // A call that needs to be executed
    private val call = service.getMovieList()

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