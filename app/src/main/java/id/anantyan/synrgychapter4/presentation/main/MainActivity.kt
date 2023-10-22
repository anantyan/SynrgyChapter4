package id.anantyan.synrgychapter4.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import id.anantyan.synrgychapter4.R
import id.anantyan.synrgychapter4.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), NavController.OnDestinationChangedListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bindView()
    }

    private fun bindView() {
        val host = supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        val navController = host.navController
        navController.addOnDestinationChangedListener(this)

        binding.bottomNav.setupWithNavController(navController)
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        when (destination.id) {
            R.id.homeFragment -> binding.bottomNav.isVisible = true
            R.id.profileFragment -> binding.bottomNav.isVisible = true
            else -> binding.bottomNav.isVisible = false
        }
    }
}