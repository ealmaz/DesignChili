package com.design.chili.view.bottom_sheet

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.os.bundleOf
import com.design.chili.R
import com.design.chili.extensions.setOnSingleClickListener
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.io.Serializable

class ActionBottomSheet : BaseBottomSheetDialogFragment() {

    override fun createContentView(inflater: LayoutInflater, container: ViewGroup?): View {
        val linearLayout = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
        }
        renderActionButtons(linearLayout)
        return linearLayout
    }

    private fun renderActionButtons(layout: LinearLayout) {
        val buttons = arguments?.get(BUTTONS_ARG) as? List<ActionBottomSheetButton>
        buttons?.forEach { buttonInfo ->
            layout.addView(createActionButtonView(buttonInfo))
        }
    }

    private fun createActionButtonView(buttonInfo: ActionBottomSheetButton): TextView {
        val buttonStyle = when(buttonInfo.type) {
            ActionBottomSheetButtonType.SIMPLE -> R.style.ActionBottomSheetButtonStyle
            ActionBottomSheetButtonType.ACCENT -> R.style.ActionBottomSheetAccentButtonStyle
        }
        return TextView(context, null, 0, buttonStyle).apply {
            text = buttonInfo.title
            setOnSingleClickListener { buttonInfo.onClickAction.invoke(this@ActionBottomSheet) }
        }
    }

    companion object {

        const val BUTTONS_ARG = "buttons"

        fun create(buttons: List<ActionBottomSheetButton>): ActionBottomSheet {
            return ActionBottomSheet().apply {
                arguments = bundleOf(BUTTONS_ARG to buttons)
            }
        }
    }
}

enum class ActionBottomSheetButtonType {
    SIMPLE, ACCENT
}

data class ActionBottomSheetButton(
    val title: String?,
    val type: ActionBottomSheetButtonType,
    val onClickAction: BottomSheetDialogFragment.() -> Unit
): Serializable