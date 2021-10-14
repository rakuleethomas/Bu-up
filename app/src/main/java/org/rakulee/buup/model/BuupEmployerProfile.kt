package org.rakulee.buup.model

data class BuupEmployerProfile(
    val userId : String,
    val firstName : String,
    val lastName : String,
    val email : String,
    val loginId : String,
    val password : String,      // hash
    val verified : Boolean,
    val photoUrl : String,
    val socialMedia : String,
    val timestamp : String,
    val companyInfo : CompanyInfo
){

    data class CompanyInfo(
        val name : String,
        val logoUrl : String,
        val address : String,
        val description : String,
        val industry : ArrayList<String>
    )
}
