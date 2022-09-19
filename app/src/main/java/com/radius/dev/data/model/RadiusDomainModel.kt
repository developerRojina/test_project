package com.radius.dev.data.model

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey



open class Facility : RealmObject() {
    @PrimaryKey
    var id: Int = 0
    var name: String = ""
    var options: RealmList<Option> = RealmList()
}

open class Option : RealmObject() {
    @PrimaryKey
    var id: Int = 0
    var facilityId: Int = 0
    var name: String = ""
    var icon: String = ""
    var exclusions: RealmList<Int> = RealmList()

}

