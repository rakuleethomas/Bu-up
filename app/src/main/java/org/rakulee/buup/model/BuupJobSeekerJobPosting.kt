package org.rakulee.buup.model

import com.google.gson.annotations.SerializedName

data class BuupJobSeekerJobPosting(
    var city: String = "",
    var companyName: String = "",
    var jobTitle: String = "",
    var latitude: Double = 0.0,
    var liked: Boolean = false,
    var logo: String = "",
    var longitude: Double = 0.0,
    var payRateHigh: String = "",
    var payRateLow: String = "",
    var postId: String = ""
)
