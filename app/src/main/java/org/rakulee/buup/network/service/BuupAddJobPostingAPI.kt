package org.rakulee.buup.network.service


import okhttp3.ResponseBody
import org.rakulee.buup.model.BuupAddJobPostingResponse
import org.rakulee.buup.model.BuupTestResponse
import org.rakulee.buup.model.JobPostingItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.*

interface BuupAddJobPostingAPI {
    @Headers("Content-Type:application/json")
    @POST("addJobPosting")
    suspend fun addJobPosting( @Body jobPostingItem: JobPostingItem
    ) : Response<BuupAddJobPostingResponse>
}