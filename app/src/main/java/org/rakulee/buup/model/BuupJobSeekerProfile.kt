package org.rakulee.buup.model

data class BuupJobSeekerProfile(
    var userId : String = "",
    var firstName : String = "",
    var lastName : String  = "",
    var email : String  = "",
    var loginId : String  = "",
    var password : String  = "",      // hash
    var verified : Boolean = false,
    var industry : ArrayList<String> = ArrayList<String>(),
    var badgeList : ArrayList<JobSeekerSignInResponse.Message.Badge> = ArrayList<JobSeekerSignInResponse.Message.Badge>(),
    var photoUrl : String = "",
    var buupCount : Int = 0,
    var skills : ArrayList<String> = ArrayList<String>(),
    var socialMedia : String = "",
    var timestamp : String = "",
    var wageMin : String = "",
    var wageMax : String = "",
    var zipCode : String = "",
    var availability : ArrayList<ArrayList<Boolean>> = ArrayList<ArrayList<Boolean>>()
) {
    data class Skill(
        val name : String = "",
        val proficiency : Int = 0
    )
    data class Availability(
        val date : String = "",
        val result : Map<String, ArrayList<Boolean>> = HashMap<String, ArrayList<Boolean>>()
    )
}

