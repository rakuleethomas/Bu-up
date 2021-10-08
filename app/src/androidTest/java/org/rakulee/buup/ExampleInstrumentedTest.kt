package org.rakulee.buup

import android.location.Geocoder
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.rakulee.buup.module.NetworkModule
import org.rakulee.buup.network.service.BuupJobPostingAPI
import org.rakulee.buup.network.service.BuupTestAPI
import java.util.concurrent.CountDownLatch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest : CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO


    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("org.rakulee.buup", appContext.packageName)
    }

    @Test
    fun sampleQuery(){
        val countDownLatch = CountDownLatch(1)
        launch {
            val retrofit = NetworkModule.providesBuupRetrofit(Configs.BUUP_BASE_URL)
            val res = NetworkModule.providesBuupTestAPIService(retrofit).queryUserInfo("1Q").body()
            print(res)
        }
        countDownLatch.await()
    }

    @Test
    fun sampleUploadQuery(){
        val countDownLatch = CountDownLatch(1)
        launch {
            val retrofit = NetworkModule.providesBuupRetrofit(Configs.BUUP_BASE_URL)
            val appContext = InstrumentationRegistry.getInstrumentation().targetContext
            val geoCoder = Geocoder(appContext)
            val resultAddressList = geoCoder.getFromLocationName("95051", 5)
            val latLng = LatLng(resultAddressList[0].latitude, resultAddressList[0].longitude)
            val res = NetworkModule.provideBuupJobPostingService(retrofit).addJobPosting(
                "Warehouse Part-Time Worker", "Amazon", "https://www.amazon.com",
                "27", "51", "Santa Clara", 10.0, true, resultAddressList[0].latitude, resultAddressList[0].longitude
            )
            print(res)


        }
        countDownLatch.await()
    }
}