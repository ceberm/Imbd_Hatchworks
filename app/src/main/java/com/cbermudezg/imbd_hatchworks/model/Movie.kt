package com.cbermudezg.imbd_hatchworks.model

/**
 * Base Class of Movie (based on structure coming from API)
 */
class Movie {
    val rank: Int = 0
    val year: Int = 0
    lateinit var title: String
    lateinit var description: String
    lateinit var image: String
    lateinit var big_image: String
    lateinit var thumbnail: String
    lateinit var rating: String
    lateinit var id: String
    lateinit var imdbid: String
    lateinit var imdb_link: String
    lateinit var genre: ArrayList<String>
}