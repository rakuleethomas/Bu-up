package org.rakulee.buup.network.service

import org.rakulee.buup.model.BuupTestResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface BuupTestAPI {
    @Headers("Content-Type:application/json")
    @GET("users/{test}")
    suspend fun queryUserInfo(
        @Path("test") res : String
    ) : Response<BuupTestResponse>
}