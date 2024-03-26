package com.felixwuggenig.moviemanager.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.felixwuggenig.moviemanager.R
import com.felixwuggenig.moviemanager.models.Movie

class FavoriteMovieAdapter(
    private var movies: List<Movie>,
    private var onMovieClicked: (Int) -> Unit
) :
    RecyclerView.Adapter<FavoriteMovieAdapter.ViewHolder>() {

    fun updateFavoriteMovies(newMovies: List<Movie>) {
        movies = newMovies
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.favorite_item, parent, false)
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
        private val imageViewFavorite: ImageView = itemView.findViewById(R.id.imageViewFavorite)

        fun bind(movie: Movie) {
            itemView.setOnClickListener {
                onMovieClicked(movie.id)
            }
            Glide.with(imageViewFavorite).load(movie.posterURL).into(imageViewFavorite)
        }
    }
}