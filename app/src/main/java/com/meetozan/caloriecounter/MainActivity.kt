package com.meetozan.caloriecounter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.meetozan.caloriecounter.ui.main.addmeal.AddMealFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            supportFragmentManager.commit {
                replace<AddMealFragment>(R.id.fragmentContainerView)
                setReorderingAllowed(true)
                addToBackStack("replacement")
            }
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
    }
}