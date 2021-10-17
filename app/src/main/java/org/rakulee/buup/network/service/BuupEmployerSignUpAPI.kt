package org.rakulee.buup.network.service

import okhttp3.RequestBody
import org.rakulee.buup.model.EmployerSignUpResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface BuupEmployerSignUpAPI {
    @POST("EmployerSignUp")
    suspend fun employerSignUp(
        @Body requestBody: RequestBody
    ) : Response<EmployerSignUpResponse>
}