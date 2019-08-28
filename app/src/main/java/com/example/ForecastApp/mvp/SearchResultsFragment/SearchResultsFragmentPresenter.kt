package com.example.ForecastApp.mvp.MainScreenFragment

import android.content.Context
import com.example.ForecastApp.DataBank.Utils
import com.example.minimoneybox.model.ApplicationModelContract


//this fragment is made up of the search bar aswell as a recyclerview containing recent searches, once a search has been
//actioned, ie someone has chosen a city this screen will be replaced with my second fragment hich is a search results fragment

//TODO pass activity context to presenter in compnent
class SearchResultsFragmentPresenter(private val myModelInteractor: ApplicationModelContract) : SearchResultsFragmentContract.Presenter {

  lateinit var activityContext: Context
    lateinit var myView : SearchResultsFragmentContract.View

    override fun attach(context: Context,fragview: SearchResultsFragmentContract.View) {
        this.activityContext=context
        this.myView=fragview
    }

    override fun showSearchResults(location: String ){

        val viewfromcontext = activityContext as MainActivityContract.View

       // viewfromcontext.showSearchResultsFragment(location)

        myModelInteractor.getForecastShowInSearchResults(Utils.isOnline(activityContext),location, myView)

        //TODO display the search results for the location

    }



    override fun detatchView() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }




}
