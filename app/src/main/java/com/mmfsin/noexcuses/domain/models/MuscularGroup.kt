package com.mmfsin.noexcuses.domain.models

import androidx.compose.ui.graphics.Color
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.presentation.core.theme.BackColor
import com.mmfsin.noexcuses.presentation.core.theme.BicepsColor
import com.mmfsin.noexcuses.presentation.core.theme.CardioColor
import com.mmfsin.noexcuses.presentation.core.theme.ChestColor
import com.mmfsin.noexcuses.presentation.core.theme.CoreColor
import com.mmfsin.noexcuses.presentation.core.theme.ForearmColor
import com.mmfsin.noexcuses.presentation.core.theme.GlutesColor
import com.mmfsin.noexcuses.presentation.core.theme.LegsColor
import com.mmfsin.noexcuses.presentation.core.theme.ShoulderColor
import com.mmfsin.noexcuses.presentation.core.theme.Transparent
import com.mmfsin.noexcuses.presentation.core.theme.TricepsColor

data class MuscularGroup(
    var id: String,
    var name: String,
    var manImageURL: String,
    var womanImageURL: String,
    var longImageURL: String,
    var shortImageURL: String,
)

enum class MuscularGroupType(
    val id: String,
    val color: Color,
    val title: Int,
    val icon: Int
) {
    FOREARM("antebrazo", ForearmColor, R.string.mgroups_forearm, R.drawable.ic_mg_forearm),
    BICEPS("biceps", BicepsColor, R.string.mgroups_biceps, R.drawable.ic_mg_biceps),
    CARDIO("cardio", CardioColor, R.string.mgroups_cardio, R.drawable.ic_mg_chest),
    CORE("core", CoreColor, R.string.mgroups_core, R.drawable.ic_mg_core),
    BACK("espalda", BackColor, R.string.mgroups_back, R.drawable.ic_mg_back),
    GLUTES("gluteos", GlutesColor, R.string.mgroups_glutes, R.drawable.ic_mg_glutes),
    SHOULDER("hombro", ShoulderColor, R.string.mgroups_shoulder, R.drawable.ic_mg_shoulder),
    CHEST("pecho", ChestColor, R.string.mgroups_chest, R.drawable.ic_mg_chest),
    LEGS("pierna", LegsColor, R.string.mgroups_legs, R.drawable.ic_mg_legs),
    TRICEPS("triceps", TricepsColor, R.string.mgroups_triceps, R.drawable.ic_mg_triceps);

    companion object {
        fun getMuscularGroupColor(id: String): Color = entries.find { it.id == id }?.color ?: Transparent
        fun getMuscularGroupName(id: String): Int = entries.find { it.id == id }?.title ?: R.string.empty
        fun getMuscularGroupIcon(id: String): Int = entries.find { it.id == id }?.icon ?: R.drawable.ic_mg_core
    }
}

fun getMuscularGroupsExamples() = listOf(
    MuscularGroup(
        id = "1", name = "Hombro", manImageURL = "", womanImageURL = "", longImageURL = "", shortImageURL = ""
    ),
    MuscularGroup(
        id = "2", name = "Bíceps", manImageURL = "", womanImageURL = "", longImageURL = "", shortImageURL = ""
    ),
    MuscularGroup(
        id = "3", name = "Pecho", manImageURL = "", womanImageURL = "", longImageURL = "", shortImageURL = ""
    ),
    MuscularGroup(
        id = "4", name = "Pierna", manImageURL = "", womanImageURL = "", longImageURL = "", shortImageURL = ""
    ),
    MuscularGroup(
        id = "5", name = "Tríceps", manImageURL = "", womanImageURL = "", longImageURL = "", shortImageURL = ""
    ),
)