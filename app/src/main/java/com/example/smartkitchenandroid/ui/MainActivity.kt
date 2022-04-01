package com.example.smartkitchenandroid.ui

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.smartkitchenandroid.R
import com.example.smartkitchenandroid.databinding.ActivityMainBinding
import com.example.smartkitchenandroid.models.Roles

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initNavigation()
        navigateAccordingToRole()
    }

    private fun navigateAccordingToRole() {
        if (isAlreadySignedIn())
            if (getRole() != "")
                if (getRole() == Roles.WAITER.name)
                    navController.navigate(R.id.action_global_waiter)
                else
                    navController.navigate(R.id.action_global_kitchenCoordinator)
            else
                navController.navigate(R.id.action_global_signIn)
    }

    private fun initNavigation() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragment_container_view) as NavHostFragment
        navController = navHostFragment.navController
        val topLevelDestinations =
            setOf(R.id.signInFragment, R.id.waiterFragment, R.id.kitchenCoordinatorFragment)
        val appBarConfiguration = AppBarConfiguration(topLevelDestinations)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
        setSupportActionBar(binding.toolbar)
    }

    private fun isAlreadySignedIn(): Boolean {
        val sharedPref = this.getPreferences(Context.MODE_PRIVATE)
        return sharedPref.getBoolean(getString(R.string.pref_already_signed_in), false)
    }

    private fun getRole(): String {
        val sharedPref = this.getPreferences(Context.MODE_PRIVATE)
        return sharedPref.getString(getString(R.string.pref_role), "").toString()
    }
}