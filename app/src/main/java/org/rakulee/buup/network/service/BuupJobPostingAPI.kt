package org.rakulee.buup.network.service

import org.rakulee.buup.model.BuupTestResponse
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.*

interface BuupJobPostingAPI {
    @Headers("Content-Type:applcation/json")
    @FormUrlEncoded
    @POST("/dev/")
    suspend fun addJobPosting(
        @Field("jobTitle") jobTitle : String,
        @Field("companyName") companyName : String,
        @Field("logo") logo : String,
        @Field("payRateLow") payRatelow : String,
        @Field("payRateHigh") payRateHigh : String,
        @Field("city") city : String,
        @Field("distance") distance : Double,
        @Field("liked") liked : Boolean,
        @Field("longitude") longitude : Double,
        @Field("langitude") langitude : Double
    ) : Callback<String>
}