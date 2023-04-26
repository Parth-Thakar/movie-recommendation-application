package com.example.movierecom_rsl.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.InputQueue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.example.movierecom_rsl.AdapterForAllMovies
import com.example.movierecom_rsl.MovieModelClass
import com.example.movierecom_rsl.R
import com.example.movierecom_rsl.VolleySingelton
import org.json.JSONArray
import org.json.JSONObject


class AllMoviesFragment : Fragment() {

    val API_URL: String = "https://mocki.io/v1/13fc070d-690a-40ef-86ea-4090b8f68026"

    private lateinit var recyclerView : RecyclerView
    private lateinit var requestQueue : RequestQueue
    private lateinit var movieList :  ArrayList<MovieModelClass>

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_all_movies, container, false)

        recyclerView = view.findViewById(R.id.recyclerviewForAllMovies)
        recyclerView.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager
        return view

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestQueue = activity?.let { VolleySingelton.getInstance(it).requestQueue }!!
        movieList = ArrayList()
        fetchMovies()

    }

    fun fetchMovies() {
        val jsonArrayRequest = JsonArrayRequest(Request.Method.GET, API_URL, null, { response ->
            for(i in 0 until response.length())
            {
                val jsonObject : JSONObject = response.getJSONObject(i)
                val movieName : String = jsonObject.getString("title")
                val posterImage : String = jsonObject.getString("posterUrl")
                val generes : JSONArray = jsonObject.getJSONArray("genres")
                val genereList = generes.toArrayList()
                val id : Int = jsonObject.getInt("id")

                val movie = MovieModelClass(movieName,id.toString(),posterImage,genereList)

                movieList.add(movie)
            }
            val adapter = activity?.let { AdapterForAllMovies(it,movieList) }

            recyclerView.adapter = adapter

        }, { err ->
            Log.e("error error", "nhi hua $err")
        })

        requestQueue.add(jsonArrayRequest)
    }

    fun JSONArray.toArrayList(): ArrayList<String> {
        val list = arrayListOf<String>()
        for (i in 0 until this.length()) {
            list.add(this.getString(i))
        }

        return list
    }

}