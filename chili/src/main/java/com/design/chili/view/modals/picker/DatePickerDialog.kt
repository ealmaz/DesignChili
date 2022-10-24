package com.design.chili.view.modals.picker

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import com.design.chili.R
import com.design.chili.extensions.setOnSingleClickListener
import java.util.*

class DatePickerDialog : DialogFragment() {

    lateinit var view: DatePickerDialogVariables

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
        return inflater.inflate(R.layout.chili_view_date_picker_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews(view)
        setupPicker(savedInstanceState)
    }

    private fun setupViews(view: View) {
        this.view = DatePickerDialogVariables(
            tvTitle = view.findViewById(R.id.tv_title),
            datePicker = view.findViewById(R.id.datePicker),
            btnDone = view.findViewById(R.id.btn_done)
        )
        this.view.run {
            tvTitle.text = arguments?.getString(ARG_TITLE)
            btnDone.text = arguments?.getString(ARG_BUTTON_TEXT)
            btnDone.setOnSingleClickListener {
                setFragmentResult()
                dismiss()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putSerializable(ARG_CURRENT_DATE, getSelectedDate())
        super.onSaveInstanceState(outState)
    }

    private fun setFragmentResult() {
        parentFragmentManager.setFragmentResult(PICKER_DIALOG_RESULT, bundleOf(
            ARG_SELECTED_DATE to getSelectedDate()
        ))
    }

    private fun getSelectedDate(): Calendar {
        val result = Calendar.getInstance()
        view.datePicker.run {
            result.set(year, month, dayOfMonth, 0, 0, 0)
        }
        return result
    }

    private fun setupPicker(savedInstanceState: Bundle?) {
        val currentDate = when (savedInstanceState != null) {
            true -> savedInstanceState.getSerializable(ARG_CURRENT_DATE) as? Calendar
            else -> requireArguments().getSerializable(ARG_CURRENT_DATE) as? Calendar
        }
        (currentDate ?: Calendar.getInstance()).run {
            view.datePicker.updateDate(
                get(Calendar.YEAR),
                get(Calendar.MONTH),
                get(Calendar.DAY_OF_MONTH)
            )
        }

        val endLimitDate = requireArguments().getSerializable(ARG_END_LIMIT_DATE) as? Calendar
        endLimitDate?.let { view.datePicker.maxDate = endLimitDate.timeInMillis }

        val startLimitDate = requireArguments().getSerializable(ARG_START_LIMIT_DATE) as? Calendar
        startLimitDate?.let { view.datePicker.minDate = startLimitDate.timeInMillis }
    }

    companion object {
        const val PICKER_DIALOG_RESULT = "pickerDialogResult"

        const val ARG_SELECTED_DATE = "selectedDate"
        private const val ARG_CURRENT_DATE = "currentDate"
        private const val ARG_END_LIMIT_DATE = "endLimitDate"
        private const val ARG_START_LIMIT_DATE = "startLimitDate"
        private const val ARG_TITLE = "title"
        private const val ARG_BUTTON_TEXT = "buttonText"

        fun create(
            buttonText: String,
            titleText: String,
            currentDate: Calendar = Calendar.getInstance(),
            startLimitDate: Calendar? = null,
            endLimitDate: Calendar? = null,
        ): DatePickerDialog {
            return DatePickerDialog().apply {
                arguments = bundleOf(
                    ARG_CURRENT_DATE to currentDate,
                    ARG_END_LIMIT_DATE to endLimitDate,
                    ARG_START_LIMIT_DATE to startLimitDate,
                    ARG_TITLE to titleText,
                    ARG_BUTTON_TEXT to buttonText
                )
            }
        }
    }
}

data class DatePickerDialogVariables(
    val tvTitle: TextView,
    val datePicker: DatePicker,
    val btnDone: Button,
)