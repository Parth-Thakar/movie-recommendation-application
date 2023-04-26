package com.example.movierecom_rsl.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.example.movierecom_rsl.DataBaseHelper
import com.example.movierecom_rsl.R


class RegisterFragment : Fragment() {
    lateinit var handler: DataBaseHelper
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_register, container, false)

        val registerButton : Button = view.findViewById(R.id.signup_button)

        handler = activity?.let { DataBaseHelper(it) }!!


        val emailEditText : EditText = view.findViewById(R.id.signup_email)
        val passwordEditText : EditText = view.findViewById(R.id.signup_password)
        // Register Button Clicked
        registerButton.setOnClickListener{
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            handler.insertuserData(email,password)
            Log.e("credentials","$email $password")
        }



        return view
    }

}