package com.mmfsin.noexcusescompose.data.repository

import com.google.firebase.database.FirebaseDatabase
import com.mmfsin.noexcusescompose.data.ddbb.SharedPrefs
import com.mmfsin.noexcusescompose.data.ddbb.daos.StretchDAO
import com.mmfsin.noexcusescompose.data.mappers.toStretchList
import com.mmfsin.noexcusescompose.data.models.StretchDTO
import com.mmfsin.noexcusescompose.domain.interfaces.IStretchRepository
import com.mmfsin.noexcusescompose.domain.models.Stretch
import com.mmfsin.noexcusescompose.util.STRETCH
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