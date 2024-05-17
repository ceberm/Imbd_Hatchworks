package com.cbermudezg.imbd_hatchworks

import com.cbermudezg.imbd_hatchworks.model.network.HttpServiceGenerator
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class RetrofitUnitTest {
    /**
     * To validate the service generator is correct and Scalable
     */
    @Test
    fun api_isCorrect() {
        val service = HttpServiceGenerator().createService()
        assert(service != null)
    }

    /**
     * Top 100 Ranked Movies
     */
    @Test
    fun get_fullList() {
        // get service from generic function
        val service = HttpServiceGenerator().createService()
        assert(service != null)
        //get retrofit call, this will handle all the execute requests and enqueue method to call async
        val call = service.getMovieList()
        assert(call != null)
        if (call != null) {
            try {
                // execute request and check for the response
                val response = call.execute()
                assertNotNull(response.body())
                assertEquals(response.code(), 200)
                response.body()?.let {
                    // the list of movies must be size == 100
                    assertEquals(it.size, 100)
                }
            } catch (ex: Exception) {
                assertFalse(false)
            }
        }
    }
}