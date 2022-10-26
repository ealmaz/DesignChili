package com.design.chili.view.buttons

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.annotation.StringRes
import com.design.chili.R
import com.design.chili.extensions.gone
import com.design.chili.extensions.invisible
import com.design.chili.extensions.visible

class LoaderButton @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = R.attr.loaderButtonDefaultStyle,
    defStyle: Int = R.style.Chili_LoaderButton
) : FrameLayout(context, attributeSet, defStyleAttr, defStyle) {

    private lateinit var view: LoaderButtonViewVariables

    init {
        initView(context)
        obtainAttributes(context, attributeSet, defStyleAttr, defStyle)
    }

    private fun initView(context: Context) {
        val view = LayoutInflater.from(context).inflate(R.layout.chili_view_loader_button, this, true)
        this.view = LoaderButtonViewVariables(
            button = view.findViewById(R.id.button),
            progress = view.findViewById(R.id.progress)
        )
    }

    private fun obtainAttributes(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int, defStyle: Int) {
        context.obtainStyledAttributes(attributeSet, R.styleable.LoaderButton, defStyleAttr, defStyle).run {
            setText(getString(R.styleable.LoaderButton_android_text))
            setEnabled(getBoolean(R.styleable.LoaderButton_android_enabled, true))
            setIsLoading(getBoolean(R.styleable.LoaderButton_isLoading, false))
            recycle()
        }
    }

    fun setText(text: String?) {
        view.button.text = text
    }

    fun setText(@StringRes textResId: Int) {
        view.button.setText(textResId)
    }

    fun setIsLoading(isLoading: Boolean) {
        when (isLoading) {
            true -> showLoader()
            else -> hideLoader()
        }
    }

    private fun showLoader() = with (view){
        button.invisible()
        progress.visible()
    }

    private fun hideLoader() = with (view){
        button.visible()
        progress.gone()
    }

    override fun setOnClickListener(l: OnClickListener?) {
        view.button.setOnClickListener(l)
    }

    override fun isEnabled(): Boolean {
        return view.button.isEnabled
    }

    override fun setEnabled(enabled: Boolean) {
        view.button.isEnabled = enabled
    }
}

data class LoaderButtonViewVariables(
    val button: Button,
    val progress: ProgressBar
)