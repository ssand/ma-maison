package com.bitklog.core.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PropertyDto(
    @SerialName("id") val id: Int,
    @SerialName("area") val area: Double,
    @SerialName("offerType") val offerType: Int,
    @SerialName("city") val city: String,
    @SerialName("price") val price: Double,
    @SerialName("propertyType") val propertyType: String? = null,
    @SerialName("url") val url: String? = null,
    @SerialName("professional") val professional: String? = null,
    @SerialName("rooms") val rooms: Int? = null,
    @SerialName("bedrooms") val bedrooms: Int? = null
)
