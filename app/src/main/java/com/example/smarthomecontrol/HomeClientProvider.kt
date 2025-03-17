package com.example.smarthomecontrol

import android.content.Context
import com.google.home.Home
import com.google.home.HomeClient
import com.google.home.HomeConfig

// Singleton class to ensure Home.getClient is called only once
object HomeClientProvider {

    var homeClient: HomeClient? = null

    fun getClient(context: Context, homeConfig: HomeConfig): HomeClient {

        if (homeClient == null)
            homeClient = Home.getClient(context = context, homeConfig = homeConfig)

        return homeClient!!
    }
}
