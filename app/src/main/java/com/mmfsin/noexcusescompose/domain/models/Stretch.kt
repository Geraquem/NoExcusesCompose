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

fun getExampleStretchData()= listOf(
    Stretch(
      mGroup = "Pecho",
        stretching = listOf(
            Stretching(
                imageURL = "",
                description = "Desc pecho 1",
                order = 1
            ),
            Stretching(
                imageURL = "",
                description = "Desc pecho 2",
                order = 2
            ),
        )
    ),
    Stretch(
      mGroup = "Espalda",
        stretching = listOf(
            Stretching(
                imageURL = "",
                description = "Desc Espalda 1",
                order = 1
            ),
            Stretching(
                imageURL = "",
                description = "Desc Espalda 2",
                order = 2
            ),
        )
    ),
)