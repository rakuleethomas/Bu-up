package org.rakulee.buup

import androidx.test.platform.app.InstrumentationRegistry
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.rakulee.buup.repo.BuupAPIRepo
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode
import org.robolectric.shadows.ShadowLog
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltAndroidTest
@Config(application = HiltTestApplication::class)
@RunWith(RobolectricTestRunner::class)
@LooperMode(LooperMode.Mode.PAUSED)
class BuupJobSeekerAPITest : CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    val context = InstrumentationRegistry.getInstrumentation().targetContext

    @get:Rule
    var hiltAndroidRule = HiltAndroidRule(this)

    @Before
    fun setUp() {
        hiltAndroidRule.inject()
        ShadowLog.stream = System.out
    }

    @Inject
    lateinit var buupRepo : BuupAPIRepo
    @Inject
    lateinit var okHttpClient: OkHttpClient

    @Test
    fun getMapDetailView(){

    }
}