package com.mmfsin.noexcuses.data.ddbb.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mmfsin.noexcuses.data.models.MuscularGroupDTO

@Dao
interface MuscularGroupsDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMuscularGroups(muscularGroups: List<MuscularGroupDTO>)

    @Query("SELECT * FROM table_muscular_groups")
    suspend fun getMuscularGroups(): List<MuscularGroupDTO>
}