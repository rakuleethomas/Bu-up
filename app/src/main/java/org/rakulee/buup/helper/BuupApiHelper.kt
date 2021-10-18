package org.rakulee.buup.helper

import okhttp3.RequestBody
import org.rakulee.buup.model.EmployerSignupResponse
import org.rakulee.buup.model.JobSeekerSignInResponse
import retrofit2.Response

interface BuupApiHelper {
    suspend fun buupJobSeekerSignIn(body: RequestBody) : Response<JobSeekerSignInResponse>
    suspend fun buupEmployerSignup(body: RequestBody) : Response<EmployerSignupResponse>
}