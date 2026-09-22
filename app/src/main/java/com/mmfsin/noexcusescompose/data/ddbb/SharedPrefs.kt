package com.mmfsin.noexcusescompose.data.ddbb

import android.content.SharedPreferences
import androidx.core.content.edit
import com.mmfsin.noexcusescompose.util.SP_MUSCULAR_GROUPS_SERVER
import com.mmfsin.noexcusescompose.util.SP_VERSION_SAVED
import javax.inject.Inject

class SharedPrefs @Inject constructor(
    private val prefs: SharedPreferences
) {
    /** VERSION */
    fun getVersionSaved(): Long = prefs.getLong(SP_VERSION_SAVED, -1)
    fun updateVersionSaved(value: Long) = prefs.edit { putLong(SP_VERSION_SAVED, value) }

    /** MUSCULAR GROUPS SERVER */
    fun getMuscularGroupsFromServer(): Boolean = prefs.getBoolean(SP_MUSCULAR_GROUPS_SERVER, true)
    fun updateMuscularGroupsFromServer(value: Boolean) = prefs.edit { putBoolean(SP_MUSCULAR_GROUPS_SERVER, value) }

    fun restartValues(){
        prefs.edit {
            putBoolean(SP_MUSCULAR_GROUPS_SERVER, true)
        }
    }
}