package com.bitklog.core.data.util

import com.bitklog.core.data.model.PropertyDto
import com.bitklog.domain.model.Property

val dummyPropertyDto = PropertyDto(
    id = 1,
    area = 75.0,
    offerType = 0,
    city = "Paris",
    price = 350_000.0,
    propertyType = "Apartment",
    url = "https://example.com/property/1.jpg",
    professional = "John Doe",
    rooms = 3,
    bedrooms = 2
)

val dummyProperty = Property(
    id = 1,
    area = 75.0,
    offerType = 0,
    city = "Paris",
    price = 350_000.0,
    propertyType = "Apartment",
    url = "https://example.com/property/1.jpg",
    professional = "John Doe",
    rooms = 3,
    bedrooms = 2
)
