package com.example.movierecom_rsl

import android.content.Context
import android.os.TestLooperManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class AdapterForRecommendationMovies(context: Context, Data: List<MovieModelClass>) :
    RecyclerView.Adapter<AdapterForRecommendationMovies.MovieHolder>() {
    // Adapter for Recommendation Movies
    private var context: Context
    private var movieList: List<MovieModelClass>

    init {
        this.context = context
        this.movieList = Data
    }

    class MovieHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val poster: ImageView = itemView.findViewById(R.id.posterImage)
        val movieName: TextView = itemView.findViewById(R.id.movieName)
        val genre: TextView = itemView.findViewById(R.id.genre)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recommendation_movies_item_layout, parent, false)

        return MovieHolder(view)
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {

        val currentMovie = movieList.get(position)

        holder.movieName.text = currentMovie.name
        holder.genre.text = currentMovie.genre.toString()
        Glide.with(context).load(currentMovie.image).into(holder.poster)

    }

    override fun getItemCount(): Int {
        return movieList.size
    }


}