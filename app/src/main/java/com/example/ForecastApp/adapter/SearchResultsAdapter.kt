package com.example.ForecastApp.adapter

import android.annotation.SuppressLint
import android.content.res.Resources
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.recyclerview.widget.RecyclerView
import com.example.ForecastApp.DataBank.Constants
import com.example.ForecastApp.R
import com.example.ForecastApp.DataBank.Utils
import com.example.ForecastApp.model.Objects.Main_Elements.Day
import com.squareup.picasso.Picasso
import java.util.ArrayList
import java.util.Objects
import butterknife.BindView
import butterknife.ButterKnife
import androidx.cardview.widget.CardView
import com.example.ForecastApp.model.Objects.Main_Elements.Day_w


class SearchResultsAdapter() : RecyclerView.Adapter<SearchResultsAdapter.ForecastViewHolder>() {


    private val daysinfo: MutableList<Day_w>


    interface OnItemClickListener {
        fun onItemClick(v: View, position: Int)
    }

    init {
        daysinfo = ArrayList()
    }

    fun setData(days: List<Day_w>) {
        Objects.requireNonNull(days)
        daysinfo.clear()
        daysinfo.addAll(days)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val rootView = LayoutInflater.from(parent.context).inflate(R.layout.forecast_search_item,
                parent, false)
        val viewholder = ForecastViewHolder(rootView)

        return viewholder
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        holder.bind(daysinfo[position])
    }

    override fun getItemCount(): Int {
        return daysinfo.size
    }

    class ForecastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @Nullable
        @BindView(R.id.s_day)
        lateinit var DateAndDay: TextView
        @Nullable
        @BindView(R.id.r_condition)
        lateinit var condition: TextView
        @BindView(R.id.s_clouds)
        lateinit var clouds: TextView
        @BindView(R.id.s_maxmintemp)
        lateinit var temparature: TextView
        @BindView(R.id.s_wind)
        lateinit var wind: TextView
        @BindView(R.id.iv_weather_icon)
        lateinit var weatherIcon: ImageView

        private val resources: Resources

        init {
            ButterKnife.bind(this, itemView)
            resources = itemView.context.resources
        }

        fun bind(day: Day_w) {
            Picasso.get().load(Constants.ICON_BASE_URL + (day.icon) + Constants.ICON_EXTENSION)
                    .into(weatherIcon)
            DateAndDay.text = day.dateDay
            condition.text = day.condition
            clouds.text = day.clouds.toString()
            val tempMin = day.minTemp
            val tempMax = day.maxTemp
            temparature.text = "$tempMin - $tempMax"
            wind.text = resources.getString(R.string.wind_speed,
                    Utils.roundDoubleToTwoDecimalPoints(day.wind?.speed))

        }


    }
}
