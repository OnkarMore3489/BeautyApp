package com.beautyfox.customerapp.retrofit.di

import com.beautyfox.customerapp.retrofit.api.OffersApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent :: class)
class NetworkCall {

    @Singleton
    @Provides
    fun makeNetworkCall() : Retrofit{
        return Retrofit.Builder().baseUrl("https://api.jsonbin.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun getOffers(retrofit: Retrofit) : OffersApi{
        return retrofit.create(OffersApi ::class.java)
    }
}