package id.anantyan.synrgychapter4.presentation.main

import android.app.UiModeManager
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.isVisible
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import id.anantyan.synrgychapter4.R
import id.anantyan.synrgychapter4.common.DataStoreManager
import id.anantyan.synrgychapter4.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), NavController.OnDestinationChangedListener {

    private lateinit var binding: ActivityMainBinding
    private val datastore: DataStoreManager by lazy { DataStoreManager(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bindObserver()
        bindView()
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        when (destination.id) {
            R.id.homeFragment -> binding.bottomNav.isVisible = true
            R.id.profileFragment -> binding.bottomNav.isVisible = true
            R.id.addProductFragment -> binding.bottomNav.isVisible = true
            R.id.editProductFragment -> binding.bottomNav.isVisible = true
            else -> binding.bottomNav.isVisible = false
        }
    }

    private fun bindObserver() {
        datastore.getTheme().onEach {
            val legacy = if (it) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
            val latest = if (it) UiModeManager.MODE_NIGHT_YES else UiModeManager.MODE_NIGHT_NO

            when {
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
                    val uiModeManager = getSystemService(Context.UI_MODE_SERVICE) as UiModeManager
                    uiModeManager.setApplicationNightMode(latest)
                }
                Build.VERSION.SDK_INT < Build.VERSION_CODES.S -> {
                    AppCompatDelegate.setDefaultNightMode(legacy)
                }
            }
        }.flowWithLifecycle(lifecycle).launchIn(lifecycleScope)
    }

    private fun bindView() {
        val host = supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        val navController = host.navController
        navController.addOnDestinationChangedListener(this)

        binding.bottomNav.setupWithNavController(navController)
    }

    fun bottomNav(bool: Boolean = false) {
        binding.bottomNav.isVisible = bool
    }
}