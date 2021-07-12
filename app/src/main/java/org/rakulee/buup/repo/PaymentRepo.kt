package org.rakulee.buup.repo

import dagger.Component
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import okhttp3.ResponseBody
import org.rakulee.buup.model.ChargeResult
import org.rakulee.buup.network.service.SquareAPI
import org.rakulee.buup.viewmodel.PaymentViewModel
import retrofit2.Call
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PaymentRepo @Inject constructor(
    private val squareAPI : SquareAPI,
    private val retrofit: Retrofit
) {

    var errorConverter : Converter<ResponseBody, SquareAPI.ChargeErrorResponse>

    init {
        val noAnnotations = arrayOf<Annotation>()
        val errorResponseType: Type = SquareAPI.ChargeErrorResponse::class.java
        errorConverter = retrofit.responseBodyConverter(errorResponseType, noAnnotations)
    }

    fun callPaymentInBackground(nonce: String, points: String) : Call<ChargeResult> {
        return squareAPI.charge( SquareAPI.ChargeRequest(nonce, points))
    }
}