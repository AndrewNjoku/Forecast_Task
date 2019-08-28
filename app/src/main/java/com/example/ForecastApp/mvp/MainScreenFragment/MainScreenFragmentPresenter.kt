package com.example.ForecastApp.mvp.MainScreenFragment

import android.app.PendingIntent.getActivity
import android.content.Context
import com.example.ForecastApp.Activities.HomeActivity
import com.example.ForecastApp.Fragments.OnLocationSelectedListener
import com.example.ForecastApp.model.Objects.Main_Elements.Forecast
import com.example.minimoneybox.model.ApplicationModelContract


//this fragment is made up of the search bar aswell as a ListView containing recent searches, once a search has been
//actioned, ie someone has chosen a city this screen will be replaced with my second fragment hich is a search results fragment

class MainScreenFragmentPresenter(private val myModelInteractor: ApplicationModelContract) : MainScreenFragmentContract.Presenter {

    lateinit var context : Context
    lateinit var fragView: MainScreenFragmentContract.View

    override fun attach(context: Context, fragView: MainScreenFragmentContract.View) {
        this.activityContext = context
        this.fragView=fragView


            myModelInteractor.getRecentForecasts(fragView)

    }
    override fun getWeatherDetails() {

    }

    override fun detatchView() {

    }

    private var mListener: OnLocationSelectedListener? = null
    lateinit var activityContext: Context

    override fun setSelectedLocation(location: String) {

        checkIfListenerAttached()

        mListener?.onLocationSelected(location)
    }

    private fun checkIfListenerAttached() {
        if (mListener == null) {
            try {
                mListener = activityContext as HomeActivity
            } catch (e: ClassCastException) {
                throw ClassCastException("$activityContext must implement OnLocationSelectedListener")
            }
        }
    }

    override fun getRecentForecasts(){

        myModelInteractor.getRecentForecasts(fragView)
    }

}
