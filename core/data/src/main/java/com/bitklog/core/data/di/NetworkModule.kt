package com.bitklog.core.data.di

import com.bitklog.core.data.network.ktorHttpClient
import io.ktor.client.HttpClient
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
class NetworkModule {

    @Single
    fun providesKtorClient(): HttpClient =
        ktorHttpClient
}
