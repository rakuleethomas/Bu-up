package org.rakulee.buup.model

data class EmployerSavedListItem(
    val jobTitle: String,
    val companyTitle: String,
    val payRateLow: String,
    val payRateHigh: String,
    val city: String,
    val distance: String,
    val liked: Boolean
)
