package com.mmfsin.noexcusescompose.data.repository

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.mmfsin.noexcusescompose.data.ddbb.SharedPrefs
import com.mmfsin.noexcusescompose.data.ddbb.daos.MuscularGroupsDAO
import com.mmfsin.noexcusescompose.data.mappers.toMuscularGroupList
import com.mmfsin.noexcusescompose.data.models.MuscularGroupDTO
import com.mmfsin.noexcusescompose.domain.interfaces.IMenuRepository
import com.mmfsin.noexcusescompose.domain.models.MuscularGroup
import com.mmfsin.noexcusescompose.util.MUSCULAR_GROUPS
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class MenuRepository @Inject constructor(
    val prefs: SharedPrefs,
    val muscularGroupsDAO: MuscularGroupsDAO,
) : IMenuRepository {

    override suspend fun checkVersion() {
        //        getDataFromFirebase(getSavedVersion())
    }

    override suspend fun getMuscularGroups(): List<MuscularGroup> {
        if (prefs.getMuscularGroupsFromServer()) {
            val snapshot = FirebaseDatabase
                .getInstance()
                .getReference(MUSCULAR_GROUPS)
                .get()
                .await()

            val firebaseMuscularGroups = snapshot.children
                .mapNotNull { it.getValue(MuscularGroupDTO::class.java) }

            if (firebaseMuscularGroups.isNotEmpty()) {
                prefs.updateMuscularGroupsFromServer(false)
                muscularGroupsDAO.insertMuscularGroups(firebaseMuscularGroups)
            }
            return firebaseMuscularGroups.toMuscularGroupList()

        } else return muscularGroupsDAO.getMuscularGroups().toMuscularGroupList()
    }

    //    override fun getMyActualRoutine(): Routine? {
    //        val myRoutines = realmDatabase.getObjectsFromRealm {
    //            query<MyRoutineDTO>("$ROUTINE_DOING_IT == $0", true).find()
    //        }
    //        if (myRoutines.isNotEmpty()) return myRoutines.first().toRoutine()
    //
    //        val dfRoutines = realmDatabase.getObjectsFromRealm {
    //            query<DefaultRoutineDTO>("$ROUTINE_DOING_IT == $0", true).find()
    //        }
    //        if (dfRoutines.isNotEmpty()) return dfRoutines.first().toRoutine()
    //        return null
    //    }
    //
    //    override fun getMyActualRoutineDays(routineId: String): List<Day> {
    //        val routine = getMyActualRoutine()
    //        routine?.let {
    //            if (routine.createdByUser) {
    //                val days = realmDatabase.getObjectsFromRealm {
    //                    query<DayDTO>("$ROUTINE_ID == $0", routineId).find()
    //                }
    //                return days.toDayListFromDayDTO()
    //            } else {
    //                val days = realmDatabase.getObjectsFromRealm {
    //                    query<DefaultDayDTO>("$ROUTINE_ID == $0", routineId).find()
    //                }
    //                return days.toDayListFromDefaultDayDTO()
    //            }
    //        }
    //        return emptyList()
    //    }
    //
    //    override suspend fun unpinRoutineFromMenu(routineId: String) {
    //        realmDatabase.write {
    //             DefaultRoutineDTO
    //            query<DefaultRoutineDTO>("$ID == $0", routineId).first().find()?.let {
    //                it.doingIt = false
    //            }
    //
    //             MyRoutineDTO
    //            query<MyRoutineDTO>("$ID == $0", routineId).first().find()?.let {
    //                it.doingIt = false
    //            }
    //        }
    //    }
    //
    //    override suspend fun unpinNoteFromMenu(noteId: String) {
    //        realmDatabase.write {
    //            val note = query<NoteDTO>("$ID == $0", noteId).first().find()
    //            note?.let {
    //                it.pinned = false
    //            }
    //        }
    //    }
    //
    //    override fun checkBodyImage(): Boolean {
    //        val sharedPrefs = context.getSharedPreferences(MY_SHARED_PREFS, MODE_PRIVATE)
    //        return sharedPrefs.getBoolean(BODY_IMAGE_WOMAN_SELECTED, false)
    //    }
    //
    //    override fun editBodyImage(womanImageSelected: Boolean) {
    //        val sharedPrefs = context.getSharedPreferences(MY_SHARED_PREFS, MODE_PRIVATE)
    //        sharedPrefs.edit().apply {
    //            putBoolean(BODY_IMAGE_WOMAN_SELECTED, womanImageSelected)
    //            apply()
    //        }
    //    }

    /*****************************************************************************************************************************************/
    private suspend fun insertDataInFirestore() {
        //        val db = Firebase.firestore
        //        val batch = db.batch()
        //
        //        val routineId = "routine_7"
        //        val dayId = "r7d3"
        //
        //        val listToInsert = getExercisesToInsert(dayId)
        //        //        val listToInsert = getDaysToInsert(routineId)
        //
        //        val latch = CountDownLatch(1)
        //
        //        val usersCollection = db.collection(DEFAULT_ROUTINES).document(routineId)
        //            .collection(DAYS)
        //            .document(dayId)
        //            .collection(EXERCISES)
        //
        //        for (user in listToInsert) {
        //            val newDocRef = user["id"]?.let { usersCollection.document(it) }
        //            if (newDocRef != null) {
        //                batch.set(newDocRef, user)
        //            }
        //        }
        //
        //        batch.commit()
        //            .addOnSuccessListener {
        //                latch.countDown()
        //            }
        //            .addOnFailureListener {
        //                latch.countDown()
        //            }
        //        withContext(Dispatchers.IO) { latch.await() }
    }

    private fun getDaysToInsert(routineId: String): List<HashMap<String, String>> {
        return listOf(
            hashMapOf(
                "id" to "r7d1",
                "routineId" to routineId,
                "name" to "Piernas y Core",
                "exercises" to "2000000000000000000",
            ),
            hashMapOf(
                "id" to "r7d2",
                "routineId" to routineId,
                "name" to "Pecho, Hombros y Tríceps",
                "exercises" to "2000000000000000000",
            ),
            hashMapOf(
                "id" to "r7d3",
                "routineId" to routineId,
                "name" to "Espalda, Bíceps y Core",
                "exercises" to "2000000000000000000",
            ),
            //            hashMapOf(
            //                "id" to "r8d4",
            //                "routineId" to routineId,
            //                "name" to "Pierna y abdomen",
            //                "exercises" to "2000000000000000000",
            //            ),
            //            hashMapOf(
            //                "id" to "r8d5",
            //                "routineId" to routineId,
            //                "name" to "TORSO (Pecho, Espalda, Hombro y Brazos)",
            //                "exercises" to "2000000000000000000",
            //            )
        )
    }

    private fun getExercisesToInsert(day: String): List<HashMap<String, String>> {
        return listOf(
            hashMapOf(
                "dayId" to day,
                "id" to "${day}e1",
                "exerciseId" to "espalda2",
                "reps" to "12,12,15,15",
                "desc" to "1.30",
            ),
            hashMapOf(
                "dayId" to day,
                "id" to "${day}e2",
                "exerciseId" to "espalda5",
                "reps" to "12,12,15,15",
                "desc" to "1.30",
            ),
            hashMapOf(
                "dayId" to day,
                "id" to "${day}e3",
                "exerciseId" to "espalda11",
                "reps" to "12,12,15,15",
                "desc" to "1.30",
            ),
            hashMapOf(
                "dayId" to day,
                "id" to "${day}e4",
                "exerciseId" to "espalda17",
                "reps" to "12,12,15,15",
                "desc" to "1.30",
            ),
            hashMapOf(
                "dayId" to day,
                "id" to "${day}e5",
                "exerciseId" to "biceps12",
                "reps" to "12,12,15,15",
                "desc" to "1.30",
            ),
            hashMapOf(
                "dayId" to day,
                "id" to "${day}e6",
                "exerciseId" to "biceps2",
                "reps" to "12,12,15,15",
                "desc" to "1.30",
            ),
            hashMapOf(
                "dayId" to day,
                "id" to "${day}e7",
                "exerciseId" to "core15",
                "reps" to "10,10,10,10",
                "desc" to "1",
            ),
            hashMapOf(
                "dayId" to day,
                "id" to "${day}e8",
                "exerciseId" to "cardio5",
                "reps" to "20'",
                "desc" to "-",
            )
        )
    }
    /*****************************************************************************************************************************************/
}