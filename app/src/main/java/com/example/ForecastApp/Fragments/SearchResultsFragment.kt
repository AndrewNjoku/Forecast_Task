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
import com.example.ForecastApp.Activities.HomeActivity
import com.example.ForecastApp.DI.Dagger_Weather_Feature.SearchFeatureModule
import com.example.ForecastApp.R
import com.example.ForecastApp.adapter.SearchResultsAdapter
import com.example.ForecastApp.application.App
import com.example.ForecastApp.model.Objects.Main_Elements.Day
import com.example.ForecastApp.mvp.MainScreenFragment.MainActivityContract
import com.example.ForecastApp.mvp.MainScreenFragment.SearchResultsFragmentContract
import java.lang.ClassCastException
import javax.inject.Inject



class SearchResultsFragment : Fragment(),SearchResultsFragmentContract.View{

    lateinit var forecastAdapter: SearchResultsAdapter

    @Inject
    lateinit var presenter: SearchResultsFragmentContract.Presenter

    private lateinit var activityContext: Context

    var binder: Unbinder? = null

    lateinit var location : String

    @BindView(R.id.detail_button)
    lateinit var detailb: ImageView
    @BindView(R.id.search_results)
    lateinit var searchresults: RecyclerView
    @BindView(R.id.search_progress)
    lateinit var progressBar: ProgressBar
    @BindView(R.id.search_try_again)
   lateinit var tryAgain: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        injectDependencies()
        val view =inflater.inflate(R.layout.search_results_frame,container,false)
        binder = ButterKnife.bind(this,view)

        location = arguments?.getString("Location").toString()
        val a =activityContext as HomeActivity
       val bar = a.supportActionBar
        bar?.title =location
        forecastAdapter= SearchResultsAdapter()
            searchresults.layoutManager = LinearLayoutManager(activityContext)
            searchresults.adapter = forecastAdapter
        //retrieve the location of the city thats passed in the bundle wehen we created this fragment
        presenter.showSearchResults(location)
         val b = activityContext as MainActivityContract.View
        detailb.setOnClickListener {
            Log.e("sclick","button clicked ")
            b.showDetailsFragment(location)

        }
        return view
    }
    override fun injectDependencies() {

       App.instance.component.plus(SearchFeatureModule(this,activity as Context)).inject(this)
    }

    override fun onDetach() {
        super.onDetach()
        presenter.detatchView()
    }

    override fun showResults(days: List<*>) {
        try {
            forecastAdapter.setData(days as List<Day>)
        }
        catch (exception: ClassCastException)
        {
            Log.e("CLASSCASTEXCEPTION",exception.toString())
        }
        presenter.stop()
    }

    override fun showNoResults() {
      Log.e("DATA","No data to update")
    }

    override fun showError(throwable: Throwable?) {
        throwable?.printStackTrace()
        val myActivityView = activityContext as MainActivityContract.View

        myActivityView.showError(throwable!!)
    }

    override fun showProgress(shouldShow: Boolean) {
        progressBar.visibility = if (shouldShow) View.VISIBLE else View.GONE
    }


    //show the title of the city we are displaying weather results for
    override fun setActivityTitle(name: String?){

    }

    override fun showTryAgain(shouldShow: Boolean) {
        if (shouldShow) {
            searchresults.visibility = View.GONE
            tryAgain.visibility = View.VISIBLE
        } else {
            tryAgain.visibility = View.GONE
            searchresults.visibility = View.VISIBLE
        }
    }
    @OnClick(R.id.search_try_again)
    fun onTryAgainClicked() {
        presenter.showSearchResults(location)
    }

    //here we create a new searchFragment and we are passing the location via a bundle
    //this will allow us to initiate the search based on the location and display in the fragments recyclerview

    fun newInstance(location:String): SearchResultsFragment{

        Log.e("SearchResults","creating fragment")

        val searchResultsFragment = SearchResultsFragment()
        val bundle = Bundle().apply {
            putString("Location", location)
        }
        searchResultsFragment.arguments = bundle

        return searchResultsFragment
    }
}





