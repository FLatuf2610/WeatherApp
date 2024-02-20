package com.example.apipractice.ui.home

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.apipractice.R
import com.example.apipractice.databinding.ItemHourlyBinding
import com.example.apipractice.domain.models.HourlyWeather
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class HourlyAdapter(private val hours: List<HourlyWeather>) :RecyclerView.Adapter<HourlyAdapter.HourlyViewHolder>() {

    private lateinit var mContext: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyViewHolder {
        mContext = parent.context
        return HourlyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_hourly, parent, false))
    }


    override fun getItemCount(): Int = 24

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: HourlyViewHolder, position: Int) {
        holder.render(hours[position])
    }

    inner class HourlyViewHolder(view: View):RecyclerView.ViewHolder(view){
       private val binding = ItemHourlyBinding.bind(view)
        @RequiresApi(Build.VERSION_CODES.O)
        fun render(hourly: HourlyWeather) {
            binding.tvHour.text = hourly.dt.toString()
            val zone = ZoneId.systemDefault()
            val instant = Instant.ofEpochSecond(hourly.dt.toLong())
            val dateTime = instant.atZone(zone).toLocalDateTime()
            val formatter = DateTimeFormatter.ofPattern("HH:mm")
            val formattedTime = dateTime.format(formatter)
            binding.tvHour.text = formattedTime

            binding.tvTemperatureHourly.text = hourly.temp.toInt().toString() + "°"
            binding.tvHumidityHourly.text = hourly.humidity.toString() + "%"
            val icon = hourly.weather[0].icon
            Glide.with(mContext)
                .load("https://openweathermap.org/img/wn/${icon}@2x.png")
                .into(binding.ivIconWeatherHourly)
        }

    }

}