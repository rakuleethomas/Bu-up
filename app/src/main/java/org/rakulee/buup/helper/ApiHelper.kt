package org.rakulee.buup.helper

import okhttp3.RequestBody
import org.rakulee.buup.model.BuupTestResponse
import org.rakulee.buup.model.JobSeekerSignInResponse
import retrofit2.Response

interface ApiHelper {
    suspend fun buupJobSeekerSignIn(body: RequestBody) : Response<JobSeekerSignInResponse>
}