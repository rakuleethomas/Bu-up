package org.rakulee.buup.model

data class EmployerSavedListItem(
    val jobTitle: String,
    val companyTitle: String,
    val priceLow: String,
    val priceHigh: String,
    val location: String,
    val distanceMiles: String,
    val liked: Boolean
)
