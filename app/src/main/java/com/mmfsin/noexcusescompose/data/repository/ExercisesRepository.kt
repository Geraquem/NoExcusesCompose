package com.mmfsin.noexcusescompose.data.repository

import com.google.firebase.database.FirebaseDatabase
import com.mmfsin.noexcusescompose.data.ddbb.SharedPrefs
import com.mmfsin.noexcusescompose.data.ddbb.daos.ExercisesDAO
import com.mmfsin.noexcusescompose.data.mappers.toExercise
import com.mmfsin.noexcusescompose.data.mappers.toExerciseList
import com.mmfsin.noexcusescompose.data.models.ExerciseDTO
import com.mmfsin.noexcusescompose.domain.interfaces.IExercisesRepository
import com.mmfsin.noexcusescompose.domain.models.Exercise
import com.mmfsin.noexcusescompose.util.EXERCISES
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ExercisesRepository @Inject constructor(
    val prefs: SharedPrefs,
    val exercisesDAO: ExercisesDAO,
) : IExercisesRepository {

    override suspend fun getExercises(): List<Exercise> {
        if (prefs.getExercisesFromServer()) {
            val snapshot = FirebaseDatabase
                .getInstance()
                .getReference(EXERCISES)
                .get()
                .await()

            val firebaseExercises = snapshot.children.flatMap { categorySnapshot ->
                categorySnapshot.children.mapNotNull { exerciseSnapshot ->
                    exerciseSnapshot.getValue(ExerciseDTO::class.java)
                }
            }

            if (firebaseExercises.isNotEmpty()) {
                prefs.updateExercisesFromServer(false)
                exercisesDAO.insertExercises(firebaseExercises)
            }
            return firebaseExercises.toExerciseList()

        } else return exercisesDAO.getAllExercises().toExerciseList()
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
}

//    suspend fun getExercises(): List<Exercise> {
//        val latch = CountDownLatch(1)
//        val sharedPrefs = context.getSharedPreferences(MY_SHARED_PREFS, Context.MODE_PRIVATE)
//
//        if (sharedPrefs.getBoolean(SERVER_EXERCISES, true)) {
//            realmDatabase.deleteAllObjects(ExerciseDTO::class)
//            val exercises = mutableListOf<ExerciseDTO>()
//            Firebase.database.reference.child(EXERCISES).get().addOnSuccessListener {
//                for (mgroup in it.children) {
//                    for (child in mgroup.children) {
//                        child.getValue(ExerciseDTO::class.java)?.let { exerciseDTO ->
//                            saveExerciseInRealm(exerciseDTO)
//                            exercises.add(exerciseDTO)
//                        }
//                    }
//                }
//                sharedPrefs.edit().apply {
//                    putBoolean(SERVER_EXERCISES, false)
//                    apply()
//                }
//                latch.countDown()
//
//            }.addOnFailureListener {
//                latch.countDown()
//            }
//
//            withContext(Dispatchers.IO) { latch.await() }
//            return exercises.sortedBy { it.order }.toExerciseList()
//
//        } else {
//            val exercises = realmDatabase.getObjectsFromRealm { query<ExerciseDTO>().find() }
//            return exercises.sortedBy { it.order }.toExerciseList()
//        }
//    }
//
//    override suspend fun getExercisesByMuscularGroup(mGroup: String): List<Exercise> {
//        val exercises = getExercises()
//        return exercises.filter { it.category == mGroup }
//    }
//
//    private fun saveExerciseInRealm(exercise: ExerciseDTO) = realmDatabase.addObject { exercise }
//
//    override fun getExerciseById(id: String): Exercise? {
//        val exercises = realmDatabase.getObjectFromRealm(ExerciseDTO::class, ID, id)
//        return exercises?.toExercise()
//    }
//
//    override fun getDayExercises(dayId: String): List<CompactExercise> {
//        val exercises = realmDatabase.getObjectsFromRealm {
//            query<ChExerciseDTO>("$DAY_ID == $0", dayId).find()
//        }
//        val resultList = mutableListOf<CompactExercise>()
//        for (exercise in exercises) {
//            exercise.exerciseId?.let { id ->
//                val ex = getExerciseById(id)
//                ex?.let { e -> resultList.add(exercise.toCompactExercise(e)) }
//            }
//        }
//        return resultList.sortedBy { it.position }
//    }
//
//    override suspend fun addChExercise(chExercise: ChExercise) {
//        realmDatabase.write {
//            val day = query<DayDTO>("$ID == $0", chExercise.dayId).first().find()
//            var exercisePos: Int
//            day?.let { d ->
//                d.exercises += 1
//                exercisePos = d.exercises
//
//                copyToRealm(toChExerciseDTO(exercisePos, chExercise))
//            }
//        }
//    }
//
//    override fun addDefaultExerciseAsMine(chExercise: ChExerciseDTO) {
//        realmDatabase.addObject { chExercise }
//    }
//
//    override suspend fun editChExercise(chExercise: ChExercise) {
//        realmDatabase.write {
//            val exercise = query<ChExerciseDTO>("$ID == $0", chExercise.id).first().find()
//            exercise?.let { e ->
//                e.data = setExerciseData(chExercise.data)
//                e.time = chExercise.time
//                e.superSerie = chExercise.superSerie
//                e.notes = chExercise.notes
//            }
//        }
//    }
//
//    override suspend fun moveChExercise(exercises: List<String>) {
//        realmDatabase.write {
//            exercises.forEachIndexed { i, id ->
//                val exercise = query<ChExerciseDTO>("$ID == $0", id).first().find()
//                exercise?.position = i
//            }
//        }
//    }
//
//    override fun getChExerciseById(chExerciseId: String): ChExercise? =
//        getChExerciseDTO(chExerciseId)?.toChExercise()
//
//    private fun getChExerciseDTO(chExerciseId: String): ChExerciseDTO? =
//        realmDatabase.getObjectFromRealm(ChExerciseDTO::class, ID, chExerciseId)
//
//    override fun getFavExercises(): List<Exercise> {
//        val favs = realmDatabase.getObjectsFromRealm {
//            query<ExerciseDTO>("$FAV_ID == $0", true).find()
//        }
//        return favs.toExerciseList().sortedBy { it.category }
//    }
//
//    override fun checkExerciseFav(exerciseId: String): Boolean {
//        val exercise = getExerciseById(exerciseId)
//        return exercise?.isFav ?: run { false }
//    }
//
//    override suspend fun updateExerciseFav(exerciseId: String) {
//        realmDatabase.write {
//            val exercise = query<ExerciseDTO>("$ID == $0", exerciseId).first().find()
//            exercise?.let { e ->
//                e.isFav = !e.isFav
//            }
//        }
//    }
//
//    override suspend fun deleteChExercise(chExerciseId: String) {
//        realmDatabase.write {
//            val chExercise = query<ChExerciseDTO>("$ID == $0", chExerciseId).first().find()
//            chExercise?.let { e ->
//                // Eliminar todos los datos relacionados
//                val dataId = e.exerciseId + e.dayId
//                val data = query<DataDTO>("$DATA_ID == $0", dataId).find()
//                delete(data)
//
//                // Actualizar el contador de ejercicios del día
//                val day = query<DayDTO>("$ID == $0", e.dayId).first().find()
//                day?.let {
//                    it.exercises -= 1
//                }
//
//                // Eliminar el chExercise
//                delete(e)
//            }
//        }
//    }
//
//    override fun createCustomExercise(createdExercise: CreatedExercise) {
//        val exercises = realmDatabase.getObjectsFromRealm {
//            query<ExerciseDTO>("$CATEGORY == $0", createdExercise.category).find()
//        }.sortedBy { it.order }
//        val order = exercises.last().order + 10
//
//        val exerciseToAdd = toExerciseDTO(o = order, cE = createdExercise)
//        saveExerciseInRealm(exerciseToAdd)
//    }
//
//    override suspend fun editCustomExercise(createdExercise: CreatedExercise, id: String) {
//        realmDatabase.write {
//            val exercise = query<ExerciseDTO>("$ID == $0", id).first().find()
//            exercise?.let { e ->
//                e.order.let { order ->
//                    val n = toExerciseDTO(id, order, createdExercise)
//                    e.order = order
//                    e.imageURL = n.imageURL
//                    e.gifURL = n.gifURL
//                    e.name = n.name
//                    e.description = n.description
//                    e.muscles = n.muscles
//                    e.muscleWikiURL = n.muscleWikiURL
//                }
//            }
//        }
//    }
//
//    override fun deleteCustomExercise(createdExerciseId: String) {
//        realmDatabase.deleteObject(ExerciseDTO::class, ID, createdExerciseId)
//    }
//}