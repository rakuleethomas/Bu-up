package org.rakulee.buup


import android.util.Log
import androidx.test.platform.app.InstrumentationRegistry
import com.google.gson.Gson
import com.google.gson.JsonObject
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.toRequestBody
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.rakulee.buup.model.BuupEmployerProfile
import org.rakulee.buup.module.NetworkModule
import org.rakulee.buup.repo.BuupAPIRepo
import org.rakulee.buup.util.Util
import org.rakulee.buup.util.Util.decodeHex
import org.rakulee.buup.util.Util.decryptPassword
import org.rakulee.buup.util.Util.toHex
import java.util.concurrent.CountDownLatch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext
import org.robolectric.annotation.Config
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.LooperMode
import java.nio.charset.StandardCharsets


@HiltAndroidTest
@Config(application = HiltTestApplication::class)
@RunWith(RobolectricTestRunner::class)
@LooperMode(LooperMode.Mode.PAUSED)
class BuupUserAPITest : CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    val context = InstrumentationRegistry.getInstrumentation().targetContext

    @get:Rule var hiltAndroidRule = HiltAndroidRule(this)
//
    @Before
    fun setUp() {
        hiltAndroidRule.inject()
    }

    @Inject lateinit var buupRepo : BuupAPIRepo
    @Inject lateinit var okHttpClient: OkHttpClient

    @Test
    fun signIn(){
        val countDownLatch = CountDownLatch(1)
        launch {
            val jsonObject = JsonObject()
            jsonObject.addProperty("loginId", "john.snow@email.com")
            jsonObject.addProperty("password", "q1w2e3r4t5")
            val jsonString = jsonObject.toString()
            val requestBody = jsonString.toRequestBody("application/json".toMediaTypeOrNull())
            val result = buupRepo.jobSeekerSignIn(requestBody)
            print(result.body())
        }
        countDownLatch.await()
    }


    /**
     * data class BuupEmployerProfile(
    var userId : String = "",
    var firstName : String = "",
    var lastName : String = "",
    var email : String = "",
    var loginId : String = "",
    var password : String = "",      // hash
    var verified : Boolean = false,
    var photoUrl : String = "",
    var socialMedia : String = "",
    var timestamp : String = "",
    var companyInfo : CompanyInfo = CompanyInfo()
    ){
    data class CompanyInfo(
    var name : String = "",
    var logoUrl : String = "",
    var address1 : String = "",
    var address2 : String = "",
    var city : String = "",
    var zipCode : String = "",
    var state : String = "",
    var description : String = "",
    var companySize : String = "",
    var budget : String = "",
    var industry : ArrayList<String> = ArrayList<String>()
    )
    }
     */


    @Test
    fun employerSignUp(){
        val countDownLatch = CountDownLatch(1)
        launch {
            val jsonObject = JsonObject()
            val encryptedPassword = Util.encryptPassword("1234".toCharArray())
            val industryList = ArrayList<String>()
            industryList.add("Non-Profit/Volunteering")
            val companyInfo = BuupEmployerProfile.CompanyInfo("Rakulee, Inc.",
                "","155 E. Campbell Ave.","Suite #111","Campbell",
                "95008","CA","","1 to 25",
                "$10000", industryList)
            val gson = Gson()

            val encryptedHexString = encryptedPassword.toHex()
            val decriptByteArray = encryptedHexString.decodeHex()
            if(encryptedPassword.contentEquals(decriptByteArray)){
                print("true")
            }

            jsonObject.addProperty("loginId", "test5@gmail.com")
            jsonObject.addProperty("password", encryptedPassword.toHex())
            jsonObject.addProperty("timestamp", System.currentTimeMillis())
            jsonObject.add("companyInfo", gson.toJsonTree(companyInfo))
            val jsonString = jsonObject.toString()
            val requestBody = jsonString.toRequestBody("application/json".toMediaTypeOrNull())
            val result = buupRepo.employerSignUp(requestBody)
            print(result.body())
            Log.d("TEST", "employerSignUp: ${jsonString}")
//            val result = buupRepo.jobSeekerSignIn(requestBody)
//            print(result.body())
        }
        countDownLatch.await()
    }
}