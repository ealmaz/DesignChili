package com.design.app.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.design.app.base.BaseFragment
import com.design.app.databinding.FrgmentBottomSheetsBinding
import com.design.chili.view.bottom_sheet.ActionBottomSheet
import com.design.chili.view.bottom_sheet.ActionBottomSheetButton
import com.design.chili.view.bottom_sheet.ActionBottomSheetButtonType

class BottomSheetsFragment : BaseFragment<FrgmentBottomSheetsBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vb.action.setOnClickListener {
            ActionBottomSheet.create(listOf(
                ActionBottomSheetButton("First", ActionBottomSheetButtonType.SIMPLE, {Toast.makeText(context, "OnClcik first", Toast.LENGTH_SHORT).show()}),
                ActionBottomSheetButton("Second", ActionBottomSheetButtonType.SIMPLE, {Toast.makeText(context, "OnClcik second", Toast.LENGTH_SHORT).show()}),
                ActionBottomSheetButton("Third", ActionBottomSheetButtonType.SIMPLE, {Toast.makeText(context, "OnClcik third", Toast.LENGTH_SHORT).show()}),
                ActionBottomSheetButton("Cancel", ActionBottomSheetButtonType.ACCENT, {dismiss()}),
            )).show(childFragmentManager, null)
        }
    }

    override fun inflateViewBinging(): FrgmentBottomSheetsBinding {
        return FrgmentBottomSheetsBinding.inflate(layoutInflater)
    }
}