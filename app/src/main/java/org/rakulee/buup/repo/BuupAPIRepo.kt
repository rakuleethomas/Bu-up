package org.rakulee.buup.repo

import okhttp3.RequestBody
import org.rakulee.buup.helper.ApiHelper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BuupAPIRepo @Inject constructor(
    private val apiHelper: ApiHelper
) {
    suspend fun jobSeekerSignIn(body: RequestBody) = apiHelper.buupJobSeekerSignIn(body)
}