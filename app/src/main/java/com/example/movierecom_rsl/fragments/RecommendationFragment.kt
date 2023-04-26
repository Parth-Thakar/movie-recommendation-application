package com.example.movierecom_rsl.fragments

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.example.movierecom_rsl.*
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.json.JSONArray
import org.json.JSONObject

class RecommendationFragment : Fragment() {

    val API_URL: String = "https://mocki.io/v1/13fc070d-690a-40ef-86ea-4090b8f68026"

    // Fragment for Loading the recyclerview of recommendation movies
    private lateinit var recyclerView: RecyclerView
    private lateinit var requestQueue: RequestQueue
    private lateinit var movieList: ArrayList<MovieModelClass>
    private lateinit var movieListToSend: ArrayList<MovieModelClass>


    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_recommendation, container, false)
        recyclerView = view.findViewById(R.id.recyclerviewForRecommendationMovies)
        recyclerView.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestQueue = activity?.let { VolleySingelton.getInstance(it).requestQueue }!!
        movieList = ArrayList()
        movieListToSend = ArrayList()
        fetchMovies()
    }

    // Fecthing movies
    fun fetchMovies() {

        val jsonArrayRequest = JsonArrayRequest(Request.Method.GET, API_URL, null, { response ->
            for (i in 0 until response.length()) {
                val jsonObject: JSONObject = response.getJSONObject(i)
                val movieName: String = jsonObject.getString("title")
                val posterImage: String = jsonObject.getString("posterUrl")
                val generes: JSONArray = jsonObject.getJSONArray("genres")
                val genereList = generes.toArrayList()
                val id: Int = jsonObject.getInt("id")

                val movie = MovieModelClass(movieName, id.toString(), posterImage, genereList)

                movieList.add(movie)
            }

            val sharedPreferences: SharedPreferences =
                (activity?.getSharedPreferences("GenreData", 0) ?: null) as SharedPreferences
            val json: String? = sharedPreferences.getString("Liked_genre", null)
            val gson = GsonBuilder().create()
            var check = gson.fromJson<ArrayList<String>>(
                json,
                object : TypeToken<ArrayList<String>>() {}.type
            )
            //if check is null create a dumy empty null data and add it to movielistTosend
            if (check == null) {
                val NodataTemp = MovieModelClass("No prefer Data", "NA", "NA", ArrayList())
                movieListToSend.add(NodataTemp)
            } else {
                // Adding those items in the final list which have similar generes that is also present in the shared prefrences
                for (currentItem in movieList) {
                    if (currentItem.isRecommended(check)) {
                        movieListToSend.add(currentItem)
                    }
                }
            }
            // setting up the adapter
            val adapter = activity?.let { AdapterForRecommendationMovies(it, movieListToSend) }

            recyclerView.adapter = adapter

        }, { err ->
            //Error response Listner
            Log.e("error error", "$err")
        })
        //adding Request to Volley
        requestQueue.add(jsonArrayRequest)
    }

    // Extension function to convert JSON Arry to ArrayList
    fun JSONArray.toArrayList(): ArrayList<String> {
        val list = arrayListOf<String>()
        for (i in 0 until this.length()) {
            list.add(this.getString(i))
        }

        return list
    }

}