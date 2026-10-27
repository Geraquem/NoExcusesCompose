package com.mmfsin.noexcuses.data.repository

import com.google.firebase.database.FirebaseDatabase
import com.mmfsin.noexcuses.data.ddbb.SharedPrefs
import com.mmfsin.noexcuses.data.ddbb.daos.StretchDAO
import com.mmfsin.noexcuses.data.mappers.toStretchList
import com.mmfsin.noexcuses.data.models.StretchDTO
import com.mmfsin.noexcuses.domain.interfaces.IStretchRepository
import com.mmfsin.noexcuses.domain.models.Stretch
import com.mmfsin.noexcuses.util.STRETCH
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class StretchRepository @Inject constructor(
    val prefs: SharedPrefs,
    val stretchDAO: StretchDAO,
) : IStretchRepository {

    override suspend fun getStretchingData(): List<Stretch> {
        if (prefs.getStretchDataFromServer()) {
            val snapshot = FirebaseDatabase
                .getInstance()
                .getReference(STRETCH)
                .get()
                .await()

            val firebaseData = snapshot.children
                .flatMap { categorySnapshot ->
                    categorySnapshot.children.mapNotNull { exerciseSnapshot ->
                        exerciseSnapshot.getValue(StretchDTO::class.java)
                    }
                }

            if (firebaseData.isNotEmpty()) {
                prefs.updateStretchDataFromServer(false)
                stretchDAO.insertStretchData(firebaseData)
            }
        }

        return stretchDAO.getStretchingData().toStretchList()
    }


}