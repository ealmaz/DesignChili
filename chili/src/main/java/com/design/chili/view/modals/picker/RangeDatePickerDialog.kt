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

class RangeDatePickerDialog : DialogFragment() {

    lateinit var view: RangeDatePickerDialogVariables

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
        return inflater.inflate(R.layout.chili_view_range_date_picker_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews(view)
        setupPicker(savedInstanceState)
    }

    private fun setupViews(view: View) {
        this.view = RangeDatePickerDialogVariables(
            tvTitleTo = view.findViewById(R.id.tv_title_to),
            datePickerTo = view.findViewById(R.id.datePicker_to),
            tvTitleFrom= view.findViewById(R.id.tv_title_from),
            datePickerFrom = view.findViewById(R.id.datePicker_from),
            btnDone = view.findViewById(R.id.btn_done)
        )
        this.view.run {
            btnDone.text = arguments?.getString(ARG_BUTTON_TEXT)
            tvTitleFrom.text = arguments?.getString(ARG_START_TITLE)
            tvTitleTo.text = arguments?.getString(ARG_END_TITLE)
            btnDone.setOnSingleClickListener {
                setFragmentResult()
                dismiss()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putSerializable(ARG_CURRENT_START_DATE, getSelectedDate(DateType.FROM))
        outState.putSerializable(ARG_CURRENT_END_DATE, getSelectedDate(DateType.TO))
        super.onSaveInstanceState(outState)
    }

    private fun setFragmentResult() {
        parentFragmentManager.setFragmentResult(RANGE_PICKER_DIALOG_RESULT, bundleOf(
            ARG_SELECTED_START_DATE to getSelectedDate(DateType.FROM),
            ARG_SELECTED_END_DATE to getSelectedDate(DateType.TO)
        ))
    }

    private fun getSelectedDate(type: DateType): Calendar {
        val result = Calendar.getInstance()
        when (type) {
            DateType.FROM -> view.datePickerFrom.apply {
                result.set(year, month, dayOfMonth, 0, 0, 0)

            }
            DateType.TO -> view.datePickerTo.apply {
                result.set(year, month, dayOfMonth, 23, 59, 59)
            }
        }
        return result
    }

    private fun setupPicker(savedInstanceState: Bundle?) {
        val currentStartDate = when (savedInstanceState != null) {
            true -> savedInstanceState.getSerializable(ARG_CURRENT_START_DATE) as? Calendar
            else -> requireArguments().getSerializable(ARG_CURRENT_START_DATE) as? Calendar
        }
        (currentStartDate ?: Calendar.getInstance()).run {
            view.datePickerFrom.updateDate(
                get(Calendar.YEAR),
                get(Calendar.MONTH),
                get(Calendar.DAY_OF_MONTH)
            )
        }

        val currentEndDate = when (savedInstanceState != null) {
            true -> savedInstanceState.getSerializable(ARG_CURRENT_END_DATE) as? Calendar
            else -> requireArguments().getSerializable(ARG_CURRENT_END_DATE) as? Calendar
        }
        (currentEndDate ?: Calendar.getInstance()).run {
            view.datePickerTo.updateDate(
                get(Calendar.YEAR),
                get(Calendar.MONTH),
                get(Calendar.DAY_OF_MONTH)
            )
        }

        val endLimitDate = requireArguments().getSerializable(ARG_END_LIMIT_DATE) as? Calendar
        endLimitDate?.let {
            view.datePickerTo.maxDate = endLimitDate.timeInMillis
            view.datePickerFrom.maxDate = endLimitDate.timeInMillis
        }

        val startLimitDate = requireArguments().getSerializable(ARG_START_LIMIT_DATE) as? Calendar
        startLimitDate?.let {
            view.datePickerTo.minDate = startLimitDate.timeInMillis
            view.datePickerFrom.minDate = startLimitDate.timeInMillis
        }
    }

    companion object {
        const val RANGE_PICKER_DIALOG_RESULT = "rangePickerDialogResult"

        const val ARG_SELECTED_START_DATE = "selectedStartDate"
        const val ARG_SELECTED_END_DATE = "selectedEndDate"

        private const val ARG_CURRENT_START_DATE = "currentStartDate"
        private const val ARG_CURRENT_END_DATE = "currentEndDate"
        private const val ARG_END_LIMIT_DATE = "endLimitDate"
        private const val ARG_START_LIMIT_DATE = "startLimitDate"
        private const val ARG_START_TITLE = "startTITLE"
        private const val ARG_END_TITLE = "endTitle"
        private const val ARG_BUTTON_TEXT = "buttonText"

        fun create(
            buttonText: String,
            startTitle: String,
            endTitle: String,
            currentStartDate: Calendar = Calendar.getInstance(),
            currentEndDate: Calendar = Calendar.getInstance(),
            startLimitDate: Calendar? = null,
            endLimitDate: Calendar? = null,
        ): RangeDatePickerDialog {
            return RangeDatePickerDialog().apply {
                arguments = bundleOf(
                    ARG_CURRENT_START_DATE to currentStartDate,
                    ARG_CURRENT_END_DATE to currentEndDate,
                    ARG_END_LIMIT_DATE to endLimitDate,
                    ARG_START_LIMIT_DATE to startLimitDate,
                    ARG_START_TITLE to startTitle,
                    ARG_END_TITLE to endTitle,
                    ARG_BUTTON_TEXT to buttonText
                )
            }
        }
    }
}

private enum class DateType {
    FROM, TO
}

data class RangeDatePickerDialogVariables(
    val tvTitleFrom: TextView,
    val datePickerFrom: DatePicker,
    val tvTitleTo: TextView,
    val datePickerTo: DatePicker,
    val btnDone: Button,
)