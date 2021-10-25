package org.rakulee.buup.model

data class EmployerHomeRecommendedItem(
    val industry : String = "industry",
    val jobSeekerInfo : ArrayList<BuupJobSeekerProfile> = ArrayList<BuupJobSeekerProfile>()
)
