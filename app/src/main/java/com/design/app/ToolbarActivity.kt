package com.design.app

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import com.design.app.databinding.ActivityToolbarBinding
import com.design.chili.view.navigation_components.ChiliToolbar

class ToolbarActivity : AppCompatActivity() {


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val vb = ActivityToolbarBinding.inflate(layoutInflater)
        setContentView(vb.root)
        vb.toolbar.initToolbar(ChiliToolbar.Configuration(
            this,
            title = "Transparent Toolbar",
            onNavigateUpClick = { onBackPressed() },
            isNavigateUpButtonEnabled = true
        ))

        vb.toolbar2.initToolbar(ChiliToolbar.Configuration(
            this,
            onNavigateUpClick = { onBackPressed() },
        ))

        vb.toolbar4.initToolbar(ChiliToolbar.Configuration(
            this,
            onNavigateUpClick = { onBackPressed() },
            isNavigateUpButtonEnabled = true
        ))

        vb.toolbar5.initToolbar(ChiliToolbar.Configuration(
            this,
            onNavigateUpClick = { onBackPressed() },
        ))

        vb.toolbar6.initToolbar(ChiliToolbar.Configuration(
            this,
            onNavigateUpClick = { onBackPressed() },
        ))

        vb.toolbar7.initToolbar(ChiliToolbar.Configuration(
            this,
            onNavigateUpClick = { onBackPressed() },
        ))

        vb.toolbar3.initToolbar(ChiliToolbar.Configuration(
            this,
            isNavigateUpButtonEnabled = true,
        ))
    }
}