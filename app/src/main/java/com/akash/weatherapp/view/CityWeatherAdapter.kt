package com.akash.weatherapp.view

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.akash.weatherapp.R
import com.akash.weatherapp.model.WeatherEntity
import com.akash.weatherapp.utils.Constants
import com.akash.weatherapp.utils.GlideUtils
import java.util.*

class CityWeatherAdapter : RecyclerView.Adapter<CityWeatherAdapter.ViewHolder>() {
    private lateinit var context: Context
    private val weatherEntities: ArrayList<WeatherEntity> = ArrayList<WeatherEntity>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvCityName: TextView = itemView.findViewById(R.id.tv_city)
        val tvDisplayDate: TextView = itemView.findViewById(R.id.tv_date)
        val tvTempDisplay: TextView = itemView.findViewById(R.id.tv_temperature)
        val tvWeatherDisplay: TextView = itemView.findViewById(R.id.tv_weather)
        val tvWeatherIcon: ImageView = itemView.findViewById(R.id.iv_icon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val transactionItemView =
            LayoutInflater.from(parent.context).inflate(R.layout.city_weather_row, parent, false)
        context = parent.context
        return ViewHolder(transactionItemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.apply {
            weatherEntities[position].let {
                this.tvCityName.text = it.cityName + ", " + it.countryName
                this.tvDisplayDate.text = it.dateTime
                this.tvTempDisplay.text = it.temp.toString() + " \u2103"
                it.icon.let {
                    GlideUtils.setWeatherIcon(
                        this.tvWeatherIcon,
                        Constants.IMAGE_ICON_URL + "${it}.png"
                    )
                }
                this.tvWeatherDisplay.text = it.weather
            }
        }
    }

    override fun getItemCount(): Int {
        return weatherEntities.size
    }

    fun addWeatherEntities(list: List<WeatherEntity>) {
        weatherEntities.clear()
        weatherEntities.addAll(list)
    }
}

