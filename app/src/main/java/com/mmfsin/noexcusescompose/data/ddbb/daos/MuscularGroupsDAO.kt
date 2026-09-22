package com.mmfsin.noexcusescompose.data.ddbb.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mmfsin.noexcusescompose.data.models.MuscularGroupDTO

@Dao
interface MuscularGroupsDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMuscularGroups(rankings: List<MuscularGroupDTO>)

    @Query("SELECT * FROM table_muscular_groups")
    suspend fun getMuscularGroups(): List<MuscularGroupDTO>
}