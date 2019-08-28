package com.example.ForecastApp.adapter

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


class SearchResultsAdapter(private var listener: OnItemClickListener, private val location: String) : RecyclerView.Adapter<SearchResultsAdapter.ForecastViewHolder>() {


    private val daysinfo: MutableList<Day>

    interface OnItemClickListener : View.OnClickListener {
        override fun onClick(p0: View?)
        fun onItemClick(item: Day, location: String)
    }

    init {
        daysinfo = ArrayList()
    }

    fun setData(days: List<Day>) {
        Objects.requireNonNull(days)
        daysinfo.clear()
        daysinfo.addAll(days)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val rootView = LayoutInflater.from(parent.context).inflate(R.layout.forecast_search_item,
                parent, false)
        return ForecastViewHolder(rootView)
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        holder.bind(daysinfo[position],listener)
    }

    override fun getItemCount(): Int {
        return daysinfo.size
    }

    class ForecastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @BindView(R.id.search_results_card)
        lateinit var cardView: CardView
        lateinit var condition: TextView
        @BindView(R.id.s_clouds)
        lateinit var clouds: TextView
        @BindView(R.id.s_maxmintemp)
        @Nullable
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

        fun bind(day: Day,listener:OnItemClickListener) {
            Picasso.get().load(Constants.ICON_BASE_URL + (day.weather?.get(0)?.icon) + Constants.ICON_EXTENSION)
                    .into(weatherIcon)
           // dayAndTime.text = Utils.getDateForLocaleFromUtc(day.dateAndTime)
            condition.text = day.weather?.get(0)?.description?.toUpperCase()
            clouds.text = resources.getString(R.string.cloud_percentage, day.clouds?.all)
            temparature.text = Utils.getCelsiusFromKelvin(day.main?.temp)
            wind.text = resources.getString(R.string.wind_speed,
                    Utils.roundDoubleToTwoDecimalPoints(day.wind?.speed))
            cardView.setOnClickListener(listener)
        }


    }
}
