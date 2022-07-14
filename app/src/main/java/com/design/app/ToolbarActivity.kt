package com.design.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.design.app.databinding.ActivityToolbarBinding

class ToolbarActivity : AppCompatActivity() {

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.dark -> Toast.makeText(this, "OnMenuClick", Toast.LENGTH_SHORT).show()
        }
        return true
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val vb = ActivityToolbarBinding.inflate(layoutInflater)
        setContentView(vb.root)

        vb.toolbar1.initForActivity(this) {
            Toast.makeText(this, "onNavigationClick", Toast.LENGTH_SHORT).show()
        }
        vb.toolbar6.initForActivity(this)
        vb.toolbar5.initForActivity(this as AppCompatActivity) {
            Toast.makeText(this, "onNavigationClick", Toast.LENGTH_SHORT).show()
        }
        vb.toolbar5.changeNavigatorIcon(com.design.chili.R.drawable.ic_simple_plus)
        vb.toolbar5.setIconClickListener {
            Toast.makeText(this, "OnIconClick", Toast.LENGTH_SHORT).show()
        }
    }
}