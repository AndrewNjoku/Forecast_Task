package com.example.ForecastApp.Fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.Unbinder
import com.example.ForecastApp.Activities.HomeActivity
import com.example.ForecastApp.DI.Dagger_Main.MainPresenterModule
import com.example.ForecastApp.R
import com.example.ForecastApp.adapter.RecentSearchesAdapter
import com.example.ForecastApp.adapter.SearchAutoCompleteAdapter
import com.example.ForecastApp.application.App
import com.example.ForecastApp.model.Objects.Main_Elements.Forecast
import com.example.ForecastApp.model.Objects.Predicitions.Prediction
import com.example.ForecastApp.mvp.MainActivity.MainActivityContract
import com.example.ForecastApp.mvp.MainScreenFragment.MainScreenFragmentContract
import com.example.ForecastApp.widget.DelayAutoCompleteTextView
import java.lang.ClassCastException

import javax.inject.Inject


class MainScreenFragment : Fragment(), MainScreenFragmentContract.View {


    @Inject
    lateinit var presenter: MainScreenFragmentContract.Presenter

    private val THRESHOLD = 3 //minimum chars before search

    private var unbinder: Unbinder? = null

    lateinit var savedSearchesAdapter : RecentSearchesAdapter

    //This fragment consists of a search bar with autocomplete suggestions for coty
    //and a progress bar
    //There is a recycleview listing the five day weather for the city selected

    @BindView(R.id.txt_search)
    lateinit var mSearchView: DelayAutoCompleteTextView
    @BindView(R.id.mainpage_loading_indicator)
    lateinit var mProgress: ProgressBar
    @BindView(R.id.mainpage_results)
    lateinit var mSavedResults: ListView
    @BindView(R.id.btn_clear)
    lateinit var mCancel:View


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        injectDependencies()
       val view = inflater.inflate(R.layout.weather_mainpage_frame, container, false)
       unbinder = ButterKnife.bind(this, view)
        //initialise the adapter logic for the autoCOmplete text search and the recent searches list
        savedSearchesInit()
        autoCompleteSearchInit()
        presenter.getRecentForecasts()


        // clear search text
        mCancel.setOnClickListener {
            mSearchView.setText("")
        }
        return view
    }

    override fun injectDependencies() {
        App.instance.component.plus(MainPresenterModule(this,activity as HomeActivity)).inject(this)
    }



    override fun savedSearchesInit() {

        savedSearchesAdapter= RecentSearchesAdapter(activity as Context)
        mSavedResults.adapter = savedSearchesAdapter
        mSavedResults.onItemClickListener = AdapterView.OnItemClickListener {

            parent, view, position, id ->
            val p = parent.getItemAtPosition(position) as Forecast
            val location = p.city.name

            val activityView = activity as MainActivityContract.View

            activityView.showSearchResultsFragment(location)

        }

    }

    override fun autoCompleteSearchInit() {
        //Autocomplete initialisation
        mSearchView.threshold = THRESHOLD //min chars before search
        mSearchView.setAdapter(SearchAutoCompleteAdapter(activity as Context))
        mSearchView.setLoadingIndicator(mProgress)
        mSearchView.onItemClickListener = AdapterView.OnItemClickListener {

            parent, view, position, id ->
            val p = parent.getItemAtPosition(position) as Prediction

            Log.e("Prediction", p.toString())

           presenter.setSelectedLocation(p.toString())

            //presenter.showSearchResults(p.name.toString())
            //  presenter.setSelectedLocation(p.toString())
        }
    }

    override fun showResults(forecasts: List<*>) {
        try {
            savedSearchesAdapter.setRecentForecasts(forecasts as List<Forecast>)
        }
        catch(exception: ClassCastException){
            Log.e("CLASSCASTEXCEPTION", exception.toString())
        }
    }


    override fun showError(error: Throwable?) {

        val activityView = activity as MainActivityContract.View

        activityView.showError(error)

    }

    override fun showNoInternet() {

       Toast.makeText(activity,"Please reconnect to the internet and try again",Toast.LENGTH_SHORT).show()
    }


    override fun showTryAgain(b: Boolean) {

        val activityView = activity as MainActivityContract.View


        activityView.showTryAgain(b)

    }


    override fun showNoResults() {
        Log.e("MainScreenRecentSearches","no recent searches to display")

    }


    //Show loading when fetching data , will be actioned by the onSubscribe() and onComplete in my model
    override fun showProgress(b: Boolean) {

        when(b){
          true ->  mProgress.visibility=View.VISIBLE
           false -> mProgress.visibility=View.INVISIBLE

        }
    }

    override fun onDetach() {
        super.onDetach()
        unbinder?.unbind()
      //  presenter.detatchView()
    }

}
interface OnLocationSelectedListener {
    fun onLocationSelected(location: String)
    fun onLocationSelected(locId: Long)
    fun showDetailFragment(dayPos: Int)
    fun showDetailsFragment(location: String)
}
