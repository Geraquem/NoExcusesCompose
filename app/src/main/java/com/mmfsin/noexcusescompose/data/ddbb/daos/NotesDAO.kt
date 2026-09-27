package com.mmfsin.noexcusescompose.data.ddbb.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mmfsin.noexcusescompose.data.models.NoteDTO
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: NoteDTO)

    @Query("SELECT * FROM table_notes")
    fun getNotes(): Flow<List<NoteDTO>>

    @Query("SELECT * FROM table_notes WHERE id = :noteId")
    fun getNoteById(noteId: String): NoteDTO?

    @Query("""
    UPDATE table_notes
    SET pinned = CASE
        WHEN id = :noteId THEN NOT pinned
        ELSE 0
    END
    """)
    fun updatePinnedNote(noteId: String)

    @Query("SELECT * FROM table_notes WHERE pinned = 1")
    fun getPinnedNote(): Flow<NoteDTO?>
}