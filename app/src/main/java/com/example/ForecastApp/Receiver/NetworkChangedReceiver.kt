package com.example.ForecastApp.Receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.ForecastApp.Fragments.NetworkHelper

class NetworkChangedReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        //create a new intent to check the internet connection
        val `in` = Intent(NetworkHelper.CHECK_INTERNET)

        //fill the intent with the Bool result of NetworkHelper.isInternetConnected(context)
        `in`.putExtra(NetworkHelper.CHECK_INTERNET, NetworkHelper.isInternetConnected(context))

        //send out the broadcast to be received by the NetworkHelper fragment running in the background
        LocalBroadcastManager.getInstance(context).sendBroadcast(`in`)
    }
}
