package com.example.ForecastApp.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import com.example.ForecastApp.DataBank.Constants.AUTOCOMPLETE_API_URL
import com.example.ForecastApp.R
import com.example.ForecastApp.application.App
import com.example.ForecastApp.model.Objects.Predicitions.Prediction
import com.example.ForecastApp.model.Objects.Predicitions.Predictions
import com.google.gson.reflect.TypeToken
import com.koushikdutta.ion.Ion
import java.lang.ClassCastException
import java.net.URLEncoder
import java.util.ArrayList

class SearchAutoCompleteAdapter(private val mContext: Context) : BaseAdapter(), Filterable {

    private var resultList: List<Prediction> = ArrayList()

    override fun getCount(): Int {
        return resultList.size
    }
    override fun getItem(index: Int): Prediction {
        return resultList[index]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        if (convertView == null) {
            val inflater = mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(R.layout.simple_dropdown_item, parent, false)
        }
        (convertView!!.findViewById<View>(R.id.text1) as TextView).text = getItem(position).toString()
        return convertView
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults? {
                val filterResults = FilterResults()
                if (constraint != null) {
                    Log.e("Filter", "constraint text is not null")
                    val predictions = findLocation(mContext, constraint.toString())
                    // Assign the data to the FilterResults
                    filterResults.values = predictions.predictions
                    filterResults.count = predictions.count
                }
                //return result of the filter
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                if (results != null && results.count > 0) {
                    try {
                        resultList = results.values as List<Prediction>
                    }
                    //if cast fails return error
                    catch(exception:ClassCastException){
                        Log.e("AutoCompleteAdapater",exception.message.toString())
                    }
                    notifyDataSetChanged()
                } else {
                    notifyDataSetInvalidated()
                }
            }
        }
    }

    //based on the findLocation search, we get a list of predictions (Predictions)
    //this search is initialised when two or ore characters are enetered based on
    private fun findLocation(context: Context, queryText: String): Predictions {
        try {
            //convert the search text into a search url to make the RESTful call
            //again we use kotlins elvis operator to either return the corect string
            //or we get the predictions error object
            val url = getLocationSearchUrl(queryText) ?: return Predictions.ERROR
//blocking call, since perform filtering is called on a helper thread,
            //we don't need to do this asynchronously.
            val json = Ion.with(context)
                    .load(url).`as`(object : TypeToken<Predictions>() {
                    }).get()
            //return the prediction
            return json ?: Predictions.ERROR //Avoid returning nulls.
    } catch (e: Exception)
    {
        Log.e("AutoCompleteAdapater", e.message, e)
        return Predictions.ERROR
    }
    }

    companion object {
        private var URL: String? = null
        fun getLocationSearchUrl(queryText: String): String? {
            if (URL == null) {

                URL = AUTOCOMPLETE_API_URL + App.instance.getString(R.string.google_api_key)

            }
            try {
                return String.format(URL!!, URLEncoder.encode(queryText, "UTF-8"))
            } catch (e: Exception) {
                return null
            }

        }
    }
}
