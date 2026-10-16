package com.mmfsin.noexcusescompose.domain.models

import androidx.compose.ui.graphics.Color
import com.mmfsin.noexcusescompose.presentation.core.theme.BlueLight
import com.mmfsin.noexcusescompose.presentation.core.theme.GreenLight
import com.mmfsin.noexcusescompose.presentation.core.theme.OrangeLight
import com.mmfsin.noexcusescompose.presentation.core.theme.PurpleLight
import com.mmfsin.noexcusescompose.presentation.core.theme.RedLight

data class Routine(
    var id: String,
    var name: String,
    var description: String?,
    var days: List<Day>,
    var pinned: Boolean,
    val createdByUser: Boolean,
    val pinnedDate: String?,
    val order: Int,
    val color: Color,
)

enum class RoutineColor(val id: Int, val color: Color) {
    RED(0, RedLight),
    GREEN(1, GreenLight),
    BLUE(2, BlueLight),
    ORANGE(3, OrangeLight),
    PURPLE(4, PurpleLight);

    companion object {
        fun getRandomRoutineColorId(): Int = entries.random().id
        fun getRoutineColorById(id: Int): Color = entries.find { it.id == id }?.color ?: BlueLight
    }
}

fun getExampleRoutines() = listOf(
    Routine(
        id = "1",
        name = "Rutina 1",
        description = "Descripción rutina 1",
        days = getExampleDays(),
        pinned = false,
        createdByUser = true,
        pinnedDate = null,
        order = 0,
        color = PurpleLight
    ),
    Routine(
        id = "2",
        name = "Rutina 2",
        description = null,
        days = getExampleDays(),
        pinned = true,
        createdByUser = true,
        pinnedDate = "24 Agosto 2025",
        order = 1,
        color = GreenLight
    ),
    Routine(
        id = "3",
        name = "Rutina 3",
        description = "Descripción rutina 3",
        days = getExampleDays(),
        pinned = false,
        createdByUser = false,
        pinnedDate = null,
        order = 2,
        color = OrangeLight
    ),
)