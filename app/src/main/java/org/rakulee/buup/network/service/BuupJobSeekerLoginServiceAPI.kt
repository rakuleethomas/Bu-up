package org.rakulee.buup.network.service

import okhttp3.RequestBody
import org.rakulee.buup.model.JobSeekerSignInResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface BuupJobSeekerLoginServiceAPI {

    @POST("jobSeekerSignIn")
    suspend fun jobSeekerLogin(
        @Body requestBody: RequestBody
    ) : Response<JobSeekerSignInResponse>
}