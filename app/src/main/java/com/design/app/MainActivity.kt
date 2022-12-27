package com.design.app

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.fragment.app.Fragment
import com.design.app.fragments.MainFragment
import com.design.chili.view.navigation_components.ChiliToolbar

class MainActivity : AppCompatActivity() {

    private lateinit var toolbar: ChiliToolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupToolbar()
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment())
                .commit()
        }
    }

    private fun setupToolbar() {
        toolbar = findViewById<ChiliToolbar>(R.id.toolbar)
        toolbar.initToolbar(ChiliToolbar.Configuration(
            this,
            onNavigateUpClick = { onBackPressed() },
            isNavigateUpButtonEnabled = false
        ))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.dark -> AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
            R.id.light -> AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
        }
        return true
    }

    fun openFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .addToBackStack(null)
            .commit()
    }

    fun setTitle(title: String) {
        toolbar.setTitle(title)
    }

    fun setUpHomeEnabled(isEnabled: Boolean) {
        toolbar.isUpHomeEnabled(this, isEnabled)
    }
}