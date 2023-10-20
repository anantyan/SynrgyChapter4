package id.anantyan.synrgychapter4.common.shared_preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager

class SharedPreferences(context: Context) : SharedHelper {

    private var prefShared: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    override fun setLogin(value: Boolean) = prefShared.set(LOOGED, value)
    override fun getLogin() = prefShared.getBoolean(LOOGED, false)

    companion object {
        private const val LOOGED = "LOGIN"
    }
}

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