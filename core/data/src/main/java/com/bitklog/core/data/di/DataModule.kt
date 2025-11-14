package com.bitklog.core.data.di

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

@Module(includes = [NetworkModule::class])
@ComponentScan("com.bitklog.core.data")
class DataModule
