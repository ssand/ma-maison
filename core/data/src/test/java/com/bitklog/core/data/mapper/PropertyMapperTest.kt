package com.bitklog.core.data.mapper

import com.bitklog.core.data.model.PropertyDto
import com.bitklog.domain.model.Property
import org.junit.Assert.assertEquals
import org.junit.Test

class PropertyMapperTest {

    @Test
    fun `toDomain maps all fields`() {
        val dto = PropertyDto(
            id = 10,
            area = 55.0,
            offerType = 2,
            city = "Paris",
            price = 250000.0,
            propertyType = "Apartment",
            url = "https://example.com",
            professional = "Agency",
            rooms = 3,
            bedrooms = 2
        )

        val expected = Property(
            id = 10,
            area = 55.0,
            offerType = 2,
            city = "Paris",
            price = 250000.0,
            propertyType = "Apartment",
            url = "https://example.com",
            professional = "Agency",
            rooms = 3,
            bedrooms = 2
        )

        assertEquals(expected, dto.toDomain())
    }
}
