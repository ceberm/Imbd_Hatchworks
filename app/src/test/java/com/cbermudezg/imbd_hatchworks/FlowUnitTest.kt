package com.cbermudezg.imbd_hatchworks

import com.cbermudezg.imbd_hatchworks.viewmodel.MainViewModel
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import timber.log.Timber

class FlowUnitTest {

    private val mainViewModel = MainViewModel()

    // This was taken from official documentation https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-test/
    @OptIn(ExperimentalCoroutinesApi::class, DelicateCoroutinesApi::class)
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun mainViewModel_CorrectMovieRetrieving() = runTest {

        //Fetch the movies and keep them on the state
        launch(Dispatchers.Main) {

            try {
                mainViewModel.fetchMovies()
                delay(2000) // Wait the request to finish, this can be improved with
                val firstMovie = mainViewModel.movies.value.first()
                assert(firstMovie.title == "The Shawshank Redemption")
            } catch (e: Exception) {
                Timber.e(e)
                assertTrue(false)
            }
        }

    }
}