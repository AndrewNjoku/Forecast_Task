package com.example.ForecastApp.mvp.MainScreenFragment

import android.content.Context
import com.example.minimoneybox.model.ApplicationModelContract

class DetailFragmentPresenter(private val modelInteractor: ApplicationModelContract) : DetailFragmentContract.Presenter {

    lateinit var activityContext: Context
    lateinit var fragView: DetailFragmentContract.View

    override fun attach(context: Context,fragView: DetailFragmentContract.View) {
        this.activityContext=context
        this.fragView=fragView

    }
    override fun getDayDetails(location: String) {

        modelInteractor.getForecastDayDetails(location,fragView)


    }



    override fun detatchView() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }




}
