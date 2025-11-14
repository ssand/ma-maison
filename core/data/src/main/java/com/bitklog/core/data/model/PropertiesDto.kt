package com.bitklog.core.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PropertiesDto(
    @SerialName("totalCount") val totalCount: Int = 0,
    @SerialName("items") val items: List<PropertyDto>
)
