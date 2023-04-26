package com.example.movierecom_rsl.fragments

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.movierecom_rsl.DataBaseHelper
import com.example.movierecom_rsl.MovieListActivity
import com.example.movierecom_rsl.R

class LogInFragment : Fragment() {
    open val PREFS_NAME: String = "MyPrefsFile"
    lateinit var handler: DataBaseHelper
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_log_in, container, false)
        val thiscontext = activity?.let { DataBaseHelper(it) }!!
        handler = thiscontext

        // Declaring the prefrence object
        val preferences = this.activity
            ?.getSharedPreferences(LogInFragment::PREFS_NAME.toString(), 0)


        val loginButton: Button = view.findViewById(R.id.login_button)
        val emailEditText: EditText = view.findViewById(R.id.login_email)
        val passwordEditText: EditText = view.findViewById(R.id.login_password)

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            // Calling function of DB helper class
            if (handler.userPresent(email, password)) {
                Log.d("sucessfull", "$email $password")
                var editor: SharedPreferences.Editor =
                    (preferences?.edit() ?: null) as SharedPreferences.Editor
                // changing the boolean data of key hasLoggedIn to true because credentials is matched now
                editor.putBoolean("hasLoggedIn", true)
                editor.commit()
                // Starting the Activity with Intent
                val intent = Intent(activity, MovieListActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            } else {
                //Generating Log message
                Log.d("Not sucessfull", "$email $password")
            }
        }


        return view
    }

}