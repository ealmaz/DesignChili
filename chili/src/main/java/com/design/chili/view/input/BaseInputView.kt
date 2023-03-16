package com.design.chili.view.input

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.text.*
import android.text.method.DigitsKeyListener
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.annotation.StyleRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.updatePadding
import com.design.chili.R
import com.design.chili.extensions.*
import com.design.chili.extensions.gone
import com.design.chili.extensions.visible
import com.design.chili.util.cyrillicRegex
import com.design.chili.view.input.text_watchers.ClearTextIconTextWatcher
import com.design.chili.view.input.text_watchers.SimpleTextWatcher
import com.google.android.material.textfield.TextInputLayout

open class BaseInputView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.inputViewDefaultStyle,
    defStyle: Int = R.style.Chili_InputViewStyle_Simple
): ConstraintLayout(context, attrs, defStyleAttr, defStyle) {

    lateinit var view: BaseInputViewVariables

    protected val textWatchers by lazy { mutableListOf<TextWatcher>() }

    protected var messageText: String? = null

    @ColorInt protected var hintTextColor: Int = -1
    @ColorInt protected var fieldBackground: Int = -1
    @ColorInt protected var fieldErrorBackground: Int = -1

    @StyleRes protected var inputViewTextAppearanceRes: Int = -1

    @ColorInt protected var messageTextColor: Int = -1
    @ColorInt protected var errorMessageTextColor: Int = -1

    init {
        inflateViews(context)
        obtainAttributes(attrs, defStyleAttr, defStyle)
    }

    private fun inflateViews(context: Context) {
        val view = LayoutInflater.from(context).inflate(R.layout.chili_view_base_input, this)
        this.view = BaseInputViewVariables(
            inputField = view.findViewById(R.id.et_input),
            textInputLayout = view.findViewById(R.id.til_input_container),
            tvMessage = view.findViewById(R.id.tv_message),
            tvAction = view.findViewById(R.id.tv_action),
            flActionBg = view.findViewById(R.id.fl_action_bg),
            ivEndIcon = view.findViewById(R.id.iv_end_icon))
    }

    private fun obtainAttributes(attrs: AttributeSet?, defStyleAttr: Int, defStyle: Int) {
        context?.obtainStyledAttributes(attrs, R.styleable.BaseInputView, defStyleAttr, defStyle)?.run {

            getColor(R.styleable.BaseInputView_errorFieldBackground, ContextCompat.getColor(context, R.color.red_3)).let {
                setupErrorFieldBackground(it)
            }
            getColor(R.styleable.BaseInputView_hintTextColor, ContextCompat.getColor(context, R.color.gray_5)).let {
                setupHintTextColor(it)
            }
            getColor(R.styleable.BaseInputView_errorMessageTextColor, ContextCompat.getColor(context, R.color.red_1)).let {
                errorMessageTextColor = it
            }
            getColor(R.styleable.BaseInputView_messageTextColor, ContextCompat.getColor(context, R.color.black_5)).let {
                messageTextColor = it
            }
            getColor(R.styleable.BaseInputView_fieldBackground, ContextCompat.getColor(context, R.color.gray_5)).let {
                setupFieldBackground(it)
            }
            getColor(R.styleable.BaseInputView_endIconTint, ContextCompat.getColor(context, R.color.gray_1)).let {
                view.ivEndIcon.setColorFilter(it)
            }
            getDrawable(R.styleable.BaseInputView_endIconDrawable)?.let {
                setInputRightDrawable(it)
            }
            getString(R.styleable.BaseInputView_android_hint)?.let { hint ->
                setHint(hint)
            }

            getBoolean(R.styleable.BaseInputView_android_enabled, true).let {enabled ->
                setIsInputEnabled(enabled)
            }

            getBoolean(R.styleable.BaseInputView_android_textAllCaps, false).let {isCaps ->
                setIsTextAllCaps(isCaps)
            }

            getString(R.styleable.BaseInputView_message)?.let { message ->
                setMessage(message)
            }

            getString(R.styleable.BaseInputView_errorMessage)?.let { error ->
                setupFieldAsError(error)
            }

            getInteger(R.styleable.BaseInputView_android_gravity, Gravity.CENTER).let { gravity ->
                setGravity(gravity)
            }
            getString(R.styleable.BaseInputView_actionButtonText)?.let {
                setAction(it)
            }
            getInteger(R.styleable.BaseInputView_android_inputType, InputType.TYPE_CLASS_TEXT).let {
                setInputType(it)
            }
            getInteger(R.styleable.BaseInputView_endIconMode, TextInputLayout.END_ICON_CUSTOM).let {
                setupEndDrawableMode(it)
            }
            getResourceId(R.styleable.BaseInputView_android_textAppearance, -1).takeIf { it != -1 }?.let {
                setupInputTextAppearance(it)
            }
            getColorStateList(R.styleable.BaseInputView_android_editTextColor)?.let {
                view.inputField.setTextColor(it)
            }
            getInteger(R.styleable.BaseInputView_android_maxLength, -1)
                .takeIf { it != -1 }?.let {
                    setMaxLength(it)
                }
            recycle()
        }
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        var superState: Parcelable? = null
        (state as? Bundle)?.let { bundle ->
            setText(bundle.getString(EditText::class.java.canonicalName))
            superState = bundle.getParcelable(SUPER_STATE)
        }
        super.onRestoreInstanceState(superState)
    }

    override fun onSaveInstanceState(): Parcelable? {
        val supperState = super.onSaveInstanceState()
        return Bundle().apply {
            putString(EditText::class.java.canonicalName, getInputText())
            putParcelable(SUPER_STATE, supperState)
        }
    }

    protected open fun setupHintTextColor(@ColorInt color: Int) {
        hintTextColor = color
        view.inputField.setHintTextColor(color)
    }

    private fun setupErrorFieldBackground(color: Int) {
        fieldErrorBackground = color
        view.textInputLayout.setBackgroundColor(color)
    }

    private fun setupFieldBackground(color: Int) {
        fieldBackground = color
        view.textInputLayout.setBackgroundColor(color)
    }

    fun getInputField(): SelectionEditText {
        return view.inputField
    }

    fun setText(text: String?) {
        view.inputField.setText(text)
    }

    fun appendString(text: String) {
        view.inputField.append(text)
    }

    fun setSelectionStartLimit(limit: Int) {
        view.inputField.startSelectionLimit = limit
    }

    fun setSelection(selection: Int) {
        view.inputField.setSelection(selection)
    }

    fun setSelectionToEnd() {
        view.inputField.text?.length?.let {
            setSelection(it)
        }
    }

    open fun getInputText() = view.inputField.text?.toString() ?: ""

    open fun isInputEmpty() = getInputText().isEmpty()

    open fun clearInput() = setText("")

    fun setHint(hint: String?) {
        view.inputField.hint = hint
    }

    fun setInputType(type: Int) {
        view.inputField.inputType = type
    }

    fun setIsTextAllCaps(isCaps: Boolean) {
        view.inputField.isAllCaps =isCaps
    }

    fun showSystemKeyboard() {
        requestInputFocus()
        showKeyboard()
    }

    fun requestInputFocus() {
        view.inputField.requestFocus()
    }

    private fun showKeyboard() {
        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(view.inputField, InputMethodManager.SHOW_IMPLICIT)
    }

    fun clearAndSetFilters(filter: Array<InputFilter>) {
        view.inputField.filters = filter
    }

    fun addFilter(inputFilter: InputFilter) {
        view.inputField.filters += inputFilter
    }

    fun addRegexFilter(regex: Regex) {
        addFilter { source, start, end, _, _, _ ->
            var keepOriginal = true
            val sb: StringBuilder = StringBuilder(end - start)
            for (i in start until end) {
                if (regex.matches(source[i].toString()))
                    sb.append(source[i].toString())
                else
                    keepOriginal = false
            }
            if (keepOriginal) null else {
                if (source is Spanned) {
                    val sp = SpannableString(sb)
                    TextUtils.copySpansFrom(source, start, sb.length, null, sp, 0)
                    sp
                } else {
                    sb
                }
            }
        }
    }

    fun setMaxLength(charCount: Int) {
        addFilter(InputFilter.LengthFilter(charCount))
    }

    fun setOnLongClick(action: () -> Unit) {
        view.inputField.setOnLongClickListener { action(); true }
    }

    fun setOnDoubleClick(action: () -> Unit) {
        view.inputField.setOnDoubleClickListener { action() }
    }

    fun disableSuggestions() {
        view.inputField.apply { inputType = inputType or InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS }
    }

    fun disableEdition() {
        view.inputField.isEnabled = false
    }

    fun isInputEnabled() = view.inputField.isEnabled

    fun setIsInputEnabled(isEnabled: Boolean) {
        view.inputField.isEnabled = isEnabled
    }

    fun disableSystemKeyboard() {
        view.inputField.showSoftInputOnFocus = false
    }

    fun setInputLetterSpacing(spacing: Float) {
        view.inputField.letterSpacing = spacing
    }

    fun changeInputPositionToStart() {
        setGravity(Gravity.LEFT)
    }

    fun changeInputPositionToCenter() {
        setGravity(Gravity.CENTER)
    }

    fun setGravity(gravity: Int) {
        view.inputField.gravity = gravity
    }

    fun setEms(n: Int) {
        view.inputField.setEms(n)
    }

    fun setFocusChangeListener(onFocus: () -> Unit = {}, onFocusLost: () -> Unit = {}) {
        view.inputField.setOnFocusChangeListener { _, hasFocus ->
            when {
                hasFocus -> onFocus()
                else -> onFocusLost()
            }
        }
    }

    fun setupForCyrillicInput() {
        addRegexFilter(cyrillicRegex)
    }

    fun setDigitKeyListener(symbols: String) {
        view.inputField.keyListener = DigitsKeyListener.getInstance(symbols)
    }

    fun addTextWatcher(
        beforeTextChanged: ((s: CharSequence?, start: Int, count: Int, after: Int) -> Unit)? = null,
        onTextChanged: ((s: CharSequence?, start: Int, before: Int, count: Int) -> Unit)? = null,
        afterTextChanged: ((s: Editable?) -> Unit)? = null,
        onTextChangedListener: ((String?) -> Unit)? = null
    ) {
        val textWatcher = SimpleTextWatcher(beforeTextChanged, onTextChanged, afterTextChanged, onTextChangedListener)
        view.inputField.addTextChangedListener(textWatcher)
        textWatchers.add(textWatcher)
    }

    fun addTextWatcher(textWatcher: TextWatcher) {
        view.inputField.addTextChangedListener(textWatcher)
        textWatchers.add(textWatcher)
    }

    fun removeAllTextWatcher() {
        textWatchers.forEach {
            view.inputField.removeTextChangedListener(it)
        }
        textWatchers.clear()
    }

    fun getAllTextWatchers(): List<TextWatcher> {
        return textWatchers
    }

    fun setSimpleTextChangedListener(onTextChanged: (String?) -> Unit) {
        val textWatcher = SimpleTextWatcher(onTextChangedListener = onTextChanged)
        view.inputField.addTextChangedListener(textWatcher)
    }

    fun doAfterTextChanged(action: (Editable?) -> Unit) {
        val textWatcher = SimpleTextWatcher(afterTextChanged = action)
        view.inputField.addTextChangedListener(textWatcher)
    }

    fun setTextChangeListener(onTextChanged: ((CharSequence?, Int, Int, Int) -> Unit)? = null) {
        val textWatcher = SimpleTextWatcher(
            onTextChanged = onTextChanged,
            beforeTextChanged = { _, _, _, _ -> clearFieldError() })
        view.inputField.addTextChangedListener(textWatcher)
    }


    fun setInputTailedIcon(@DrawableRes drawableId: Int) {
        view.inputField.run {
            setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, drawableId, 0)
            val frameLayoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
            )
            if (view.inputField.gravity in listOf(Gravity.CENTER, Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL)) {
                frameLayoutParams.gravity = Gravity.CENTER_HORIZONTAL or Gravity.CENTER_VERTICAL
            }
            this.layoutParams = frameLayoutParams
            minEms = 1
        }
    }

    fun setInputTailedIcon(drawable: Drawable) {
        view.inputField.run {
            setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, drawable, null)
            val frameLayoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
            )
            if (view.inputField.gravity in listOf(Gravity.CENTER, Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL)) {
                frameLayoutParams.gravity = Gravity.CENTER_HORIZONTAL or Gravity.CENTER_VERTICAL
            }
            this.layoutParams = frameLayoutParams
            minEms = 1
        }
    }

    fun matchInputWidthToParentWidth() {
        view.inputField.layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT,
            FrameLayout.LayoutParams.WRAP_CONTENT
        )
    }

    fun setInputRightDrawable(drawable: Drawable) {
        getInputRightImageView().apply {
            visible()
            setImageDrawable(drawable)
        }
        view.inputField.updatePadding(right = resources.getDimensionPixelSize(R.dimen.padding_32dp))
    }

    fun setInputRightDrawable(@DrawableRes drawableId: Int) {
        getInputRightImageView().apply {
            visible()
            setImageResource(drawableId)
        }
        view.inputField.updatePadding(right = resources.getDimensionPixelSize(R.dimen.padding_32dp))
    }

    fun isInputRightDrawableExist(): Boolean {
        return getInputRightImageView().drawable != null
    }

    fun removeInputRightDrawable() {
        getInputRightImageView().gone()
        view.inputField.updatePadding(right = resources.getDimensionPixelSize(R.dimen.padding_0dp))
    }

    fun getInputRightImageView(): ImageView {
        return view.ivEndIcon
    }

    fun setupInputTextAppearance(@StyleRes inputViewTextAppearanceRes: Int) {
        this.inputViewTextAppearanceRes = inputViewTextAppearanceRes
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            view.inputField.setTextAppearance(inputViewTextAppearanceRes)
        } else {
            view.inputField.setTextAppearance(context, inputViewTextAppearanceRes)
        }
    }

    private fun setupEndDrawableMode(mode: Int) {
       when (mode) {
           TextInputLayout.END_ICON_CLEAR_TEXT -> setupClearTextButton()
           TextInputLayout.END_ICON_PASSWORD_TOGGLE -> setupAsPasswordField()
           TextInputLayout.END_ICON_NONE -> removeInputRightDrawable()
       }
    }

    fun setupAsPasswordField() {
        removeInputRightDrawable()
        view.textInputLayout.apply {
            setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)
            endIconMode = TextInputLayout.END_ICON_PASSWORD_TOGGLE
            setEndIconDrawable(R.drawable.chili_password_toggle_drawable)
            setupInputTextAppearance(inputViewTextAppearanceRes)
        }
    }

    fun setAction(title: String, action: () -> Unit = {}) {
        view.flActionBg.visible()
        view.tvAction.apply {
            text = title
            setOnSingleClickListener(action)
            visible()
        }
    }

    fun setSingleLine(singleLine: Boolean){
        view.inputField.isSingleLine = singleLine
    }

    fun setOnActionClickListener(action: () -> Unit = {}) {
        view.tvAction.setOnClickListener { action.invoke() }
    }

    fun hideAction() {
        view.apply {
            tvAction.gone()
            flActionBg.gone()
        }
    }

    fun setMessage(@StringRes resId: Int) {
        setMessage(resources.getString(resId))
    }

    fun setMessage(text: String?) {
        messageText = text
        when (text.isNullOrBlank()) {
            true -> view.tvMessage.gone()
            else -> {
                view.tvMessage.apply {
                    setText(messageText)
                    setTextColor(messageTextColor)
                    visible()
                }
            }
        }
    }

    fun setupFieldAsError(@StringRes errorTextResId: Int) {
        setupFieldAsError(resources.getString(errorTextResId))
    }

    fun setupFieldAsError(errorText: String) {
        view.tvMessage.apply {
            text = errorText
            setTextColor(errorMessageTextColor)
            visible()
        }
        view.textInputLayout.setBackgroundColor(fieldErrorBackground)
    }

    fun clearFieldError() {
        view.textInputLayout.setBackgroundColor(fieldBackground)
        setMessage(messageText)
    }

    fun hideMessage() {
        view.tvMessage.gone()
    }

    fun setupClearTextButton(
        clearText: (() -> Unit)? = null,
        isInputEmpty: (() -> Boolean)? = null,
        isInputEnabled: (() -> Boolean)? = null,
    ) {
        view.textInputLayout.apply {
            if (endIconMode == TextInputLayout.END_ICON_PASSWORD_TOGGLE) {
                endIconMode = TextInputLayout.END_ICON_NONE
            }
        }
        if (!isInputRightDrawableExist()) setInputRightDrawable(R.drawable.chili_ic_clear)
        view.inputField.addTextChangedListener(
            ClearTextIconTextWatcher(
                getInputRightImageView(),
                clearText ?: ::clearInput,
                isInputEmpty ?: ::isInputEmpty,
                isInputEnabled ?: ::isInputEnabled)
        )
    }

    fun setPasteTextListener(onPasteListener: PasteListener) {
        view.inputField.setPasteListener(onPasteListener)
    }

    fun setActionWithColor(title: String, @ColorInt textColor: Int, action: () -> Unit = {}) {
        setAction(title, action)
        view.tvAction.setTextColor(textColor)
    }


    companion object {
        const val SUPER_STATE = "superState"
    }
}

data class BaseInputViewVariables(
    var inputField: SelectionEditText,
    var textInputLayout: TextInputLayout,
    var tvMessage: TextView,
    var tvAction: TextView,
    var flActionBg: FrameLayout,
    var ivEndIcon: ImageView
)