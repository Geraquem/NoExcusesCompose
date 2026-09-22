package com.mmfsin.noexcusescompose.domain.interfaces

import com.mmfsin.noexcusescompose.domain.models.MuscularGroup
import com.mmfsin.noexcusescompose.domain.models.Routine

interface IMenuRepository {
    suspend fun checkVersion()

    suspend fun getMuscularGroups(): List<MuscularGroup>

//    fun getMyActualRoutine(): Routine?
//    fun getMyActualRoutineDays(routineId: String): List<Day>

//    suspend fun unpinRoutineFromMenu(routineId: String)
//    suspend fun unpinNoteFromMenu(noteId: String)
//
//    fun checkBodyImage(): Boolean
//    fun editBodyImage(womanImageSelected: Boolean)
}