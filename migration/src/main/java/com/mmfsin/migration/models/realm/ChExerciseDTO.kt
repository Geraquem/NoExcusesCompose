package com.mmfsin.migration.models.realm

import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

open class ChExerciseDTO: RealmObject {
    @PrimaryKey
    var id: String = ""
    var routineId: String = ""
    var dayId: String = ""
    var exerciseId: String? = null
    var data: RealmList<DataDTO>? = null
    var time: Double? = null
    var notes: String? = null
    var position: Int = 0
    var superSerie: Boolean = false
}

open class DataDTO : RealmObject {
    @PrimaryKey
    var id: String? = null
    var exerciseDayId: String? = null
    var reps: Int? = null
    var weight: Double? = null
}