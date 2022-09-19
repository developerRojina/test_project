package com.radius.dev.data.source.remote

import com.radius.dev.data.model.RadiusDTO
import com.radius.dev.data.source.remote.RadiusApiService
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val apiService: RadiusApiService) {
    suspend fun getRadiusData(): RadiusDTO = apiService.getRadiusData()
}