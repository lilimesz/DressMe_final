@file:Suppress("DEPRECATION")

package com.google.dressme

import android.annotation.SuppressLint
import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import org.json.JSONObject
import java.net.URL
import kotlin.math.roundToInt

class Home : Fragment() {
    val city: String= "Szombathely"
    val api: String = "7f4f240952e9ae84fc6a23123fffd6af"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WeatherTask().execute()
    }

   @SuppressLint("StaticFieldLeak")
   inner class WeatherTask : AsyncTask<String,Void,String>() {
        @Deprecated("Deprecated in Java")
        override fun doInBackground(vararg params: String?): String? {
            val response: String? = try {
                URL("https://api.openweathermap.org/data/2.5/weather?q=$city&appid=$api&units=metric")
                    .readText(Charsets.UTF_8)

            } catch (e: Exception) {
                null
            }
            return response
        }

        @Deprecated("Deprecated in Java")
        override fun onPostExecute(result: String) {
            super.onPostExecute(result)
            try{
                val jsonObj=JSONObject(result)
                val main = jsonObj.getJSONObject("main")
                val weather = jsonObj.getJSONArray("weather").getJSONObject(0)
                val temp = main.getDouble("temp").roundToInt().toString()+" °C"
                val weatherDescription=weather.getString("description")
                val address = jsonObj.getString("name")

                view?.findViewById<TextView>(R.id.location)!!.text=address
                view?.findViewById<TextView>(R.id.temp)!!.text= temp
                view?.findViewById<TextView>(R.id.weatherDesc)!!.text= weatherDescription

            }
            catch (e:Exception)
            {
                val address="Something went wrong..."
                view?.findViewById<TextView>(R.id.location)!!.text=address

            }
        }
    }





    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

}