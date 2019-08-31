package com.example.ForecastApp.mvp.MainScreenFragment

import android.content.Context
import com.example.minimoneybox.model.ApplicationModelContract

class DetailFragmentPresenter(private val modelInteractor: ApplicationModelContract) : DetailFragmentContract.Presenter {


    override fun getDayDetails(location: String) {
        modelInteractor.getForecastDayDetails(location)
    }

    override fun detatchView() {
       modelInteractor.stop()
    }




}
