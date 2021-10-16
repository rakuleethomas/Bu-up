package org.rakulee.buup.helper

import okhttp3.RequestBody
import org.rakulee.buup.model.BuupTestResponse
import org.rakulee.buup.model.JobSeekerSignInResponse
import org.rakulee.buup.network.service.BuupJobSeekerLoginServiceAPI
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(
    private val apiService : BuupJobSeekerLoginServiceAPI
) : ApiHelper{
    override suspend fun buupJobSeekerSignIn(body: RequestBody): Response<JobSeekerSignInResponse> = apiService.jobSeekerLogin(body)
}