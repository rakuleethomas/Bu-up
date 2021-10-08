package org.rakulee.buup.repo

import okhttp3.ResponseBody
import org.rakulee.buup.model.ChargeResult
import org.rakulee.buup.network.service.BuupTestAPI
import retrofit2.Call
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BuupTestRepo @Inject constructor(
    private val buupTestAPI: BuupTestAPI,
    private val retrofit: Retrofit
) {

}