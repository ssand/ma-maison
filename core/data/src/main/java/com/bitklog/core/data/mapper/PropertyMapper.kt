package com.bitklog.core.data.mapper

import com.bitklog.core.data.model.PropertyDto
import com.bitklog.domain.model.Property

fun PropertyDto.toDomain(): Property = Property(
    id = id,
    area = area,
    offerType = offerType,
    city = city,
    price = price,
    propertyType = propertyType,
    url = url,
    professional = professional,
    rooms = rooms,
    bedrooms = bedrooms
)
