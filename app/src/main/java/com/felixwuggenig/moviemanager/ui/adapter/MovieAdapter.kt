package com.felixwuggenig.moviemanager.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.felixwuggenig.moviemanager.R
import com.felixwuggenig.moviemanager.models.Movie

class MovieAdapter(private var movies: List<Movie>, private val onFavoriteClicked: (Int) -> Unit) :
    RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    fun updateMovies(newMovies: List<Movie>) {
        movies = newMovies
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
        private val textViewRating: TextView = itemView.findViewById(R.id.textViewRating)
        private val textViewName: TextView = itemView.findViewById(R.id.textViewName)
        private val buttonFavorite: Button = itemView.findViewById(R.id.buttonFavorite)

        fun bind(movie: Movie) {
            // Bind data to views
            // imageViewMovie.setImageResource(movie.imageResId)
            textViewYear.text = movie.releaseDate.year.toString()
            textViewRating.text = movie.rating.toString()
            textViewName.text = movie.title

            // Set onClickListener for the favorite button
            buttonFavorite.setOnClickListener {
                onFavoriteClicked(movie.id)
            }
        }
    }
}