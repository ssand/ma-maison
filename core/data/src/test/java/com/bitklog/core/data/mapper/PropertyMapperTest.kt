package com.bitklog.core.data.mapper

import com.bitklog.core.data.util.dummyProperty
import com.bitklog.core.data.util.dummyPropertyDto
import org.junit.Assert.assertEquals
import org.junit.Test

class PropertyMapperTest {

    @Test
    fun `toDomain maps all fields`() {
        assertEquals(dummyProperty, dummyPropertyDto.toDomain())
    }
}
