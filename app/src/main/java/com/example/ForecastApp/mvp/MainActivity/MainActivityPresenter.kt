package com.example.ForecastApp.mvp.MainScreenFragment

import android.content.Context
import com.example.ForecastApp.Activities.HomeActivity
import com.example.ForecastApp.Fragments.NetworkHelper

class MainActivityPresenter(private val context: Context) : MainActivityContract.Presenter {

    val activityView = context as HomeActivity

    override fun attach(context: Context) {
        activityView.showMainPageFragment()
    }

    override fun attachSearchResultsFrag(location: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun attachSearchResultsFrag(location: Long) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    val myActivity = context as HomeActivity

    internal var mNetworkHelper: NetworkHelper? = null

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



    override fun start() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun stop() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun detatchView() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }





}
