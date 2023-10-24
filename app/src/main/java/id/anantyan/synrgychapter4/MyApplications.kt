package id.anantyan.synrgychapter4

import android.app.Application
import android.app.UiModeManager
import android.content.Context
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Lifecycle
import id.anantyan.synrgychapter4.common.DataStoreManager
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MyApplications : Application() {

}