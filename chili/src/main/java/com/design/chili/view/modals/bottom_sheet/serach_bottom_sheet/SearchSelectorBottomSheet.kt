package com.design.chili.view.modals.bottom_sheet.serach_bottom_sheet

import android.content.Context
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.ImageView
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.design.chili.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.io.Serializable

class SearchSelectorBottomSheet(mContext: Context,
    private val list: List<Option>, private val isSingleSelectionType: Boolean) : BottomSheetDialog(mContext, R.style.Chili_BottomSheetStyle), SearchSelectorItemListener {

    private var filterText: String = ""

    private val searchSelectorAdapter: SearchSelectorAdapter by lazy {
        SearchSelectorAdapter(this)
    }

    init {
        setupView()
    }

    private fun setupView() {
        val bottomSheetView: View = layoutInflater.inflate(R.layout.chili_fargment_search_selector, null)
        setContentView(bottomSheetView)
        searchSelectorAdapter.addItems(list)
        findViewById<RecyclerView>(R.id.recycler_view)?.run {
            layoutManager = LinearLayoutManager(context)
            adapter = searchSelectorAdapter
        }
        findViewById<ImageView>(R.id.iv_close)?.setOnClickListener {
            this.dismiss()
        }
        findViewById<EditText>(R.id.et_search)?.run {
            doAfterTextChanged {
                expandBottomSheet()
                filterText = it?.toString() ?: ""
                searchSelectorAdapter.addItems(list, filterText)
            }
            setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) expandBottomSheet()
            }
        }
    }

    private fun expandBottomSheet() {
        if (isExpandBottomSheet()) return
        val bottomSheetInternal = findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)!!
        BottomSheetBehavior.from(bottomSheetInternal).state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun isExpandBottomSheet(): Boolean {
        val bottomSheetInternal = findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)!!
        return BottomSheetBehavior.from(bottomSheetInternal).state == BottomSheetBehavior.STATE_EXPANDED
    }

    override fun show() {
        window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        super.show()
    }

    override fun onItemSelection(selectedItem: Option?) {
        clearPreviousState()
        selectedItem?.let { selected ->
            list.find { it.id == selected.id }?.isSelected = !selectedItem.isSelected
        }
        dismiss()
    }

    private fun clearPreviousState() {
        if (!isSingleSelectionType) return
        list.forEach {
            it.isSelected = false
        }
    }
}

data class Option(val id: String, val value: String, var isSelected: Boolean): Serializable
