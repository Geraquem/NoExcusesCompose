package com.mmfsin.noexcusescompose.domain.models

data class Stretch(
    val mGroup: String,
    val iconUrl: String,
    val stretching: List<Stretching>
)

open class Stretching(
    var name: String,
    var imageURL: String?,
    var description: String,
    var order: Long
)

fun getExampleStretchData() = listOf(
    Stretch(
        mGroup = "pecho",
        iconUrl = "",
        stretching = listOf(
            Stretching(
                name = "Nombre del estiramiento",
                imageURL = "",
                description = "Desc pecho 1",
                order = 1
            ),
            Stretching(
                name = "Nombre del estiramiento",
                imageURL = "",
                description = "Desc pecho 2",
                order = 2
            ),
        )
    ),
    Stretch(
        mGroup = "espalda",
        iconUrl = "",
        stretching = listOf(
            Stretching(
                name = "Nombre del estiramiento",
                imageURL = "",
                description = "Desc Espalda 1",
                order = 1
            ),
            Stretching(
                name = "Nombre del estiramiento",
                imageURL = "",
                description = "Desc Espalda 2",
                order = 2
            ),
        )
    ),
)