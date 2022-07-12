package com.design.app.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.design.app.R
import com.design.app.base.BaseFragment
import com.design.app.databinding.FrgmentBottomSheetsBinding
import com.design.chili.view.bottom_sheet.*

class BottomSheetsFragment : BaseFragment<FrgmentBottomSheetsBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vb.action.setOnClickListener {
            ActionBottomSheet.create(listOf(
                ActionBottomSheetButton("First", ActionBottomSheetButtonType.SIMPLE, {Toast.makeText(context, "OnClcik first", Toast.LENGTH_SHORT).show()}),
                ActionBottomSheetButton("Second", ActionBottomSheetButtonType.SIMPLE, {Toast.makeText(context, "OnClcik second", Toast.LENGTH_SHORT).show()}),
                ActionBottomSheetButton("Third", ActionBottomSheetButtonType.SIMPLE, {Toast.makeText(context, "OnClcik third", Toast.LENGTH_SHORT).show()}),
                ActionBottomSheetButton("Cancel", ActionBottomSheetButtonType.ACCENT, {dismiss()}),
            )).show(childFragmentManager)
        }
        vb.info.setOnClickListener {
            InfoBottomSheet.Builder()
                .setIcon(R.drawable.ic_cat)
                .setMessage("Текстовый блок, который содержит 120 символов, и этого количества, должно хватить для информации в четыре строки.")
                .setPrimaryButton("Понятно" to {Toast.makeText(context, "Понятно", Toast.LENGTH_SHORT).show()})
                .setSecondaryButton("Ясно" to {Toast.makeText(context, "Ясно", Toast.LENGTH_SHORT).show()})
                .build()
                .show(childFragmentManager)
        }
        vb.description.setOnClickListener {
            DescriptionBottomSheet.Builder()
                .setIcon(R.drawable.ic_cat)
                .setTitle("Title")
                .setDescription("Description")
                .setDescriptionSecondary("Secondary info")
                .setSecondaryButton("Понятно" to {Toast.makeText(context, "Понятно", Toast.LENGTH_SHORT).show()})
                .build()
                .show(childFragmentManager)
        }
    }

    override fun inflateViewBinging(): FrgmentBottomSheetsBinding {
        return FrgmentBottomSheetsBinding.inflate(layoutInflater)
    }
}