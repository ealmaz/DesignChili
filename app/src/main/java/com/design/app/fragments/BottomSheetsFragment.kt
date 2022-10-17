package com.design.app.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.design.app.R
import com.design.app.base.BaseFragment
import com.design.app.databinding.FrgmentBottomSheetsBinding
import com.design.chili.view.modals.base.BaseFragmentBottomSheetDialogFragment
import com.design.chili.view.modals.bottom_sheet.*
import com.design.chili.view.modals.in_app.InAppPushBottomSheet
import com.google.android.material.bottomsheet.BottomSheetBehavior

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
        vb.detail.setOnClickListener {
            DetailedInfoBottomSheet.Builder()
                .setIcon(R.drawable.ic_cat)
                .setMessage("Текстовый блок, который содержит много текста и не может уместиться в четыре строки (как в маленьком Bottom-sheet).\n\n" +
                        "Возможно имеет какую-то инструкцию или подробное описание функционал. Плюс тут есть картиночка. \n\n" +
                        "Высота зависит от контента.")
                .setPrimaryButton("Понятно" to {Toast.makeText(context, "Понятно", Toast.LENGTH_SHORT).show()})
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
        vb.custom.setOnClickListener {
            CustomFragmentBottomSheet().show(childFragmentManager)
        }
        vb.inApp.setOnClickListener {
            InAppPushBottomSheet.Builder()
                .setBtnMoreInfo("Подробнее" to {
                    dismiss()
                })
                .setDescription("Описание описания, которое описывает описанное описание описанного описания,\n" +
                        "максимум из 190 символов, но если ничего \n\n" +
                        "не помещается, не проблема, потому что у нас всегда есть спецсимвол такой как троеточиеef evremiv ervmeive ervnervn ervnervne enruvneuv eunrvuernv eurnvueirv eurnvuev eurnvuev ervneurv")
                .setTitle("Максимальная длина заголовка равна 60 символам, а если не помещается то verververveverv erverv erverv erverv erve")
                .setOnBannerClick {
                    dismiss()
                }
                .build()
                .show(childFragmentManager)
        }
        vb.inAppBanner.setOnClickListener {
            InAppPushBottomSheet.Builder()
                .setBannerUrl("https://cdn.pixabay.com/photo/2016/11/29/12/13/fence-1869401_1280.jpg")
                .setBtnMoreInfo("Подробнее" to {
                    dismiss()
                })
                .setDescription("Описание описания, которое описывает описанное описание описанного описания,\n" +
                        "максимум из 190 символов, но если ничего \n\n" +
                        "не помещается, не проблема, потому что у нас всегда есть спецсимвол такой как троеточиеef evremiv ervmeive ervnervn ervnervne enruvneuv eunrvuernv eurnvueirv eurnvuev eurnvuev ervneurv")
                .setTitle("Максимальная длина заголовка равна 60 символам, а если не помещается то verververveverv erverv erverv erverv erve")
                .setOnBannerClick {
                    dismiss()
                }
                .build()
                .show(childFragmentManager)
        }
        vb.visbileBottomSheet.setOnClickListener {
            openFragment(InteractiveBottomSheetFragment())
        }
    }

    override fun inflateViewBinging(): FrgmentBottomSheetsBinding {
        return FrgmentBottomSheetsBinding.inflate(layoutInflater)
    }
}

class CustomFragmentBottomSheet : BaseFragmentBottomSheetDialogFragment() {

    override val topDrawableVisible: Boolean
        get() = true
    override val hasCloseIcon: Boolean
        get() = true
    override var isHideable: Boolean = false
    override val isBackButtonEnabled: Boolean
        get() = false

    override fun setupBottomSheetBehavior(behavior: BottomSheetBehavior<*>?) {
        behavior?.peekHeight = getWindowHeight() * 30 / 100
        behavior?.isHideable = isHideable
        behavior?.halfExpandedRatio = 0.3f
        behavior?.state = BottomSheetBehavior.STATE_HALF_EXPANDED
    }

    override fun createFragment(): Fragment {
        return CommonViewsFragment()
    }

}