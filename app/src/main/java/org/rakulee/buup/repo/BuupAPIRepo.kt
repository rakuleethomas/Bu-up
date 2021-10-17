package org.rakulee.buup.repo

import okhttp3.RequestBody
import org.rakulee.buup.helper.BuupApiHelper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BuupAPIRepo @Inject constructor(
    private val buupApiHelper: BuupApiHelper
) {
    suspend fun jobSeekerSignIn(body: RequestBody) = buupApiHelper.buupJobSeekerSignIn(body)
    suspend fun employerSignUp(body: RequestBody) = buupApiHelper.buupEmployerSignup(body)
}