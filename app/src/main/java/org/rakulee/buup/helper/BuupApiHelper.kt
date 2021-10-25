package org.rakulee.buup.helper

import okhttp3.RequestBody
import org.rakulee.buup.model.BuupAddJobPostingResponse
import org.rakulee.buup.model.BuupGetJobPostingByDistanceResponse
import org.rakulee.buup.model.EmployerSignInResponse
import org.rakulee.buup.model.EmployerSignupResponse
import org.rakulee.buup.model.JobSeekerSignInResponse
import retrofit2.Response

interface BuupApiHelper {
    suspend fun buupJobSeekerSignIn(body: RequestBody) : Response<JobSeekerSignInResponse>
    suspend fun buupEmployerSignup(body: RequestBody) : Response<EmployerSignupResponse>
    suspend fun buupEmployerSignIn(body: RequestBody) : Response<EmployerSignInResponse>
    suspend fun buupEmployerAddJobPosting(body: RequestBody) : Response<BuupAddJobPostingResponse>
    suspend fun buupGetJobByDistance(body: RequestBody) : Response<BuupGetJobPostingByDistanceResponse>
}