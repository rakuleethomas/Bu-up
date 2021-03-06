package org.rakulee.buup.model


import com.google.gson.annotations.SerializedName

data class JobSeekerSignInResponse(
    @SerializedName("message")
    val message: Message
) {
    data class Message(
        @SerializedName("badge")
        val badge: ArrayList<Badge>,
        @SerializedName("buupCount")
        val buupCount: Int,
        @SerializedName("email")
        val email: String,
        @SerializedName("firstName")
        val firstName: String,
        @SerializedName("lastName")
        val lastName: String,
        @SerializedName("loginId")
        val loginId: String,
        @SerializedName("password")
        val password: String,
        @SerializedName("photoUrl")
        val photoUrl: String,
        @SerializedName("skills")
        val skills: ArrayList<String>,
        @SerializedName("socialMedia")
        val socialMedia: String,
        @SerializedName("timestamp")
        val timestamp: String,
        @SerializedName("userId")
        val userId: String,
        @SerializedName("verified")
        val verified: Boolean,
        @SerializedName("wageMax")
        val wageMax: String,
        @SerializedName("wageMin")
        val wageMin: String,
        @SerializedName("zipCode")
        val zipCode: String,
        @SerializedName("industry")
        val industry: ArrayList<String>,
        @SerializedName("availability")
        val availability : ArrayList<ArrayList<Boolean>>,
        @SerializedName("longitude")
        val longitude : Double,
        @SerializedName("latitude")
        val latitude : Double,
    ) {
        data class Badge(
            @SerializedName("badgePhotoUrl")
            val badgePhotoUrl: String,
            @SerializedName("count")
            val count: Int,
            @SerializedName("name")
            val name: String
        )
    }
}