package com.cbermudezg.imbd_hatchworks

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.animation.DecelerateInterpolator
import android.view.animation.OvershootInterpolator
import android.widget.RadioButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.cbermudezg.imbd_hatchworks.databinding.ActivityMovieDetailBinding
import com.cbermudezg.imbd_hatchworks.model.mCurrentMovie
import com.squareup.picasso.Picasso

class MovieDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        val view = binding.root
        enableEdgeToEdge()
        setContentView(view)
        // Get the current movie information
        val movie = mCurrentMovie.value
        movie?.let {
            //Use view binding to present the movie details
            Picasso.get().load(movie.image).into(binding.detailImg)
            "${movie.title}  ( ${movie.year} )".also { binding.txtDetailTitle.text = it }
            binding.txtDetailDescription.text = movie.description
            binding.txtRating.text = movie.rating
            binding.rtbMovie.rating = movie.rating.toFloat()
            movie.genre.forEach { genre ->
                val radio = RadioButton(this)
                radio.text = genre
                radio.isChecked = true
                binding.rbgGenres.addView(radio)
            }
        }
        animateDetailView()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun animateDetailView() {
        binding.detailImg.alpha = 0f // Image invisible

        //Animation for image
        val animator = ValueAnimator.ofFloat(0f, 1f)
        animator.duration = 1000
        animator.addUpdateListener { anim ->
            val animVal = anim.animatedValue as Float
            binding.detailImg.alpha = animVal // will move the alpha from 0f to 1f
        }
        animator.start()

        //Animation for description
        val finalYPos = binding.txtDetailDescription.y

        val startYPos = finalYPos + 55
        binding.txtDetailDescription.y = startYPos

        val descAnimator = ValueAnimator.ofFloat(startYPos, finalYPos) // start and finish positions
        descAnimator.duration = 1000
        descAnimator.interpolator = DecelerateInterpolator()
        descAnimator.addUpdateListener { anim ->
            val animVal = anim.animatedValue as Float
            binding.txtDetailDescription.translationY = animVal //will translate the text from bottom to top
        }
        descAnimator.start()


    }
}