package com.mmfsin.noexcuses.data.repository

import com.google.firebase.database.FirebaseDatabase
import com.mmfsin.noexcuses.data.ddbb.SharedPrefs
import com.mmfsin.noexcuses.data.ddbb.daos.MenuDAO
import com.mmfsin.noexcuses.data.ddbb.daos.MuscularGroupsDAO
import com.mmfsin.noexcuses.data.mappers.toMenuImages
import com.mmfsin.noexcuses.data.mappers.toMuscularGroupList
import com.mmfsin.noexcuses.data.models.MenuImagesDTO
import com.mmfsin.noexcuses.data.models.MuscularGroupDTO
import com.mmfsin.noexcuses.domain.interfaces.IMenuRepository
import com.mmfsin.noexcuses.domain.models.MenuImages
import com.mmfsin.noexcuses.domain.models.MuscularGroup
import com.mmfsin.noexcuses.util.MENU_IMAGES
import com.mmfsin.noexcuses.util.MUSCULAR_GROUPS
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class MenuRepository @Inject constructor(
    val prefs: SharedPrefs,
    val menuDAO: MenuDAO,
    val muscularGroupsDAO: MuscularGroupsDAO,
) : IMenuRepository {

    override suspend fun checkVersion() {
        //        getDataFromFirebase(getSavedVersion())
    }

    override suspend fun getMenuImages(): MenuImages? {
        if (prefs.getMenuImages()) {
            val snapshot = FirebaseDatabase
                .getInstance()
                .getReference(MENU_IMAGES)
                .get()
                .await()

            val result = snapshot.getValue(MenuImagesDTO::class.java)
            result?.let {
                prefs.updateMenuImages(false)
                menuDAO.insertMenuImages(it)
            }
            return result?.toMenuImages()

        } else return menuDAO.getMenuImages()?.toMenuImages()
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
            return firebaseMuscularGroups.sortedBy { it.order }.toMuscularGroupList()

        } else return muscularGroupsDAO.getMuscularGroups().sortedBy { it.order }.toMuscularGroupList()
    }

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