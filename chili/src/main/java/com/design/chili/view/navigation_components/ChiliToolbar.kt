package com.design.chili.view.navigation_components

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.annotation.StyleRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.design.chili.R
import com.design.chili.extensions.setOnSingleClickListener
import com.design.chili.extensions.setTextOrHide
import com.google.android.material.appbar.MaterialToolbar

class ChiliToolbar : LinearLayout {

    private lateinit var view: ChiliToolbarViewVariables

    constructor(context: Context) : super(context) {
        setupView()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        setupView()
        obtainAttributes(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        setupView()
        obtainAttributes(attrs, defStyle)
    }

    private fun setupView() {
        val view = LayoutInflater.from(context).inflate(R.layout.chili_view_chili_toolbar, this)
        this.view = ChiliToolbarViewVariables(
            toolbar = view.findViewById(R.id.toolbar_view),
            tvAdditionalText = view.findViewById(R.id.tv_additional_text),
            ivEndIcon = view.findViewById(R.id.iv_icon),
            rootView = view.findViewById(R.id.ll_root),
            divider = view.findViewById(R.id.divider)
        )
    }

    private fun obtainAttributes(attrs: AttributeSet, defStyle: Int = R.style.Chili_BaseNavigationComponentsStyle_ChiliToolbar) {
        context?.obtainStyledAttributes(attrs, R.styleable.ChiliToolbar, R.attr.toolbarDefaultStyle, defStyle)?.run {
            setTitle(getString(R.styleable.ChiliToolbar_title))
            setAdditionalText(getString(R.styleable.ChiliToolbar_additionalText))
            getBoolean(R.styleable.ChiliToolbar_isDividerVisible, true).let {
                setupDividerVisibility(it)
            }
            getResourceId(R.styleable.ChiliToolbar_toolbarEndIcon, -1).takeIf { it != -1 }?.let {
                setEndIcon(it)
            }
            getDimensionPixelSize(R.styleable.ChiliToolbar_toolbarEndIconSize, -1).takeIf { it != -1 }?.let {
                setEndIconSize(it, it)
            }
            getResourceId(R.styleable.ChiliToolbar_toolbarTextAppearance, -1).takeIf { it != -1 }?.let {
                setTitleTextAppearance(it)
            }
            getResourceId(R.styleable.ChiliToolbar_additionalTextTextAppearance, -1).takeIf { it != -1 }?.let {
                setAdditionalTextTextAppearance(it)
            }
            getColor(R.styleable.ChiliToolbar_background, -1).takeIf { it != -1 }?.let {
                setToolbarBackgroundColor(it)
            }
            getResourceId(R.styleable.ChiliToolbar_navigationIcon, -1).takeIf { it != -1 }?.let {
                setNavigationIcon(it)
            }
            getBoolean(R.styleable.ChiliToolbar_titleCentered, false).let {
                setIsTitleCentered(it)
            }
            recycle()
        }
    }

    fun initToolbar(config: Configuration) {
        configureToolbar(config)
        (config.hostActivity as? AppCompatActivity)?.run {
            setSupportActionBar(view.toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(config.isNavigateUpButtonEnabled)
            supportActionBar?.setHomeButtonEnabled(config.isNavigateUpButtonEnabled)
        }
        view.toolbar.setNavigationOnClickListener { config.onNavigateUpClick.invoke(config.hostActivity) }
    }

    private fun configureToolbar(config: Configuration) {
        view.toolbar.apply {
            config.navigationIconRes?.let { this@ChiliToolbar.setNavigationIcon(it) }
            config.title?.let { this@ChiliToolbar.setTitle(it) }
            config.centeredTitle?.let { setIsTitleCentered(it) }
        }
    }

    private fun setNavigationIcon(@DrawableRes drawableRes: Int) {
        view.toolbar.setNavigationIcon(drawableRes)
    }

    fun setTitle(title: String?) {
        view.toolbar.title = title
    }

    fun setTitleTextAppearance(@StyleRes textAppearanceRes: Int) {
        view.toolbar.setTitleTextAppearance(context, textAppearanceRes)
    }

    fun setIsTitleCentered(centered: Boolean) {
        view.toolbar.isTitleCentered = centered
    }

    fun setAdditionalText(@StringRes resId: Int?) {
        view.tvAdditionalText.setTextOrHide(resId)
    }

    fun setAdditionalText(text: String?) {
        view.tvAdditionalText.setTextOrHide(text)
    }

    fun setAdditionalTextTextAppearance(@StyleRes resId: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            view.tvAdditionalText.setTextAppearance(resId)
        } else {
            view.tvAdditionalText.setTextAppearance(context, resId)
        }
    }

    fun setEndIcon(@DrawableRes drawableId: Int) {
        setIconVisibility(true)
        view.ivEndIcon.setImageResource(drawableId)
    }

    fun setEndIcon(drawable: Drawable?) {
        setIconVisibility(true)
        view.ivEndIcon.setImageDrawable(drawable)
    }

    fun setIconVisibility(isVisible: Boolean) {
        view.ivEndIcon.visibility = when(isVisible) {
            true -> View.VISIBLE
            else -> View.GONE
        }
    }

    fun setEndIconClickListener(action: () -> Unit) {
        view.ivEndIcon.setOnSingleClickListener { action.invoke() }
    }

    fun setEndIconSize(widthPx: Int, heightPx: Int) {
        val layoutParams = view.ivEndIcon.layoutParams?.apply {
            width = widthPx
            height = heightPx
        } ?: LayoutParams(widthPx, heightPx)
        view.ivEndIcon.layoutParams = layoutParams
    }

    fun setToolbarBackgroundColor(@ColorInt colorInt: Int) {
        view.rootView.setBackgroundColor(colorInt)
    }

    fun setupDividerVisibility(isVisible: Boolean) {
        view.divider.visibility = when (isVisible) {
            true -> View.VISIBLE
            else -> View.GONE
        }
    }

    fun isUpHomeEnabled(hostActivity: AppCompatActivity, isEnabled: Boolean) {
        hostActivity.apply {
            supportActionBar?.setDisplayHomeAsUpEnabled(isEnabled)
            supportActionBar?.setHomeButtonEnabled(isEnabled)
        }
    }

    data class Configuration(
        val hostActivity: FragmentActivity,
        val title: String? = null,
        val centeredTitle: Boolean? = null,
        val navigationIconRes: Int? = null,
        val onNavigateUpClick: FragmentActivity.() -> Unit = { onBackPressed() },
        val isNavigateUpButtonEnabled: Boolean = false,
    )
}



data class ChiliToolbarViewVariables(
    val rootView: LinearLayout,
    val toolbar: MaterialToolbar,
    val tvAdditionalText: TextView,
    val ivEndIcon: ImageView,
    val divider: View)