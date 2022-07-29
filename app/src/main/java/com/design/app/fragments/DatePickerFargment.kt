package com.design.app.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.FragmentResultListener
import com.design.app.base.BaseFragment
import com.design.app.databinding.FragmentDatePickerBinding
import com.design.chili.view.modals.picker.DatePickerDialog
import com.design.chili.view.modals.picker.RangeDatePickerDialog
import com.design.chili.view.modals.picker.TimePickerDialog
import java.text.SimpleDateFormat
import java.util.*

class DatePickerFargment : BaseFragment<FragmentDatePickerBinding>(), FragmentResultListener {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        childFragmentManager.setFragmentResultListener(DatePickerDialog.PICKER_DIALOG_RESULT, this, this)
        childFragmentManager.setFragmentResultListener(RangeDatePickerDialog.RANGE_PICKER_DIALOG_RESULT, this, this)
        childFragmentManager.setFragmentResultListener(TimePickerDialog.TIME_PICKER_DIALOG_RESULT, this, this)

        vb.date.setOnClickListener { DatePickerDialog.create("Готово", "Дата").show(childFragmentManager, "") }
        vb.range.setOnClickListener { RangeDatePickerDialog.create("Готово", "Дата", "Date").show(childFragmentManager, "") }

        vb.dateStart.setOnClickListener { DatePickerDialog.create(
            "Готово", "Дата",
            startLimitDate = Calendar.getInstance()
        ).show(childFragmentManager, "") }
        vb.dateEnd.setOnClickListener { DatePickerDialog.create(
            "Готово", "Дата",
            endLimitDate = Calendar.getInstance()
        ).show(childFragmentManager, "") }
        vb.dateStartEnd.setOnClickListener { DatePickerDialog.create(
            "Готово", "Дата",
            startLimitDate = Calendar.getInstance(),
            endLimitDate = Calendar.getInstance().apply { add(Calendar.DAY_OF_MONTH, 5) }
        ).show(childFragmentManager, "") }


        vb.rangeStart.setOnClickListener { RangeDatePickerDialog.create(
            "Готово", "Дата", "Date",
            startLimitDate = Calendar.getInstance()
        ).show(childFragmentManager, "") }
        vb.rangeEnd.setOnClickListener { RangeDatePickerDialog.create(
            "Готово", "Дата", "Date",
            endLimitDate = Calendar.getInstance()
        ).show(childFragmentManager, "") }
        vb.rangeStartEnd.setOnClickListener { RangeDatePickerDialog.create(
            "Готово", "Дата", "Date",
            startLimitDate = Calendar.getInstance(),
            endLimitDate = Calendar.getInstance().apply { add(Calendar.DAY_OF_MONTH, 5) }
        ).show(childFragmentManager, "") }

        vb.timePicker.setOnClickListener {
            TimePickerDialog.create("Готово", "Установить время")
                .show(childFragmentManager, "")
        }
    }

    override fun inflateViewBinging(): FragmentDatePickerBinding {
        return FragmentDatePickerBinding.inflate(layoutInflater)
    }

    override fun onFragmentResult(requestKey: String, result: Bundle) {
        val formatter = SimpleDateFormat("yyyy MMM d")
        when (requestKey) {
            DatePickerDialog.PICKER_DIALOG_RESULT -> {
                val calendar = result.getSerializable(DatePickerDialog.ARG_SELECTED_DATE) as Calendar
                Toast.makeText(requireContext(), formatter.format(calendar.time), Toast.LENGTH_LONG).show()
            }
            RangeDatePickerDialog.RANGE_PICKER_DIALOG_RESULT -> {
                val calendarStart = result.getSerializable(RangeDatePickerDialog.ARG_SELECTED_START_DATE) as Calendar
                val calendarEnd = result.getSerializable(RangeDatePickerDialog.ARG_SELECTED_END_DATE) as Calendar
                Toast.makeText(requireContext(), "${formatter.format(calendarStart.time)} - ${formatter.format(calendarEnd.time)}", Toast.LENGTH_LONG).show()
            }

            TimePickerDialog.TIME_PICKER_DIALOG_RESULT -> {
                val calendar = result.getSerializable(TimePickerDialog.TIME_ARG_SELECTED_TIME) as Calendar
                val formatter = SimpleDateFormat("HH:mm")
                val time = formatter.format(calendar.time)
                Toast.makeText(requireContext(), time, Toast.LENGTH_SHORT).show()
            }
        }
    }
}