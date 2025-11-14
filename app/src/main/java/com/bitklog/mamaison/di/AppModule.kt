package com.bitklog.mamaison.di

import com.bitklog.core.data.di.DataModule
import com.bitklog.presentation.properties.di.PropertiesModule
import com.bitklog.presentation.propertydetails.di.PropertyDetailsModule
import org.koin.core.annotation.Module

@Module(
    includes = [
        PropertiesModule::class,
        PropertyDetailsModule::class,
        DataModule::class
    ]
)
class AppModule
