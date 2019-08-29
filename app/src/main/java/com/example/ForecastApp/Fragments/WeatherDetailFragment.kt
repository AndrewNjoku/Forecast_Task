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
import com.example.ForecastApp.DI.Dagger_Composer.WeatherFeatureModule
import com.example.ForecastApp.DataBank.Utils
import com.example.ForecastApp.R
import com.example.ForecastApp.adapter.WeatherDetailAdapter
import com.example.ForecastApp.application.App
import com.example.ForecastApp.model.Objects.Main_Elements.Day
import com.example.ForecastApp.mvp.MainScreenFragment.DetailFragmentContract
import com.example.ForecastApp.mvp.MainScreenFragment.MainActivityContract
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
        presenter.attach(activityContext,this)
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


    override fun showForecast(days: List<Day>) {

      forecastAdapter.setData(days)
    }

    override fun injectDependencies() {

       App.instance.component.plus(WeatherFeatureModule()).inject(this)

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activityContext=context

    }

    override fun showError(throwable: Throwable) {
        throwable.printStackTrace()
        val myActivityView = activityContext as MainActivityContract.View

        myActivityView.showError(throwable)
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

    override fun onStop() {
        super.onStop()
      //  presenter.stop()
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





