package com.cbermudezg.imbd_hatchworks.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cbermudezg.imbd_hatchworks.R
import com.squareup.picasso.Picasso
import com.tobi.imdb.model.Movie
import timber.log.Timber

/**
 * We need adapter pattern to show the list of elements efficiently using RecyclerView
 * @param dataset: - List of Movies
 */
class MovieAdapter(private val dataset: List<Movie>) : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    /**
     * ViewHolder class to recycle the views on the list and not create new ones
     *  @param view: - Reference to the view frame you are using
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Define click listener for the ViewHolder's View
        val mImgButton: ImageButton = view.findViewById(R.id.image_button)

        init {
            mImgButton.setOnClickListener {
                Timber.i("Movie Clicked!")
            }
        }
    }

    /**
     * Called to create new views
     *  @param parent: - Some View
     *  @param viewType: -
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row, parent, false)

        return ViewHolder(view)
    }

    /**
     * Return the size of movies list
     */
    override fun getItemCount(): Int {
        return dataset.size
    }

    /**
     * This one will load information from the dataset when scrolling
     * @param holder: view holder created above
     * @param position: the position of the element to render
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Use Picasso library to fetch image from URL
        Picasso.get().load(dataset[position].image).into(holder.mImgButton)
    }
}