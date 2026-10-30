package com.mmfsin.noexcuses.data.ddbb.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Transaction
import com.mmfsin.noexcuses.data.models.DayDTO
import com.mmfsin.noexcuses.data.models.MyRoutineDTO
import com.mmfsin.noexcuses.data.models.NoteDTO

@Dao
interface MigrationDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNotes(notes: List<NoteDTO>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertRoutines(routines: List<MyRoutineDTO>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDays(days: List<DayDTO>)

    @Transaction
    suspend fun migrate(
        notes: List<NoteDTO>,
        routines: List<MyRoutineDTO>,
        days: List<DayDTO>
    ) {
        insertNotes(notes)
        insertRoutines(routines)
        insertDays(days)
    }
}