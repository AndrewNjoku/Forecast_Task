package com.example.ForecastApp.mvp.DetailFragment


import com.example.minimoneybox.model.WeatherDetailUseCase

class DetailFragmentPresenter(private val modelInteractor: WeatherDetailUseCase) : DetailFragmentContract.Presenter {

    override fun getDayDetails(location: String) {
        modelInteractor.getForecastDayDetails(location)
    }

    override fun stop(){
        modelInteractor.stop()
    }




}
