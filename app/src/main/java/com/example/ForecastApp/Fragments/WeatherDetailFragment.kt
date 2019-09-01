package com.example.ForecastApp.Fragments

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.content.Context
import android.os.Bundle
import android.util.Log

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import butterknife.Unbinder
import com.example.ForecastApp.DI.Dagger_Weather_Feature.DetailFeatureModule
import com.example.ForecastApp.R
import com.example.ForecastApp.adapter.WeatherDetailAdapter
import com.example.ForecastApp.application.App
import com.example.ForecastApp.model.Objects.Main_Elements.Day
import com.example.ForecastApp.mvp.MainScreenFragment.DetailFragmentContract
import com.example.ForecastApp.mvp.MainScreenFragment.MainActivityContract
import java.lang.ClassCastException
import javax.inject.Inject



class WeatherDetailFragment : Fragment(), DetailFragmentContract.View {

    @Inject
    lateinit var presenter: DetailFragmentContract.Presenter

    private val forecastAdapter = WeatherDetailAdapter()

    private lateinit var activityContext: Context

    lateinit var location : String

    var day: Int = 0

    var binder: Unbinder? = null

    @BindView(R.id.search_button)
    lateinit var searchb: ImageView
    @BindView(R.id.detail_results)
    lateinit var recyclerView: RecyclerView
    @BindView(R.id.detail_progress)
    lateinit var progressBar: ProgressBar
    @BindView(R.id.detail_try_again)
   lateinit var tryAgain: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        injectDependencies()
        val view =inflater.inflate(R.layout.forecast_detail_f,container,false)
        binder = ButterKnife.bind(this,view)
        location = arguments?.getString("Location").toString()
            recyclerView.layoutManager = LinearLayoutManager(activityContext)
            recyclerView.adapter = forecastAdapter
        presenter.getDayDetails(location)
        val b = activityContext as MainActivityContract.View
        searchb.setOnClickListener {
            Log.e("sclick","button clicked ")
            b.showSearchResultsFragment(location)

        }
        return view

    }
    override fun injectDependencies() {

       App.instance.component.plus(DetailFeatureModule(this)).inject(this)

    }

    override fun onDetach() {
        super.onDetach()
        presenter.detatchView()
    }

    override fun showNoResults() {

        Log.e("WeatherDetailNoResults","no recent searches to display")
    }

    override fun showError(error: Throwable?) {

        error?.printStackTrace()
        val myActivityView = activityContext as MainActivityContract.View

        myActivityView.showError(error)
    }

    override fun showResults(days: List<*>) {
        try {
            forecastAdapter.setData(days as List<Day>)
        }
        catch(exception : ClassCastException){
            Log.e("ClassCastException", "exception casting in weather detail fragment")
        }
        presenter.stop()

    }


    override fun showProgress(shouldShow: Boolean) {
        progressBar.visibility = if (shouldShow) View.VISIBLE else View.GONE
    }

    override fun setActivityTitle(name: String?){

    }

    override fun showTryAgain(shouldShow: Boolean) {
        if (shouldShow) {
            recyclerView.visibility = View.GONE
            tryAgain.visibility = View.VISIBLE
        } else {
            tryAgain.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        }
    }

    @OnClick(R.id.detail_try_again)
    fun onTryAgainClicked() {
      // presenter.getWeatherDetails()
    }


    fun newInstance(location: String): WeatherDetailFragment{
        val detailFragment = WeatherDetailFragment()
        val bundle = Bundle().apply {
            putString("Location", location)
        }
       detailFragment.arguments = bundle

        return detailFragment
    }
}





