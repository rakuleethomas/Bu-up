package org.rakulee.buup.helper

import okhttp3.RequestBody
import org.rakulee.buup.model.BuupAddJobPostingResponse
import org.rakulee.buup.model.EmployerSignInResponse
import org.rakulee.buup.model.EmployerSignupResponse
import org.rakulee.buup.model.JobSeekerSignInResponse
import org.rakulee.buup.network.service.BuupEmployerAPI
import org.rakulee.buup.network.service.BuupJobSeekerLoginServiceAPI
import retrofit2.Response
import javax.inject.Inject

class BuupApiHelperImpl @Inject constructor(
    private val jobSeekerLoginApiService : BuupJobSeekerLoginServiceAPI,
    private val employerApiService : BuupEmployerAPI
) : BuupApiHelper{
    override suspend fun buupJobSeekerSignIn(body: RequestBody): Response<JobSeekerSignInResponse> = jobSeekerLoginApiService.jobSeekerLogin(body)
    override suspend fun buupEmployerSignup(body: RequestBody): Response<EmployerSignupResponse> = employerApiService.employerSignUp(body)
    override suspend fun buupEmployerSignIn(body: RequestBody): Response<EmployerSignInResponse> = employerApiService.employerSignIn(body)
    override suspend fun buupEmployerAddJobPosting(body: RequestBody): Response<BuupAddJobPostingResponse> = employerApiService.employerAddJobPosting(body)
}