package org.rakulee.buup

import android.app.Application
import com.parse.Parse
import com.parse.ParseObject
import dagger.hilt.android.HiltAndroidApp
import org.rakulee.buup.model.Job
import org.rakulee.buup.screens.LoginActivity
import org.rakulee.buup.network.service.CardEntryBackgroundHandler
import sqip.CardEntry.setCardNonceBackgroundHandler

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        ParseObject.registerSubclass(Job::class.java)
        Parse.initialize(Parse.Configuration.Builder(this)
            .applicationId("aVQUhSqemZDmI8n0mWYOSPzWBAlPb4YPUCIbtH6Q") // should correspond to Application ID env variable
            .clientKey("hrcIMfNQZESdJVFY5H3g11rkvQK9d5jRWruyQjfA")  // should correspond to Client key env variable
            .server("https://parseapi.back4app.com").build())


        val cardHandler = CardEntryBackgroundHandler()
        setCardNonceBackgroundHandler(cardHandler)
    }
}