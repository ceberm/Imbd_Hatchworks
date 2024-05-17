package com.cbermudezg.imbd_hatchworks

import android.content.Intent
import android.os.Bundle
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

    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        mainViewModel.fetchMovies()
        val recyclerView: RecyclerView = binding.list
        recyclerView.layoutManager = GridLayoutManager(this, 3)

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
                        val movieAdapter = MovieAdapter(mainViewModel.movies.value) { idx ->
                            adapterOnClick(idx)
                        }
                        recyclerView.adapter = movieAdapter
                    }
                }
            }
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    /**
     *
     */
    private fun adapterOnClick(index: Int) {
        val intent = Intent(this, MovieDetailActivity::class.java)
        mainViewModel.getMovieByIndex(index)
        startActivity(intent)
    }
}