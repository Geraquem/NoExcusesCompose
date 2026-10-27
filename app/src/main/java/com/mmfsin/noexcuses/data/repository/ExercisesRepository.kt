package com.mmfsin.noexcuses.data.repository

import com.google.firebase.database.FirebaseDatabase
import com.mmfsin.noexcuses.data.ddbb.SharedPrefs
import com.mmfsin.noexcuses.data.ddbb.daos.ExercisesDAO
import com.mmfsin.noexcuses.data.ddbb.daos.RoutinesDAO
import com.mmfsin.noexcuses.data.mappers.createExerciseDTO
import com.mmfsin.noexcuses.data.mappers.toExercise
import com.mmfsin.noexcuses.data.mappers.toExerciseList
import com.mmfsin.noexcuses.data.models.ExerciseDTO
import com.mmfsin.noexcuses.domain.interfaces.IExercisesRepository
import com.mmfsin.noexcuses.domain.models.Exercise
import com.mmfsin.noexcuses.util.EXERCISES
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ExercisesRepository @Inject constructor(
    val prefs: SharedPrefs,
    val exercisesDAO: ExercisesDAO,
    val routinesDAO: RoutinesDAO,
) : IExercisesRepository {

    override suspend fun getAllExercises() {
        if (prefs.getExercisesFromServer()) {
            val snapshot = FirebaseDatabase
                .getInstance()
                .getReference(EXERCISES)
                .get()
                .await()

            val firebaseExercises = snapshot.children
                .flatMap { categorySnapshot ->
                    categorySnapshot.children.mapNotNull { exerciseSnapshot ->
                        exerciseSnapshot.getValue(ExerciseDTO::class.java)
                    }
                }

            if (firebaseExercises.isNotEmpty()) {
                prefs.updateExercisesFromServer(false)
                exercisesDAO.insertExercises(firebaseExercises)

                println("------------------------------ all exercises from server -> ${firebaseExercises.size} exercises")
            }
        }
    }

    override suspend fun getExercisesByMuscularGroup(mGroupId: String): Flow<List<Exercise>> {
        if (prefs.getExercisesFromServer()) {
            val snapshot = FirebaseDatabase
                .getInstance()
                .getReference(EXERCISES)
                .get()
                .await()

            val firebaseExercises = snapshot.children
                .flatMap { categorySnapshot ->
                    categorySnapshot.children.mapNotNull { exerciseSnapshot ->
                        exerciseSnapshot.getValue(ExerciseDTO::class.java)
                    }
                }

            if (firebaseExercises.isNotEmpty()) {
                prefs.updateExercisesFromServer(false)
                exercisesDAO.insertExercises(firebaseExercises)
            }
        }

        return exercisesDAO.getExerciseByMuscularGroup(mGroupId).map { it.toExerciseList() }
    }

    override fun getExerciseById(id: String): Flow<Exercise?> {
        return exercisesDAO.getExerciseById(id).map { it?.toExercise() }
    }

    override fun updateFavExercise(id: String, isFav: Boolean) {
        exercisesDAO.updateFavExercise(id, isFav)
    }

    override fun getFavExercises(): Flow<List<Exercise>> {
        return exercisesDAO.getFavExercises().map { it.toExerciseList() }
    }

    override suspend fun createExercise(exercise: Exercise) {
        val exerciseDTO = exercise.createExerciseDTO()
        exercisesDAO.insertExercise(exerciseDTO)
    }

    override suspend fun deleteExercise(exerciseId: String) {
        routinesDAO.deleteExerciseAndExercisesRtn(exerciseId)
    }
}