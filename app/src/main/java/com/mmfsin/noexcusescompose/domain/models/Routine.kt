package com.mmfsin.noexcusescompose.domain.models

data class Routine(
    var id: String,
    var name: String,
    var description: String?,
    var days: Int,
    var doingIt: Boolean,
    val createdByUser: Boolean,
    val pinnedDate: Long?
)
