package com.mmfsin.noexcusescompose.domain.models

import androidx.compose.ui.graphics.Color
import com.mmfsin.noexcusescompose.R
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

enum class MuscularGroupType(
    val id: String,
    val color: Color,
    val title: Int
) {
    FOREARM("antebrazo", ForearmColor, R.string.mgroups_forearm),
    BICEPS("biceps", BicepsColor, R.string.mgroups_biceps),
    CARDIO("cardio", CardioColor, R.string.mgroups_cardio),
    CORE("core", CoreColor, R.string.mgroups_core),
    BACK("espalda", BackColor, R.string.mgroups_back),
    GLUTES("gluteos", GlutesColor, R.string.mgroups_glutes),
    SHOULDER("hombro", ShoulderColor, R.string.mgroups_shoulder),
    CHEST("pecho", ChestColor, R.string.mgroups_chest),
    LEGS("pierna", LegsColor, R.string.mgroups_legs),
    TRICEPS("triceps", TricepsColor, R.string.mgroups_triceps);

    companion object {
        fun getMuscularGroupColor(id: String): Color = entries.find { it.id == id }?.color ?: Transparent
        fun getMuscularGroupName(id: String): Int = entries.find { it.id == id }?.title ?: R.string.empty
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