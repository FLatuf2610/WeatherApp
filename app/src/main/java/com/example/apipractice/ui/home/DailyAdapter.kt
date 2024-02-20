package com.example.apipractice.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.apipractice.R
import com.example.apipractice.databinding.ItemDailyBinding
import com.example.apipractice.domain.models.DailyWeather
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DailyAdapter(private val daily: List<DailyWeather>):RecyclerView.Adapter<DailyAdapter.DailyViewHolder>() {

    private lateinit var mContext:Context



    inner class DailyViewHolder(view: View):RecyclerView.ViewHolder(view) {
        private val binding = ItemDailyBinding.bind(view)
        fun render(day: DailyWeather, position: Int) {
            binding.tvMin.text = day.temp.min.toInt().toString() + "°"
            binding.tvMax.text = day.temp.max.toInt().toString() + "°"
            val date = Date(day.dt.toLong() * 1000 )
            val dayOfWeek = SimpleDateFormat("EEEE", Locale.getDefault()).format(date)
            binding.tvDay.text = dayOfWeek.replaceFirstChar { it.uppercase() }
            if (position == 0){
                binding.tvDay.text = "Today"
            }
            Glide.with(mContext)
                .load("https://openweathermap.org/img/wn/${day.weather[0].icon}@2x.png")
                .into(binding.ivIconWeatherDaily)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyViewHolder {
        mContext = parent.context
        val view = LayoutInflater.from(mContext).inflate(R.layout.item_daily, parent, false)
        return DailyViewHolder(view)
    }

    override fun getItemCount(): Int = daily.size

    override fun onBindViewHolder(holder: DailyViewHolder, position: Int) {
        holder.render(daily[position], position)
    }
}