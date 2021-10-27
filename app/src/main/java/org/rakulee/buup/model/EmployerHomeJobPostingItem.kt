package org.rakulee.buup.model

data class EmployerHomeJobPostingItem(
    val title : String = "",
    val viewCount : Int = 0,
    val wageMin : String = "$0",
    val wageHigh : String = "$0",
    val expDate : String = "Expires in 0 days"
)
