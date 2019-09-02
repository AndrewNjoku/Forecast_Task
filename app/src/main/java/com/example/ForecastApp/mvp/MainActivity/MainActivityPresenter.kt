package com.example.ForecastApp.mvp.MainActivity

import android.content.Context
import com.example.ForecastApp.Activities.HomeActivity
import com.example.ForecastApp.Fragments.NetworkHelper

class MainActivityPresenter(private val context: Context) : MainActivityContract.Presenter {

    private val myActivity = context as HomeActivity

    private var mNetworkHelper: NetworkHelper? = null

    override fun initiateNetworkFragment() {
        //when we create the mainActivity we check if the network helper fragment has been created (invisible)
        //this helps with network checks. If it is null then we create a new instance in the activity.
        //Check for network connectivity
        mNetworkHelper = myActivity.supportFragmentManager.findFragmentByTag(NetworkHelper.TAG) as NetworkHelper?
        if (mNetworkHelper == null) {
            mNetworkHelper = NetworkHelper.newInstance()
            myActivity.supportFragmentManager.beginTransaction().add(mNetworkHelper!!, NetworkHelper.TAG).commit()
        }
    }

    override fun stop() {
        mNetworkHelper=null
    }





}
