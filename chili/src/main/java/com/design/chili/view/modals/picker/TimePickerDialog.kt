package com.design.chili.view.modals.picker

import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.TimePicker
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import com.design.chili.R
import com.design.chili.extensions.setOnSingleClickListener
import java.util.*

class TimePickerDialog: DialogFragment() {

    lateinit var view: TimePickerDialogVariables

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.chili_view_time_picker_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews(view)
        setupPicker(savedInstanceState)
    }

    private fun setupViews(view: View) {
        this.view = TimePickerDialogVariables(
            tvTitle = view.findViewById(R.id.tv_title),
            timePicker = view.findViewById(R.id.timePicker),
            btnDone = view.findViewById(R.id.btn_done)
        )
        this.view.run {
            timePicker.setIs24HourView(true)
            tvTitle.text = arguments?.getString(TIME_ARG_TITLE)
            btnDone.text = arguments?.getString(TIME_ARG_BUTTON_TEXT)
            btnDone.setOnSingleClickListener {
                setFragmentResult()
                dismiss()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putSerializable(TIME_ARG_CURRENT_TIME, getSelectedTime())
        super.onSaveInstanceState(outState)
    }

    private fun setFragmentResult() {
        parentFragmentManager.setFragmentResult(TIME_PICKER_DIALOG_RESULT, bundleOf(
            TIME_ARG_SELECTED_TIME to getSelectedTime())
        )
    }

    private fun getSelectedTime(): Calendar {
        val result = Calendar.getInstance()
        view.timePicker.run {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                result.set(Calendar.HOUR, hour)
                result.set(Calendar.MINUTE, minute)
            }
        }
        return result
    }

    private fun setupPicker(savedInstanceState: Bundle?) {
        val currentDate = when (savedInstanceState != null) {
            true -> savedInstanceState.getSerializable(TIME_ARG_CURRENT_TIME) as? Calendar
            else -> requireArguments().getSerializable(TIME_ARG_CURRENT_TIME) as? Calendar
        }
        currentDate ?: Calendar.getInstance().run {
            view.timePicker.setIs24HourView(true)
        }
    }

    companion object {
        const val TIME_PICKER_DIALOG_RESULT = "timePickerDialogResult"

        const val TIME_ARG_SELECTED_TIME = "selectedTime"
        private const val TIME_ARG_CURRENT_TIME = "currentTime"
        private const val TIME_ARG_TITLE = "title"
        private const val TIME_ARG_BUTTON_TEXT = "buttonText"

        fun create(
            buttonText: String,
            titleText: String,
            currentTime: Calendar = Calendar.getInstance(),
        ): TimePickerDialog {
            return TimePickerDialog().apply {
                arguments = bundleOf(
                    TIME_ARG_CURRENT_TIME to currentTime,
                    TIME_ARG_TITLE to titleText,
                    TIME_ARG_BUTTON_TEXT to buttonText
                )
            }
        }
    }
}

data class TimePickerDialogVariables(
    val tvTitle: TextView,
    val timePicker: TimePicker,
    val btnDone: Button,
)