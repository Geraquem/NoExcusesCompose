package com.mmfsin.noexcusescompose.data.ddbb

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.mmfsin.noexcusescompose.util.DS_PINNED_NOTE
import com.mmfsin.noexcusescompose.util.DS_PINNED_ROUTINE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStorePrefs @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    fun getPinnedRoutine(): Flow<String> =
        dataStore.data.map { preferences -> preferences[PINNED_ROUTINE] ?: "" }

    suspend fun updatePinnedRoutine(value: String) {
        dataStore.edit { preferences -> preferences[PINNED_ROUTINE] = value }
    }

    fun getPinnedNote(): Flow<String> =
        dataStore.data.map { preferences -> preferences[PINNED_NOTE] ?: "" }

    suspend fun updatePinnedNote(value: String) {
        dataStore.edit { preferences -> preferences[PINNED_NOTE] = value }
    }

    companion object {
        val PINNED_ROUTINE = stringPreferencesKey(DS_PINNED_ROUTINE)
        val PINNED_NOTE = stringPreferencesKey(DS_PINNED_NOTE)
    }
}