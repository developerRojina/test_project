package com.radius.dev.data.source.local

import com.radius.dev.data.model.Facility
import com.radius.dev.data.model.Option
import com.radius.dev.data.model.RadiusDTO
import io.realm.Realm
import io.realm.RealmList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class LocalDataSource @Inject constructor(val realm: Realm) {

    fun getFacilities(): Flow<List<Facility>> = flow {
        emit(realm.where(Facility::class.java).findAll().toList())
    }

    fun insertDataOnRealm(radiusDTO: RadiusDTO) {
        realm.beginTransaction()



        radiusDTO.facilities.map { facilitydto ->
            val facility = Facility()
            facility.id = facilitydto.facilityId
            facility.name = facilitydto.name
            val list: RealmList<Option> = facility.options
            list.addAll(facilitydto.options.map {
                val option = Option()
                option.icon = it.icon
                option.facilityId = facilitydto.facilityId
                option.id = it.id
                option.name = it.name
                option
            })
            realm.insertOrUpdate(facility)
        }


        radiusDTO.exclusions.map { exclusions ->
            exclusions.map {
                val option = realm.where(Option::class.java).equalTo("id", it.optionsId).findFirst()
                option?.let {
                    val items = it.exclusions
                    items.addAll(exclusions.filter { option.id != it.optionsId }
                        .map { it.optionsId })
                }
                realm.insertOrUpdate(option)
            }
        }
        realm.commitTransaction()
    }


}