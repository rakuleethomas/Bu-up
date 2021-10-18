package org.rakulee.buup.helper

import okhttp3.RequestBody
import org.rakulee.buup.model.EmployerSignupResponse
import org.rakulee.buup.model.JobSeekerSignInResponse
import org.rakulee.buup.network.service.BuupEmployerSignUpAPI
import org.rakulee.buup.network.service.BuupJobSeekerLoginServiceAPI
import retrofit2.Response
import javax.inject.Inject

class BuupApiHelperImpl @Inject constructor(
    private val jobSeekerLoginApiService : BuupJobSeekerLoginServiceAPI,
    private val employerSignupApiService : BuupEmployerSignUpAPI
) : BuupApiHelper{
    override suspend fun buupJobSeekerSignIn(body: RequestBody): Response<JobSeekerSignInResponse> = jobSeekerLoginApiService.jobSeekerLogin(body)
    override suspend fun buupEmployerSignup(body: RequestBody): Response<EmployerSignupResponse> = employerSignupApiService.employerSignUp(body)
}