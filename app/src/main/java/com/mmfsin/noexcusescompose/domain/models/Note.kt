package com.mmfsin.noexcusescompose.domain.models

data class Note(
    var id: String,
    var title: String,
    var description: String,
    var date: String,
    var pinned: Boolean
)
