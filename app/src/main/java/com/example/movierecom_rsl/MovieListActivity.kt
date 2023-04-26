package com.example.movierecom_rsl

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.movierecom_rsl.fragments.AllMoviesFragment
import com.example.movierecom_rsl.fragments.LogInFragment
import com.example.movierecom_rsl.fragments.RecommendationFragment

class MovieListActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_list)
        // Activity that host 2 Fragments with their RecyclerViews
        val allMoviesFragmentButton: Button = findViewById(R.id.moviesButton)
        val reccomendationFragmentButton: Button = findViewById(R.id.recommendationButton)

        replaceFragment(AllMoviesFragment())

        allMoviesFragmentButton.setOnClickListener {
            replaceFragment(AllMoviesFragment())
        }

        reccomendationFragmentButton.setOnClickListener {
            replaceFragment(RecommendationFragment())
        }

        val logoutButton : Button = findViewById(R.id.logoutButton)

        logoutButton.setOnClickListener {
            var sharedPreferences: SharedPreferences =
                this.getSharedPreferences(LogInFragment::PREFS_NAME.toString(), 0)

            // declaring the editor object to edit the booleand data
            var editor: SharedPreferences.Editor = sharedPreferences.edit()

            // changing the boolean data of key hasLoggedIn to false because user clicked on logout
            editor.putBoolean("hasLoggedIn", false)
            editor.commit()
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager  =  supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
            .replace(R.id.fragmentSpace,fragment)

        fragmentTransaction.commit()

    }
}