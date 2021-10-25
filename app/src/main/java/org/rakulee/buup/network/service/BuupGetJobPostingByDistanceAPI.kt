package org.rakulee.buup.network.service

import org.rakulee.buup.model.BuupGetJobPostingByDistanceResponse
import org.rakulee.buup.model.BuupTestResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface BuupGetJobPostingByDistanceAPI {
    @Headers("Content-Type:application/json")
    @GET("getJobPostingByDistance")
    suspend fun queryJobPosting(
        @Query("latitude") latitude : Double,
        @Query("longitude") longitude : Double,
        @Query("distance") distance : Int
    ) : Response<BuupGetJobPostingByDistanceResponse>
}