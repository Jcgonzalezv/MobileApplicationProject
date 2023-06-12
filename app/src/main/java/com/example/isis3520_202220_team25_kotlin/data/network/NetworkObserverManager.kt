package com.example.isis3520_202220_team25_kotlin.data.network

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkInfo
import android.os.Build
import androidx.lifecycle.LiveData


class NetworkObserverManager (private val context: Context):LiveData<Boolean>() {

    private val connectivityManager : ConnectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private lateinit var networkConnectionCallback: ConnectivityManager.NetworkCallback

    override fun onActive() {
        super.onActive()
        updateNetworkConnection()
        when{
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.N ->{
                connectivityManager.registerDefaultNetworkCallback(connectionCallback())
            }else ->{
                context.registerReceiver(networkReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
            }
        }
    }

    override fun onInactive() {
        super.onInactive()
        if (connectionCallback() != null && connectivityManager != null) {
            try {
                connectivityManager.unregisterNetworkCallback(connectionCallback())
                // Added code: Set call back to null so this doesn't get called again
            } catch (e: Exception) {
                //failed
            }
        }

    }

    private fun connectionCallback(): ConnectivityManager.NetworkCallback{
        networkConnectionCallback = object : ConnectivityManager.NetworkCallback(){
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                postValue(true)
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                postValue(false)
            }
        }
        return  networkConnectionCallback
    }

    private fun updateNetworkConnection(){
        val networkConnection: NetworkInfo? = connectivityManager.activeNetworkInfo
        postValue(networkConnection?.isConnected == true)
    }

    private val networkReceiver = object : BroadcastReceiver(){
        override fun onReceive(p0: Context?, p1: Intent?) {
            updateNetworkConnection()
        }
    }
}