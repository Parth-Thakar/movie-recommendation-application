package com.example.movierecom_rsl

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

class AdapterForAllMovies(context: Context, Data: List<MovieModelClass>) :
    RecyclerView.Adapter<AdapterForAllMovies.MovieHolder>() {
    // Adapter Class for Recyclerview of All Movies

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
        val likeButton: ImageView = itemView.findViewById(R.id.likeButton)
        val dislikeButton : ImageView = itemView.findViewById(R.id.dislikeButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.all_movies_item_layout, parent, false)
        return MovieHolder(view)
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {

        //setting up the data.
        // Checking the current items genre is in the shared prefrence or not if it is then the like button will be green
        val currentMovie = movieList.get(position)
        val sharedPreferences: SharedPreferences =
            (context.getSharedPreferences("GenreData", 0) ?: { "null" }) as SharedPreferences
        val json: String? = sharedPreferences.getString("Liked_genre", null)
        val gson = GsonBuilder().create()
        var check =
            gson.fromJson<ArrayList<String>>(json, object : TypeToken<ArrayList<String>>() {}.type)
        if(check!=null)
        {
            for (it in check) {
                if (it in currentMovie.genre) {
                    holder.likeButton.setColorFilter(Color.GREEN)
                }
            }
        }
        // Setting up the Widget with current item of the List
        holder.movieName.text = currentMovie.name
        holder.genre.text = currentMovie.genre.toString()
        Glide.with(context).load(currentMovie.image).into(holder.poster)

        holder.likeButton.setOnClickListener {
            saveData(currentMovie.genre)
            holder.likeButton.setColorFilter(Color.GREEN)
        }

        holder.dislikeButton.setOnClickListener {
            deleteData(currentMovie.genre)
            holder.dislikeButton.setColorFilter(Color.RED)
        }

    }




    override fun getItemCount(): Int {
        return movieList.size
    }

    private fun saveData(genre: ArrayList<String>) {
        // Code for saving data of Liked Genre in Shared Prefrences
        val sharedPreferences1: SharedPreferences =
            (context.getSharedPreferences("GenreData", 0) ?: { "null" }) as SharedPreferences
        val json: String? = sharedPreferences1.getString("Liked_genre", null)
        val gson1 = GsonBuilder().create()
        var check =
            gson1.fromJson<ArrayList<String>>(json, object : TypeToken<ArrayList<String>>() {}.type)



        val sharedPreferences: SharedPreferences = context.getSharedPreferences("GenreData", 0)
        val editor = sharedPreferences.edit()
        val gson = Gson()
        val genreData = gson.toJson(genre)
        var logarray = ArrayList<String>()
        if(check!=null)
        {
            logarray = (check + genre) as ArrayList<String>
        }
        else{
            logarray = genre
        }
        val final = removeDuplicates(logarray)
        val tolog = gson.toJson(final)



        Log.e("jsoninshared","$tolog")
        editor.putString("Liked_genre", tolog)
        editor.apply()

    }

    private fun removeDuplicates(logarray: ArrayList<String>): List<String> {
        return logarray.distinct()
    }


    private fun deleteData(genre: ArrayList<String>) {
        val sharedPreferences1: SharedPreferences =
            (context.getSharedPreferences("GenreData", 0) ?: { "null" }) as SharedPreferences
        val json: String? = sharedPreferences1.getString("Liked_genre", null)
        val gson1 = GsonBuilder().create()
        var check =
            gson1.fromJson<ArrayList<String>>(json, object : TypeToken<ArrayList<String>>() {}.type)

        val final = check - genre
        val gson = Gson()
        val tolog = gson.toJson(final)

        val sharedPreferences: SharedPreferences = context.getSharedPreferences("GenreData", 0)
        val editor = sharedPreferences.edit()
        Log.e("deleted","$tolog")
        editor.putString("Liked_genre", tolog)
        editor.apply()

    }


}