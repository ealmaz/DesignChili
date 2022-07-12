package com.design.chili.view.bottom_sheet

import android.os.Bundle
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

    override val layoutResId = R.layout.view_action_bottom_sheet

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(layoutResId, container, false)
        renderActionButtons(view)
        return view
    }

    private fun renderActionButtons(view: View) {
        val rootView = view.findViewById<LinearLayout>(R.id.ll_root)
        val buttons = arguments?.get(BUTTONS_ARG) as? List<ActionBottomSheetButton>
        buttons?.forEach { buttonInfo ->
            rootView.addView(createActionButtonView(buttonInfo))
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