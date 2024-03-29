package com.example.ForecastApp.mvp.MainScreenFragment

import android.util.Log
import com.example.ForecastApp.Activities.HomeActivity
import com.example.ForecastApp.Fragments.OnLocationSelectedListener
import com.example.minimoneybox.model.WeatherDetailUseCase


//this fragment is made up of the search bar aswell as a ListView containing recent searches, once a search has been
//actioned, ie someone has chosen a city this screen will be replaced with my second fragment hich is a search results fragment

class MainScreenFragmentPresenter(private val myModelInteractor: WeatherDetailUseCase, private val context: HomeActivity) : MainScreenFragmentContract.Presenter {

    private var mListener: OnLocationSelectedListener? = null

    override fun getRecentForecasts(){

        myModelInteractor.getRecentForecasts()
    }
    override fun setSelectedLocation(location: String) {

        Log.e("presenter_main","insidesetlocation")

        checkIfListenerAttached()

        mListener?.onLocationSelected(location)
    }


    private fun checkIfListenerAttached() {
        if (mListener == null) {
            try {
                mListener = context as OnLocationSelectedListener
            } catch (e: ClassCastException) {
                throw ClassCastException("${context}must implement OnLocationSelectedListener")
            }
        }
    }

    override fun stop() {
       myModelInteractor.stop()
    }




}
