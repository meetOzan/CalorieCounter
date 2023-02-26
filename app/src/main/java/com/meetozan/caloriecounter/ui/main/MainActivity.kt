package com.meetozan.caloriecounter.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.meetozan.caloriecounter.R
import com.meetozan.caloriecounter.ui.main.addmeal.AddMealActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this@MainActivity,AddMealActivity::class.java)
            startActivity(intent)
        }
        bottomBar()
    }

    private fun bottomBar() {
        val bottomBar = findViewById<BottomNavigationView>(R.id.bottom_bar)
        bottomBar.background = null
        bottomBar.menu.getItem(2).isEnabled = false

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        NavigationUI.setupWithNavController(
            bottomBar,
            navHostFragment.navController
        )

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        val bottomAppBar = findViewById<BottomAppBar>(R.id.bottomAppBar)

        navHostFragment.navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.viewPagerFragment) {
                bottomAppBar.visibility = View.GONE
                fab.visibility = View.GONE
            } else {
                bottomAppBar.visibility = View.VISIBLE
                fab.visibility = View.VISIBLE
            }
        }
    }
}