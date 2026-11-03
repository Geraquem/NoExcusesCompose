package com.mmfsin.migration.models.convert

data class OldExercise(
    var id: String,
    var category: String,
    var imageURL: String,
    var gifURL: String?,
    var name: String,
    var order: Long,
    var description: String,
    var muscles: String,
    var isFav: Boolean,
    var muscleWikiURL: String?,
    var createdByUser: Boolean,
)
