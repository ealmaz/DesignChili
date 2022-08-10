package com.design.chili.view.navigation_components

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.design.chili.R
import com.design.chili.extensions.drawable
import com.design.chili.extensions.setOnSingleClickListener
import com.design.chili.extensions.setTextOrHide
import com.design.chili.extensions.visible

open class ChiliToolbar : LinearLayout {

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
            toolbar = view.findViewById(R.id.toolbar),
            tvAdditionalText = view.findViewById(R.id.tv_additional_text),
            ivEndIcon = view.findViewById(R.id.iv_icon),
            rootView = view.findViewById(R.id.ll_root),
            divider = view.findViewById(R.id.divider)
        )
    }

    private fun obtainAttributes(attrs: AttributeSet, defStyle: Int = R.style.Chili_ToolbarStyle) {
        context?.obtainStyledAttributes(attrs, R.styleable.ChiliToolbar, R.attr.toolbarDefaultStyle, defStyle)?.run {
            getText(R.styleable.ChiliToolbar_title)?.let {
                updateTitle(it.toString())
            }
            getText(R.styleable.ChiliToolbar_additionalText)?.let {
                setAdditionalText(it.toString())
            }
            getBoolean(R.styleable.ChiliToolbar_isDividerVisible, true).let {
                setupDividerVisibility(it)
            }
            getResourceId(R.styleable.ChiliToolbar_endIcon, -1).takeIf { it != -1 }?.let {
                setEndIcon(it)
            }
            getBoolean(R.styleable.ChiliToolbar_isTransparent, false).let {
                if (it) setupToolbarTransparentBackground()
            }
            recycle()
        }
    }

    fun setupToolbarTransparentBackground() {
        view.apply {
            rootView.setBackgroundColor(Color.TRANSPARENT)
            toolbar.setTitleTextColor(Color.TRANSPARENT)
        }
    }

    open fun initForActivity(activity: AppCompatActivity?, title: String? = null, onBackClick: () -> Unit = { activity?.finish() }) {
        activity?.run {
            setupSupportActionBar(onBackClick)
            title?.let { supportActionBar?.title = it }
        }
    }
    
    fun getToolbar(): Toolbar = view.toolbar

    protected fun AppCompatActivity.setupSupportActionBar(onBackClick: () -> Unit) {
        setSupportActionBar(view.toolbar)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setOnBackPressListener(onBackClick)
    }

    fun changeNavigatorIcon(@DrawableRes drawableId: Int) {
        view.toolbar.navigationIcon = context.drawable(drawableId)
    }

    fun setOnBackPressListener(onBackClick: () -> Unit) {
        view.toolbar.setNavigationOnClickListener { onBackClick() }
    }

    fun updateTitle(title: String) {
        view.toolbar.title = title
    }

    fun setAdditionalText(@StringRes resId: Int?) {
        view.tvAdditionalText.setTextOrHide(resId)
    }

    fun setAdditionalText(text: String?) {
        view.tvAdditionalText.setTextOrHide(text)
    }

    fun getIconView(): View {
        return view.ivEndIcon
    }

    fun findMenuItemById(itemId: Int): MenuItem {
        return view.toolbar.menu.findItem(itemId)
    }

    fun setIconVisibility(isVisible: Boolean) {
        view.ivEndIcon.visibility = when(isVisible) {
            true -> View.VISIBLE
            else -> View.GONE
        }
    }

    fun setEndIcon(@DrawableRes drawableId: Int) {
        view.ivEndIcon.apply {
            visible()
            setImageResource(drawableId)
        }
    }

    fun setEndIcon(drawable: Drawable?) {
        view.ivEndIcon.apply {
            visible()
            setImageDrawable(drawable)
        }
    }

    fun setIconClickListener(action: () -> Unit) {
        view.ivEndIcon.setOnSingleClickListener { action() }
    }

    fun setupDividerVisibility(isVisible: Boolean) {
        view.divider.visibility = when (isVisible) {
            true -> View.VISIBLE
            else -> View.GONE
        }
    }

}

data class ChiliToolbarViewVariables(
    val rootView: LinearLayout,
    val toolbar: Toolbar,
    val tvAdditionalText: TextView,
    val ivEndIcon: ImageView,
    val divider: View)