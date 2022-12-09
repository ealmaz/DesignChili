package com.design.chili.view.modals.drop_down

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import androidx.fragment.app.Fragment
import com.design.chili.R
import com.design.chili.extensions.visible
import com.design.chili.view.input.BaseInputView

open class DropDownInputField @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.inputViewDefaultStyle,
    defStyle: Int = R.style.Chili_InputViewStyle_Simple
) : BaseInputView(context, attrs, defStyleAttr, defStyle) {

    private var chooseType = ChooseType.SINGLE
//    private var options = listOf<Option>()


    init {
        setupViews()
    }

    private fun setupViews() {
        disableEdition()
        getInputRightImageView().apply {
            visible()
            setImageResource(R.drawable.chili_ic_chevron)
        }
        setGravity(Gravity.START)
        setOnClickListener {

        }
    }

//    fun setupDropDownList(chooseType: ChooseType, options: List<Option>) {
//        this.chooseType = chooseType
//        this.options = options
//    }

    open fun showSelectionBottomSheet() {

    }
}

enum class ChooseType {
    MULTIPLE, SINGLE
}



class ItemsListFragment : Fragment() {

}
