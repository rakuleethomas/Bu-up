package org.rakulee.buup

import android.location.Geocoder
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.ResponseBody

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.rakulee.buup.model.BuupGetJobPostingByDistanceResponse
import org.rakulee.buup.model.JobPostingItem
import org.rakulee.buup.module.NetworkModule
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.CountDownLatch
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
            val res = NetworkModule.providesBuupTestAPIService(retrofit).queryUserInfo("doe").body()
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
            print(resultAddressList[0].latitude )
            print(resultAddressList[0].longitude)
            val latLng = LatLng(resultAddressList[0].latitude, resultAddressList[0].longitude)
            val addPostingItem = JobPostingItem("Santa Clara", "Amazon", "Warehouse Part-Time Worker",
            resultAddressList[0].latitude, false, "", resultAddressList[0].longitude, "58", "20", "1222")
            val res = NetworkModule.provideBuupJobPostingService(retrofit).addJobPosting(
                addPostingItem
            )
            print(res)


        }
        countDownLatch.await()
    }

    @Test
    fun sampleGetJobPostingQuery(){
        val countDownLatch = CountDownLatch(1)
        launch {
            val retrofit = NetworkModule.providesBuupRetrofit(Configs.BUUP_BASE_URL)
            val appContext = InstrumentationRegistry.getInstrumentation().targetContext
            val geoCoder = Geocoder(appContext)
            val resultAddressList = geoCoder.getFromLocationName("95051", 5)

            val latitude = resultAddressList[0].latitude
            val longitude = resultAddressList[0].longitude
            val latLng = LatLng(resultAddressList[0].latitude, resultAddressList[0].longitude)

            val res = NetworkModule.provideBuupGetJobPostingByDistanceAPIService(retrofit).queryJobPosting(
                latitude, longitude, "10.0"
            )
            val body : BuupGetJobPostingByDistanceResponse = res.body()!!
            for(item in body){
                print(item.city)
                print(item.companyName)
                print(item.jobTitle)
                print(item.latitude)
                print(item.liked)
                print(item.logo)
                print(item.payRateHigh)
                print(item.postId)
                print(item.longitude)

            }



        }
        countDownLatch.await()
    }
}