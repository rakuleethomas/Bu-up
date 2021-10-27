package org.rakulee.buup.model


import com.google.gson.annotations.SerializedName

data class JobPostingItem(
    @SerializedName("city")
    val city: String = "",
    @SerializedName("companyName")
    val companyName: String = "",
    @SerializedName("jobTitle")
    val jobTitle: String = "",
    @SerializedName("latitude")
    val latitude: Double = 0.0,
    @SerializedName("liked")
    val liked: Boolean = false,
    @SerializedName("logo")
    val logo: String = "",
    @SerializedName("longitude")
    val longitude: Double = 0.0,
    @SerializedName("payRateHigh")
    val payRateHigh: String = "$25",
    @SerializedName("payRateLow")
    val payRateLow: String = "$20",
    @SerializedName("postId")
    val postId: String = "0001"
)