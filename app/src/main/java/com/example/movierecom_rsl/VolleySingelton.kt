package com.example.movierecom_rsl
import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

class VolleySingelton constructor(context: Context){
    companion object {
        // Singleton object
        @Volatile
        private var instance: VolleySingelton? = null
        fun getInstance(context: Context) = instance?: synchronized(this){
            instance?: VolleySingelton(context)
        }
    }


    val requestQueue: RequestQueue by lazy {
        // applicationContext is key, it keeps you from leaking the
        // Activity or BroadcastReceiver if someone passes one in.
        Volley.newRequestQueue(context.applicationContext)
    }

    fun <T> addToRequestQueue(req: Request<T>){
        requestQueue.add(req)
    }
}

