package com.mmfsin.noexcusescompose.data.repository

import com.mmfsin.noexcusescompose.data.ddbb.daos.RmsDAO
import com.mmfsin.noexcusescompose.data.mappers.createRmDTO
import com.mmfsin.noexcusescompose.data.mappers.toExercise
import com.mmfsin.noexcusescompose.data.mappers.toRmData
import com.mmfsin.noexcusescompose.domain.interfaces.IRmsRepository
import com.mmfsin.noexcusescompose.domain.models.Rm
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RmsRepository @Inject constructor(
    val rmsDAO: RmsDAO,
) : IRmsRepository {

    override suspend fun addRm(exerciseId: String, date: Long, kgs: Double) {
        val rmDto = createRmDTO(exerciseId, date, kgs)
        rmsDAO.insertRM(rmDto)
    }

    override fun getRms(): Flow<List<Rm>> {
        return rmsDAO.getRms().map { rms ->
            rms.filter { it.exercise != null }
                .groupBy { it.rmDTO.exerciseId }
                .values.mapNotNull { group ->
                    val exercise = group.first().exercise ?: return@mapNotNull null
                    Rm(
                        exercise = exercise.toExercise(),
                        data = group.map { item -> item.toRmData() }.sortedByDescending { it.date }
                    )
                }
        }
    }

    override fun getRmByExerciseId(exerciseId: String): Flow<Rm?> {
        return rmsDAO.getRmByExerciseId(exerciseId).map { rms ->
            val first = rms.firstOrNull() ?: return@map null
            val exercise = first.exercise ?: return@map null
            Rm(
                exercise = exercise.toExercise(),
                data = rms.map { it.toRmData() }.sortedByDescending { it.date }
            )
        }
    }
}