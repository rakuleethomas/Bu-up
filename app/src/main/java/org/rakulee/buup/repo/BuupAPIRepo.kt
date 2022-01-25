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
    suspend fun employerSignIn(body: RequestBody) = buupApiHelper.buupEmployerSignIn(body)
    suspend fun employerAddJobPosting(body: RequestBody) = buupApiHelper.buupEmployerAddJobPosting(body)
    suspend fun getJobByDistance(latitude: Double, longitude: Double, distance: Int) = buupApiHelper.buupGetJobByDistance(latitude, longitude, distance)
}