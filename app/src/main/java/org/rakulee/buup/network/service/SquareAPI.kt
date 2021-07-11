package org.rakulee.buup.network.service

import org.rakulee.buup.model.ChargeResult
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST


interface SquareAPI {

    @POST("/chargePoints")
    fun charge(@Body request: ChargeRequest): Call<ChargeResult>

    class ChargeErrorResponse {
        var errorMessage: String? = null
    }

    class ChargeRequest (val nonce: String, val points: String)// retrofit request body value


}