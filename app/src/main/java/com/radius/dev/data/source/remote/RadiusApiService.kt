package com.radius.dev.data.source.remote

import com.radius.dev.data.model.RadiusDTO
import retrofit2.http.GET

interface RadiusApiService {

    @GET("db")
    suspend fun getRadiusData(): RadiusDTO
}