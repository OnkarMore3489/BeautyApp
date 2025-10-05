package com.beautyfox.customerapp.retrofit.api

import com.beautyfox.customerapp.offers.model.OfferItems
import retrofit2.Response
import retrofit2.http.GET

interface OffersApi {

    @GET("v3/b/68d82fedae596e708ffdf1a9?meta=false")
    suspend fun getOffers() : Response<List<OfferItems>>

}