package com.design.chili.view.story.detail

import android.animation.Animator
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.LinearGradient
import android.graphics.Shader.TileMode
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewTreeObserver
import android.view.animation.LinearInterpolator
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.core.view.GestureDetectorCompat
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.design.chili.R
import com.design.chili.view.story.DetailedStory
import com.design.chili.view.story.StoryCallbackListener
import java.util.*
import kotlin.math.abs

class StoryView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = R.style.Chili_StoryViewStyle
): ConstraintLayout(context, attrs, defStyleAttr, defStyleRes), View.OnTouchListener {

    private lateinit var storiesList: List<DetailedStory>

    lateinit var view: StoryViewVariables

    private lateinit var storyDuration: String
    private var gapBetweenProgressBars: Int = 8
    private lateinit var animation: ObjectAnimator

    private var storyIndex: Int = 1
    private var userClicked: Boolean = false
    private lateinit var storiesListener: StoryCallbackListener

    private var gestureDetector: GestureDetectorCompat

    init {
        inflateView(context)
        obtainAttributes(context, attrs, defStyleAttr, defStyleRes)
        gestureDetector = GestureDetectorCompat(context, GestureListener())
    }

    private fun obtainAttributes(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {
        context.obtainStyledAttributes(attrs, R.styleable.StoryView, defStyleAttr, defStyleRes).run {
            getDimensionPixelSize(R.styleable.StoryView_gapBetweenProgressBars, -1).takeIf { it != -1 }?.let {
                setGapBetweenProgressBars(gap = it)
            }
            getString(R.styleable.StoryView_storyDisplayTime)?.let { setDisplayTime(it) }
            getBoolean(R.styleable.StoryView_isBadgeVisible, false).apply {
                isBadgeVisible(this)
            }
            getDrawable(R.styleable.StoryView_borderDrawable)?.let { setBorderDrawable(it) }
            getDrawable(R.styleable.StoryView_overlayDrawable)?.let { setOverlayDrawable(it) }
            recycle()
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun inflateView(context: Context) {
        val view = LayoutInflater.from(context).inflate(R.layout.chili_detailed_story_layout, this, false)
        addView(view)
        this.view = StoryViewVariables(
            progressBarContainer = view.findViewById(R.id.progressBarContainer),
            overlayLayout = view.findViewById(R.id.overlayLayout),
            leftTouchPanel = view.findViewById(R.id.leftTouchPanel),
            rightTouchPanel = view.findViewById(R.id.rightTouchPanel),
            borderView = view.findViewById(R.id.border_view),
            cvBadge = view.findViewById(R.id.badge),
            ivClose = view.findViewById(R.id.btn_close),
            ivImage = view.findViewById(R.id.storyImage),
            tvTitle = view.findViewById(R.id.title),
            tvSubtitle = view.findViewById(R.id.subtitle),
            btnDetails = view.findViewById(R.id.btn_details)
        )

        this.view.leftTouchPanel.setOnTouchListener(this@StoryView)
        this.view.rightTouchPanel.setOnTouchListener(this@StoryView)
    }

    fun setOnCallbackListener(listener: StoryCallbackListener) {
        storiesListener = listener
        setOnCancelClickListener(listener)
        setOnDetailsButtonClickListener(listener)
    }

    private fun setOnCancelClickListener(listener: StoryCallbackListener) {
        view.ivClose.setOnClickListener { listener.onCancelClick() }
    }

    private fun setOnDetailsButtonClickListener(listener: StoryCallbackListener) {
        view.btnDetails.setOnClickListener { listener.onDetailButtonClick() }
    }

    private fun setGapBetweenProgressBars(gap: Int) {
        gapBetweenProgressBars = gap
    }

    private fun setDisplayTime(time: String) {
        storyDuration = time
    }

    private fun setTitle(text: String) {
        view.tvTitle.text = text
    }

    private fun setSubtitle(text: String) {
        view.tvSubtitle.text = text
        view.tvSubtitle.viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                view.tvSubtitle.viewTreeObserver.removeOnPreDrawListener(this)
                setTextGradient()
                return true
            }
        })
    }

    private fun setTextGradient() {
        val totalHeight = view.tvSubtitle.lineHeight * (view.tvSubtitle.lineCount + 1).toFloat()
        val gradient = LinearGradient(
            0f, 0f, 0f, totalHeight,
            context.getColor(R.color.white_1),
            context.getColor(R.color.transparent),
            TileMode.CLAMP
        )
        view.tvSubtitle.paint.shader = gradient
    }

    fun showBorder() {
        view.borderView.setBackgroundResource(R.drawable.chili_story_border)
    }

    fun showBadge() {
        view.cvBadge.isVisible = true
    }

    private fun setBorderDrawable(drawable: Drawable) {
        view.borderView.foreground = drawable
    }

    private fun setOverlayDrawable(drawable: Drawable?) {
        view.overlayLayout.foreground = drawable
    }

    private fun isBadgeVisible(visible: Boolean) {
        view.cvBadge.isVisible = visible
    }

    fun setStoriesList(storiesList: List<DetailedStory>) {
        this.storiesList = storiesList
        initProgressBar(storiesList)
        showStory()
    }

    private fun initProgressBar(storiesList: List<DetailedStory>) {
        var idcounter = 1
        for (story in storiesList) {
            val progressBar = ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal).apply {
                visibility = View.VISIBLE
                id = idcounter
                tag = "story${idcounter++}"
                progressDrawable = ContextCompat.getDrawable(context, R.drawable.chili_story_progress_bar)
            }
            val params = LayoutParams(0, LayoutParams.MATCH_PARENT).apply {
                marginEnd = gapBetweenProgressBars / 2
                marginStart = gapBetweenProgressBars / 2
            }
            view.progressBarContainer.addView(progressBar, params)
        }

        val constraintSet = ConstraintSet()
        constraintSet.clone(view.progressBarContainer)

        var counter = storiesList.size
        for (story in storiesList) {
            val progressBar = findViewWithTag<ProgressBar>("story${counter}")
            if (progressBar != null) {
                if (storiesList.size > 1) {
                    when (counter) {
                        storiesList.size -> {
                            constraintSet.connect(getId("story${counter}"), ConstraintSet.END, LayoutParams.PARENT_ID, ConstraintSet.END)
                            constraintSet.connect(getId("story${counter}"), ConstraintSet.TOP, LayoutParams.PARENT_ID, ConstraintSet.TOP)
                            constraintSet.connect(getId("story${counter}"), ConstraintSet.START, getId("story${counter-1}"), ConstraintSet.END)
                        }
                        1 -> {
                            constraintSet.connect(getId("story${counter}"), ConstraintSet.TOP, LayoutParams.PARENT_ID, ConstraintSet.TOP)
                            constraintSet.connect(getId("story${counter}"), ConstraintSet.START, LayoutParams.PARENT_ID, ConstraintSet.START)
                            constraintSet.connect(getId("story${counter}"), ConstraintSet.END, getId("story${counter + 1}"), ConstraintSet.START)
                        }
                        else -> {
                            constraintSet.connect(getId("story${counter}"), ConstraintSet.TOP, LayoutParams.PARENT_ID, ConstraintSet.TOP)
                            constraintSet.connect(getId("story${counter}"), ConstraintSet.START, getId("story${counter-1}"), ConstraintSet.END)
                            constraintSet.connect(getId("story${counter}"), ConstraintSet.END, getId("story${counter + 1}"), ConstraintSet.START)
                        }
                    }
                } else {
                    constraintSet.connect(getId("story${counter}"), ConstraintSet.END, LayoutParams.PARENT_ID, ConstraintSet.END)
                    constraintSet.connect(getId("story${counter}"), ConstraintSet.TOP, LayoutParams.PARENT_ID, ConstraintSet.TOP)
                    constraintSet.connect(getId("story${counter}"), ConstraintSet.START, LayoutParams.PARENT_ID, ConstraintSet.START)
                }
            }
            counter--
        }
        constraintSet.applyTo(view.progressBarContainer)
    }

    private fun showStory() {
        storiesList[storyIndex - 1].run {
            imageUri?.let { loadImage(it) }
            title?.let { setTitle(it) }
            subtitle?.let { setSubtitle(it) }
            setOverlayDrawable(ContextCompat.getDrawable(context, R.drawable.chili_shading))
        }
        initProgressBarAnimation()
    }

    private fun initProgressBarAnimation() {
        val progressBar = findViewWithTag<ProgressBar>("story${storyIndex}")
        animation = ObjectAnimator.ofInt(progressBar, "progress", 0, 100).apply {
            duration = secondsToMillis(storyDuration)
            interpolator = LinearInterpolator()
            addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animator: Animator) {}

                override fun onAnimationEnd(animator: Animator) {
                    if (storyIndex - 1 <= storiesList.size) {
                        if (userClicked) {
                            userClicked = false
                        } else {
                            if (storyIndex < storiesList.size) {
                                storyIndex += 1
                                showStory()
                            } else {
                                onStoriesCompleted()
                            }
                        }
                    } else {
                        onStoriesCompleted()
                    }
                }

                override fun onAnimationCancel(animator: Animator) {
                    progressBar.progress = 100
                }

                override fun onAnimationRepeat(animator: Animator) {}
            })
        }
        animation.start()
    }

    private fun getId(tag: String): Int {
        return findViewWithTag<ProgressBar>(tag).id
    }

    private fun secondsToMillis(seconds: String): Long {
        return (seconds.toLongOrNull() ?: 3).times(1000)
    }

    private fun resetProgressBar(storyIndex: Int) {
        val currentProgressBar = findViewWithTag<ProgressBar>("story${storyIndex}")
        val lastProgressBar = findViewWithTag<ProgressBar>("story${storyIndex - 1}")
        currentProgressBar?.let { it.progress = 0 }
        lastProgressBar?.let { it.progress = 0 }
    }

    private fun completeProgressBar(storyIndex: Int) {
        val lastProgressBar = findViewWithTag<ProgressBar>("story${storyIndex}")
        lastProgressBar?.let {
            it.progress = 100
        }
    }



    private var startClickTime = 0L
    override fun onTouch(view: View?, event: MotionEvent?): Boolean {
        gestureDetector.onTouchEvent(event)
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                startClickTime = Calendar.getInstance().timeInMillis
                animation.pause()
            }
            MotionEvent.ACTION_UP -> {
                val clickDuration = Calendar.getInstance().timeInMillis - startClickTime
                if (clickDuration < 200) {
                    //click occurred
                    view?.let {
                        if (it.id == R.id.leftTouchPanel) leftPanelTouch()
                        else if (it.id == R.id.rightTouchPanel) rightPanelTouch()
                    }
                } else {
                    //hold click occurred
                    animation.resume()
                }
            }
        }
        return true
    }

    private fun rightPanelTouch() {
        if (storyIndex == storiesList.size) {
            completeProgressBar(storyIndex)
            onStoriesCompleted()
            return
        }
        userClicked = true
        animation.end()
        if (storyIndex < storiesList.size) storyIndex += 1
        showStory()
    }

    private fun leftPanelTouch() {
        userClicked = true
        animation.end()
        resetProgressBar(storyIndex)
        if (storyIndex > 1) storyIndex -= 1
        else storiesListener.tapToPreviousStory()
        showStory()
    }

    private fun onStoriesCompleted() {
        if (::storiesListener.isInitialized)
            storiesListener.onStoriesEnd()
    }

    private fun loadImage(imageUri: String) {
        if (!(context as Activity).isDestroyed)
            Glide.with(context as Activity)
                .load(imageUri)
                .centerCrop()
                .into(view.ivImage)
    }

    inner class GestureListener : GestureDetector.SimpleOnGestureListener() {
        override fun onFling(
            e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float
        ): Boolean {
            val diffX = e2!!.x - e1!!.x
            val diffY = e2.y - e1.y
            if (abs(diffX) < abs(diffY)) {
                if (abs(diffY) > 100 && abs(velocityY) > 100) {
                    if (diffY < 0) showDescription()
                    else storiesListener.onSwipeDown()
                    return true
                }
            }
            return super.onFling(e1, e2, velocityX, velocityY)
        }
    }

    private fun showDescription() {
        if (storiesList[storyIndex - 1].details != null) {
            view.tvSubtitle.paint.shader = null
            view.tvSubtitle.text = storiesList[storyIndex - 1].details
            setOverlayDrawable(ContextCompat.getDrawable(context, R.drawable.chili_semi_transparent_overlay))
        }
        invalidate()
    }
}

data class StoryViewVariables(
    var progressBarContainer: ConstraintLayout,
    var overlayLayout: FrameLayout,
    var leftTouchPanel: FrameLayout,
    var rightTouchPanel: FrameLayout,
    var borderView: FrameLayout,
    var cvBadge: CardView,
    var ivClose: ImageView,
    var ivImage: ImageView,
    var tvTitle: TextView,
    var tvSubtitle: TextView,
    var btnDetails: Button
)