package com.cbermudezg.imbd_hatchworks.model.network

import com.cbermudezg.imbd_hatchworks.model.Movie
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

/**
 * Interface required to send to retrofit service
 */
interface RapidApiService {
    /**
     * This function will return the list of the 100 top ranked movies
     */
    @Headers("X-RapidAPI-Key: 7afe9d5845mshe6192186b423a02p1ba901jsn4fc04d0dc70a")
    @GET("/")
    fun getMovieList(): Call<List<Movie>>
}