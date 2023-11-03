package id.anantyan.synrgychapter4.common

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager

private fun SharedPreferences.settings(operation: (SharedPreferences.Editor) -> Unit) {
    this.edit(true) {
        operation(this)
        this.apply()
    }
}

private fun SharedPreferences.set(key: String, value: Any?) {
    when(value) {
        is String -> this.settings { it.putString(key, value) }
        is Boolean -> this.settings { it.putBoolean(key, value) }
        is Int -> this.settings { it.putInt(key, value) }
        is Long -> this.settings { it.putLong(key, value) }
        is Float -> this.settings { it.putFloat(key, value) }
        else -> throw UnsupportedOperationException("Not yet implemented")
    }
}