package org.rakulee.buup.model


import com.google.gson.annotations.SerializedName

class BuupGetJobPostingByDistanceResponse : ArrayList<BuupGetJobPostingByDistanceResponse.BuupGetJobPostingByDistanceResponseItem>(){
    data class BuupGetJobPostingByDistanceResponseItem(
        @SerializedName("city")
        val city: String,
        @SerializedName("companyName")
        val companyName: String,
        @SerializedName("employer")
        val employer: String,
        @SerializedName("jobTitle")
        val jobTitle: String,
        @SerializedName("latitude")
        val latitude: String,
        @SerializedName("liked")
        val liked: Boolean,
        @SerializedName("logo")
        val logo: String,
        @SerializedName("longitude")
        val longitude: String,
        @SerializedName("payRateHigh")
        val payRateHigh: String,
        @SerializedName("payRateLow")
        val payRateLow: String,
        @SerializedName("postId")
        val postId: String
    )
}