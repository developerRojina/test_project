package com.radius.dev.data.source

import com.radius.dev.data.model.Facility
import com.radius.dev.data.model.RadiusDataMapper
import com.radius.dev.data.source.data_store.DataStoreManager
import com.radius.dev.data.source.local.LocalDataSource
import com.radius.dev.data.source.remote.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RadiusRepository @Inject constructor(
    private val dataMapper: RadiusDataMapper,
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val dataStoreManager: DataStoreManager


) {


    fun getRadiusData(): Flow<List<Facility>> = localDataSource.getFacilities()


    suspend fun getRadiusDataFromApi() {
        dataStoreManager.setApiFetchTime()
        val data = remoteDataSource.getRadiusData()
        localDataSource.insertDataOnRealm(data)
    }
}

/*  val currentTime = System.currentTimeMillis()
  val apiFetchTime = dataStoreManager.apiFetchTime.first()
  if (apiFetchTime != null) {
      val difference = currentTime - apiFetchTime
      val differenceInDays = TimeUnit.DAYS.convert(difference, TimeUnit.MILLISECONDS)
      if (differenceInDays > 1) {
          getRadiusDataFromApi()
      } else {

      }
  } else {
      getRadiusDataFromApi()
  }*/



