package com.design.app.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.design.app.ToolbarActivity
import com.design.app.base.BaseFragment
import com.design.app.databinding.FragmentMainBinding

class MainFragment : BaseFragment<FragmentMainBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vb.text.setOnClickListener {
            openFragment(TextAppearancesFragment())
        }
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
        vb.cellsNew.setOnClickListener {
            openFragment(BaseCellViewsFragment())
        }
        vb.snackbar.setOnClickListener {
            openFragment(SnackbarFragment())
        }
        vb.common.setOnClickListener {
            openFragment(CommonViewsFragment())
        }
        vb.bottomSheet.setOnClickListener {
            openFragment(BottomSheetsFragment())
        }
        vb.toolbars.setOnClickListener {
            startActivity(Intent(requireActivity(), ToolbarActivity::class.java))
        }
        vb.pickers.setOnClickListener {
            openFragment(DatePickerFargment())
        }
    }

    override fun inflateViewBinging(): FragmentMainBinding {
        return FragmentMainBinding.inflate(layoutInflater)
    }
}