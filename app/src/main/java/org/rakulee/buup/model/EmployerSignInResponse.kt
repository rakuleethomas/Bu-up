package org.rakulee.buup.model


import com.google.gson.annotations.SerializedName

data class EmployerSignInResponse(
    @SerializedName("message")
    val message: Message
) {
    data class Message(
        @SerializedName("companyInfo")
        val companyInfo: CompanyInfo,
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
        @SerializedName("socialMedia")
        val socialMedia: String,
        @SerializedName("timestamp")
        val timestamp: String,
        @SerializedName("userId")
        val userId: Int,
        @SerializedName("verified")
        val verified: Boolean
    ) {
        data class CompanyInfo(
            @SerializedName("address1")
            val address1: String,
            @SerializedName("address2")
            val address2: String,
            @SerializedName("budget")
            val budget: String,
            @SerializedName("city")
            val city: String,
            @SerializedName("companySize")
            val companySize: String,
            @SerializedName("description")
            val description: String,
            @SerializedName("industry")
            val industry: ArrayList<String>,
            @SerializedName("logoUrl")
            val logoUrl: String,
            @SerializedName("name")
            val name: String,
            @SerializedName("state")
            val state: String,
            @SerializedName("zipCode")
            val zipCode: String
        )
    }
}