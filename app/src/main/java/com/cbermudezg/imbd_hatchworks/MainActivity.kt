package com.cbermudezg.imbd_hatchworks

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.MotionEvent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cbermudezg.imbd_hatchworks.databinding.ActivityMainBinding
import com.cbermudezg.imbd_hatchworks.ui.MovieAdapter
import com.cbermudezg.imbd_hatchworks.viewmodel.MainViewModel
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    /**
     * MVVM view model
     */
    private val mainViewModel: MainViewModel by viewModels()

    /**
     * I am also using view binding here cuz is more fun!
     */
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view) // root view
        mainViewModel.fetchMovies()
        val recyclerView: RecyclerView = binding.list
        //Grid Layout Style for the list
        recyclerView.layoutManager = GridLayoutManager(this, 3)
        refreshList()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun refreshList() {
        val recyclerView: RecyclerView = binding.list
        lifecycleScope.launch {
            // repeatOnLifecycle launches the block in a new coroutine every time the
            // lifecycle is in the STARTED state (or above) and cancels it when it's STOPPED.
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                // Trigger the flow and start listening for values.
                // Note that this happens when lifecycle is STARTED and stops
                // collecting when the lifecycle is STOPPED
                mainViewModel.movies.collect { movies ->
                    // New value received
                    if (movies.isNotEmpty()) {
                        /**
                         * This is interesting, we send the closure or Lambda function
                         * to the Adapter, so we can use it as a callback to MainActivity
                         * and redirect to details view.
                         * Earlier I had it this way,
                         *  val movieAdapter = MovieAdapter(mainViewModel.movies.value) { idx ->
                         *    adapterOnClick(idx)
                         *  }
                         * recyclerView.adapter = movieAdapter
                         *
                         * but I can win a variable using
                         * direct assignation like this
                         */
                        binding.listTitle.setBackgroundColor(Color.TRANSPARENT)
                        binding.listTitle.text = resources.getText(R.string.txt_title_list)
                        recyclerView.adapter = MovieAdapter(mainViewModel.movies.value) { idx ->
                            adapterOnClick(idx)
                        }
                    }
                    else {
                        binding.listTitle.setBackgroundResource(R.color.error)
                        binding.listTitle.text = resources.getText(R.string.error_msj)
                    }
                }
            }
        }
    }

    /**
     * This is a Lambda or closure function to send via param to the respective listener
     * it will allow us to click on every element of the recycler view
     * @param index: - position of the element clicked
     */
    private fun adapterOnClick(index: Int) {
        val intent = Intent(this, MovieDetailActivity::class.java)
        // Before we move to next screen get & assign current movie
        // Why? because the list of movies depends on the MainViewModel
        // If I try to access it from the other detail view the list will be empty
        // Also I think MutableLiveData is so cool
        mainViewModel.getMovieByIndex(index)
        startActivity(intent)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return when (event?.action) {
            MotionEvent.ACTION_UP -> {
                // Refresh list
                refreshList()
                true
            }
            else -> true
        }

    }
}