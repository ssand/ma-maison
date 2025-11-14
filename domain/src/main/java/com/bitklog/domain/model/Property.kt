package com.bitklog.domain.model

data class Property(
    val id: Int,
    val area: Double,
    val offerType: Int,
    val city: String,
    val price: Double,
    val propertyType: String? = null,
    val url: String? = null,
    val professional: String? = null,
    val rooms: Int? = null,
    val bedrooms: Int? = null
)
