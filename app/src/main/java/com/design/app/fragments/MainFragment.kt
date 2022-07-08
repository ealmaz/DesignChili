package com.design.app.fragments

import android.os.Bundle
import android.view.View
import com.design.app.base.BaseFragment
import com.design.app.databinding.FragmentMainBinding

class MainFragment : BaseFragment<FragmentMainBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vb.buttons.setOnClickListener {
            openFragment(ButtonsFragment())
        }
        vb.fileds.setOnClickListener {
            openFragment(InputFields())
        }

        vb.cards.setOnClickListener {
            openFragment(CardsFragment())
        }
        vb.cells.setOnClickListener {
            openFragment(CellsFragment())
        }
        vb.snackbar.setOnClickListener {
            openFragment(SnackbarFragment())
        }
    }

    override fun inflateViewBinging(): FragmentMainBinding {
        return FragmentMainBinding.inflate(layoutInflater)
    }
}