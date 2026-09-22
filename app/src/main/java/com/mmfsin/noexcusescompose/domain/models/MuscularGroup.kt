package com.mmfsin.noexcusescompose.domain.models

import androidx.compose.ui.graphics.Color
import com.mmfsin.noexcusescompose.presentation.core.theme.BackColor
import com.mmfsin.noexcusescompose.presentation.core.theme.BicepsColor
import com.mmfsin.noexcusescompose.presentation.core.theme.CardioColor
import com.mmfsin.noexcusescompose.presentation.core.theme.ChestColor
import com.mmfsin.noexcusescompose.presentation.core.theme.CoreColor
import com.mmfsin.noexcusescompose.presentation.core.theme.ForearmColor
import com.mmfsin.noexcusescompose.presentation.core.theme.GlutesColor
import com.mmfsin.noexcusescompose.presentation.core.theme.LegsColor
import com.mmfsin.noexcusescompose.presentation.core.theme.ShoulderColor
import com.mmfsin.noexcusescompose.presentation.core.theme.Transparent
import com.mmfsin.noexcusescompose.presentation.core.theme.TricepsColor

data class MuscularGroup(
    var id: String,
    var name: String,
    var manImageURL: String,
    var womanImageURL: String,
)

fun getMuscularGroupColor(id: String): Color {
    return when (id) {
        "antebrazo" -> ForearmColor
        "biceps" -> BicepsColor
        "cardio" -> CardioColor
        "core" -> CoreColor
        "espalda" -> BackColor
        "gluteos" -> GlutesColor
        "hombro" -> ShoulderColor
        "pecho" -> ChestColor
        "pierna" -> LegsColor
        "triceps" -> TricepsColor
        else -> Transparent
    }
}

fun getMuscularGroupsExamples() = listOf(
    MuscularGroup(
        id = "1", name = "Hombro", manImageURL = "", womanImageURL = ""
    ),
    MuscularGroup(
        id = "2", name = "Bíceps", manImageURL = "", womanImageURL = ""
    ),
    MuscularGroup(
        id = "3", name = "Pecho", manImageURL = "", womanImageURL = ""
    ),
    MuscularGroup(
        id = "4", name = "Pierna", manImageURL = "", womanImageURL = ""
    ),
    MuscularGroup(
        id = "5", name = "Tríceps", manImageURL = "", womanImageURL = ""
    ),
)