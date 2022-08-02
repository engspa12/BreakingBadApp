package com.example.truelogicappchallenge.util

import android.content.Context
import android.net.ConnectivityManager
import javax.inject.Inject

class ValidatorImpl @Inject constructor(
    private val context: Context
): Validator {

    override fun isOnline(): Boolean {
        //Verify if there is internet connection, if so then update the screen with the news articles
        //Otherwise show the message there is no internet connection
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val activeNetwork = cm.activeNetworkInfo;
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting;
    }
}