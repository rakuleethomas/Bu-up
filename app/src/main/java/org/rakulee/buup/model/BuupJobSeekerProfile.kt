package org.rakulee.buup.model

data class BuupJobSeekerProfile(
    val userId : String,
    val firstName : String,
    val lastName : String,
    val email : String,
    val loginId : String,
    val password : String,      // hash
    val verified : Boolean,
    val industry : ArrayList<String>,
    val badge : ArrayList<Badge>,
    val photoUrl : String,
    val buupCount : Int,
    val skills : Skill,
    val socialMedia : String,
    val timestamp : String,
    val wageMin : String,
    val wageMax : String,
    val zipCode : String
) {
    data class Badge(
        val badgePhotoUrl : String,
        val count : Int,
        val name : String
    )
    data class Skill(
        val name : String,
        val proficiency : Int
    )
    data class Availability(
        val date : String,
        val result : Map<String, ArrayList<Boolean>>
    )
}

