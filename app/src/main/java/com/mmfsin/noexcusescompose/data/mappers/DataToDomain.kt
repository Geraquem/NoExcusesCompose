package com.mmfsin.noexcusescompose.data.mappers

import com.mmfsin.noexcusescompose.data.models.MuscularGroupDTO
import com.mmfsin.noexcusescompose.domain.models.MuscularGroup

fun MuscularGroupDTO.toMuscularGroup() = MuscularGroup(
    id = id,
    name = name,
    manImageURL = manImageURL,
    womanImageURL = womanImageURL
)

fun List<MuscularGroupDTO>.toMuscularGroupList() = this.map { it.toMuscularGroup() }