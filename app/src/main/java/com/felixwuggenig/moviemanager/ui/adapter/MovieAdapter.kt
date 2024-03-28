package com.felixwuggenig.moviemanager.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.felixwuggenig.moviemanager.R
import com.felixwuggenig.moviemanager.models.Movie

class MovieAdapter(
    private var movies: List<Movie>,
    private var favoritesIDs: List<Int>,
    private val onFavoriteClicked: (Int) -> Unit,
    private val onItemClicked: (Int) -> Unit
) :
    RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    fun updateMovies(newMovies: List<Movie>, newFavoritesIDs: List<Int>) {
        movies = newMovies
        favoritesIDs = newFavoritesIDs
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageViewMovie: ImageView = itemView.findViewById(R.id.imageViewMovie)
        private val textViewYear: TextView = itemView.findViewById(R.id.textViewYear)
        private val ratingBar: RatingBar = itemView.findViewById(R.id.ratingBarMovie)
        private val textViewName: TextView = itemView.findViewById(R.id.textViewName)
        private val imageViewFavorite: ImageView = itemView.findViewById(R.id.imageViewFavorite)

        fun bind(movie: Movie) {
            textViewYear.text = movie.releaseDate.year.toString()
            ratingBar.rating = movie.rating.toFloat()
            ratingBar.setIsIndicator(true)
            textViewName.text = movie.title
            Glide.with(imageViewMovie)
                .load(movie.posterURL)
                .placeholder(R.drawable.glide_placeholder)
                .error(R.drawable.glide_error)
                .into(imageViewMovie)

            if (favoritesIDs.contains(movie.id)) {
                imageViewFavorite.setImageDrawable(imageViewFavorite.context.getDrawable(R.drawable.favorite_on))
            } else {
                imageViewFavorite.setImageDrawable(imageViewFavorite.context.getDrawable(R.drawable.favorite_off))
            }

            imageViewFavorite.setOnClickListener {
                onFavoriteClicked(movie.id)
            }

            imageViewMovie.setOnClickListener {
                onItemClicked(movie.id)
            }
        }
    }
}