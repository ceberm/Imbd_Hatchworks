package com.cbermudezg.imbd_hatchworks.model.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * This is in charge of creating the service and also hold the retrofit instance
 */
class HttpServiceGenerator {

    private val BASE_URL = "https://imdb-top-100-movies.p.rapidapi.com"

    private val builder: Retrofit.Builder = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())

    private val retrofit: Retrofit = builder.build()

    /**
     * In this case I am only returning RapidApiService because that's the only thing we need
     * Ideally this method should receive a generic class to handle different type of requests
     * for example if we want also series, that's another type, so this ca be more scalable.
     */
    fun createService(): RapidApiService {
        return retrofit.create(RapidApiService::class.java)
    }

}