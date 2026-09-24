package com.mmfsin.noexcusescompose.domain.models

data class Stretch(
    val mGroup: String,
    val stretching: List<Stretching>
)

open class Stretching(
    var imageURL: String?,
    var description: String,
    var order: Long
)

