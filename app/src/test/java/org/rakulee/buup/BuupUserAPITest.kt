package org.rakulee.buup


import androidx.test.platform.app.InstrumentationRegistry
import com.google.gson.JsonObject
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.android.testing.CustomTestApplication
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.rakulee.buup.helper.ApiHelper
import org.rakulee.buup.model.JobSeekerSignInResponse
import org.rakulee.buup.module.NetworkModule
import org.rakulee.buup.repo.BuupAPIRepo
import retrofit2.Response
import java.util.concurrent.CountDownLatch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext
import org.robolectric.annotation.Config
import org.robolectric.RobolectricTestRunner


@HiltAndroidTest
@Config(application = HiltTestApplication::class)
@RunWith(RobolectricTestRunner::class)
class BuupUserAPITest : CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    val context = InstrumentationRegistry.getInstrumentation().targetContext

    @get:Rule var hiltRule = HiltAndroidRule(this)
//
    @Before
    fun init(){
        hiltRule.inject()
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
}