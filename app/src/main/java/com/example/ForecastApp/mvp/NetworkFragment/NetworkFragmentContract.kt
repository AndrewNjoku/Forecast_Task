package com.example.ForecastApp.mvp.MainScreenFragment

import android.content.Intent
import com.example.ForecastApp.mvp.BaseContract


interface NetworkFragmentContract {

    interface View :BaseContract.View{
        //no view

    }

    interface Presenter : BaseContract.Presenter<View>{

    }
}
