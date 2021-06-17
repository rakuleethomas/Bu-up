package org.rakulee.buup

import android.app.Application
import com.parse.Parse

class ParseApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // Use for troubleshooting -- remove this line for production
        Parse.setLogLevel(Parse.LOG_LEVEL_DEBUG)

        // Use for monitoring Parse OkHttp traffic
        // Can be Level.BASIC, Level.HEADERS, or Level.BODY
        // See https://square.github.io/okhttp/3.x/logging-interceptor/ to see the options.

        // set applicationId, and server server based on the values in the back4app settings.
        // any network interceptors must be added with the Configuration Builder given this syntax
        Parse.initialize(Parse.Configuration.Builder(this)
            .applicationId("aVQUhSqemZDmI8n0mWYOSPzWBAlPb4YPUCIbtH6Q") // should correspond to Application ID env variable
            .clientKey("hrcIMfNQZESdJVFY5H3g11rkvQK9d5jRWruyQjfA")  // should correspond to Client key env variable
                .server("https://parseapi.back4app.com").build());
    }
}