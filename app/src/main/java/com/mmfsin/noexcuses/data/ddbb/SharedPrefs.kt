package com.mmfsin.noexcuses.data.ddbb

import android.content.SharedPreferences
import androidx.core.content.edit
import com.mmfsin.noexcuses.util.SP_DF_ROUTINES_SERVER
import com.mmfsin.noexcuses.util.SP_EXERCISES_SERVER
import com.mmfsin.noexcuses.util.SP_MENU_IMAGES
import com.mmfsin.noexcuses.util.SP_MIGRATION_COMPLETE
import com.mmfsin.noexcuses.util.SP_MUSCULAR_GROUPS_SERVER
import com.mmfsin.noexcuses.util.SP_STRETCH_SERVER
import com.mmfsin.noexcuses.util.SP_VERSION_SAVED
import javax.inject.Inject

class SharedPrefs @Inject constructor(
    private val prefs: SharedPreferences
) {
    /** VERSION */
    fun getVersionSaved(): Long = prefs.getLong(SP_VERSION_SAVED, -1)
    fun updateVersionSaved(value: Long) = prefs.edit { putLong(SP_VERSION_SAVED, value) }

    /** VERSION */
    fun getMigrationState(): Boolean = prefs.getBoolean(SP_MIGRATION_COMPLETE, false)
    fun updateMigrationState(value: Boolean) = prefs.edit { putBoolean(SP_MIGRATION_COMPLETE, value) }

    /** MENU IMAGES */
    fun getMenuImages(): Boolean = prefs.getBoolean(SP_MENU_IMAGES, true)
    fun updateMenuImages(value: Boolean) = prefs.edit { putBoolean(SP_MENU_IMAGES, value) }

    /** DF ROUTINES SERVER */
    fun getDfRoutinesFromServer(): Boolean = prefs.getBoolean(SP_DF_ROUTINES_SERVER, true)
    fun updateDfRoutinesFromServer(value: Boolean) = prefs.edit { putBoolean(SP_DF_ROUTINES_SERVER, value) }

    /** MUSCULAR GROUPS SERVER */
    fun getMuscularGroupsFromServer(): Boolean = prefs.getBoolean(SP_MUSCULAR_GROUPS_SERVER, true)
    fun updateMuscularGroupsFromServer(value: Boolean) = prefs.edit { putBoolean(SP_MUSCULAR_GROUPS_SERVER, value) }

    /** EXERCISES SERVER */
    fun getExercisesFromServer(): Boolean = prefs.getBoolean(SP_EXERCISES_SERVER, true)
    fun updateExercisesFromServer(value: Boolean) = prefs.edit { putBoolean(SP_EXERCISES_SERVER, value) }

    /** STRETCHING DATA SERVER */
    fun getStretchDataFromServer(): Boolean = prefs.getBoolean(SP_STRETCH_SERVER, true)
    fun updateStretchDataFromServer(value: Boolean) = prefs.edit { putBoolean(SP_STRETCH_SERVER, value) }

    fun restartValues() {
        prefs.edit {
            putBoolean(SP_MENU_IMAGES, true)
            putBoolean(SP_DF_ROUTINES_SERVER, true)
            putBoolean(SP_MUSCULAR_GROUPS_SERVER, true)
            putBoolean(SP_EXERCISES_SERVER, true)
            putBoolean(SP_STRETCH_SERVER, true)
        }
    }
}