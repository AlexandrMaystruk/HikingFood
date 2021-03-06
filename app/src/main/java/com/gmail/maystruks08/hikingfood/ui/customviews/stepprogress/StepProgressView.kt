package com.gmail.maystruks08.hikingfood.ui.customviews.stepprogress

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.TransitionDrawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.core.view.*
import com.gmail.maystruks08.hikingfood.R
import com.gmail.maystruks08.hikingfood.ui.customviews.DrawableHelper
import kotlin.NoSuchElementException

class StepProgressView : ViewGroup, View.OnClickListener {

    private val stepProgress = StepProgress()
    private val drawableHelper = DrawableHelper()

    private lateinit var ovalStrokeDrawable: Drawable
    private lateinit var ovalStrokeInactiveDrawable: Drawable
    private lateinit var ovalDrawable: Drawable
    private lateinit var checkedDrawable: Drawable
    private lateinit var arcActiveDrawable: ColorDrawable
    private lateinit var arcInactiveDrawable: ColorDrawable
    private var nodeTextList = mutableListOf("3", "O", "У")

    @ColorInt
    private var textNodeColor = ContextCompat.getColor(context, R.color.colorWhite)
    @ColorInt
    private var nodeColor = ContextCompat.getColor(context, R.color.colorPrimary)
    @ColorInt
    private var arcColor = ContextCompat.getColor(context, R.color.colorAccent)
    @ColorInt
    private var colorInactive = ContextCompat.getColor(context, R.color.colorGrey)

    private var stepsCount = 1
    private var startFrom = 0
    private var nodeHeight = -1f
    private var textNodeSize = 35
    private var arcHeight = SViewUtils.toPx(2f, context)
    private var arcPadding = SViewUtils.toPx(10f, context)
    private val minSpacingLength = SViewUtils.toPx(10, context)
    private val nodeDefaultRatio = 0.1
    private val arcsMaxRatio = 0.60
    private val arcTransitionDuration = 200

    var onStepSelected: ((Int) -> Unit)? = null

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        context.theme.obtainStyledAttributes(attrs, R.styleable.StepProgressView, 0, 0).apply {
            try {
                //common setup
                val countOfStep = getInteger(R.styleable.StepProgressView_stepsCount, stepsCount)
                stepsCount = if (countOfStep > 3) 3 else countOfStep
                check(stepsCount >= 0) { "Steps count can't be a negative number" }
                colorInactive = getColor(R.styleable.StepProgressView_colorInactive, colorInactive)
                //node setup
                nodeHeight = getDimension(R.styleable.StepProgressView_nodeHeight, nodeHeight)
                nodeColor = getColor(R.styleable.StepProgressView_nodeColor, nodeColor)
                //arc setup
                arcHeight = getDimension(R.styleable.StepProgressView_arcWidth, arcHeight)
                arcPadding = getDimension(R.styleable.StepProgressView_arcPadding, arcPadding)
                arcColor = getColor(R.styleable.StepProgressView_arcColor, arcColor)
                //titles setup
                textNodeSize = getDimensionPixelSize(R.styleable.StepProgressView_textNodeSize, textNodeSize)
                textNodeColor = getColor(R.styleable.StepProgressView_textNodeColor, textNodeColor)
            } finally {
                recycle()
            }
        }
        init()
    }

    private fun init() {
        ovalStrokeDrawable = drawableHelper.createStrokeOvalDrawable(context, nodeColor)
        ovalStrokeInactiveDrawable = drawableHelper.createStrokeOvalDrawable(context, colorInactive)
        ovalDrawable = drawableHelper.createOvalDrawable(nodeColor)
        checkedDrawable = drawableHelper.createCheckDrawable(context, nodeColor)
        arcActiveDrawable = ColorDrawable(arcColor)
        arcInactiveDrawable = ColorDrawable(colorInactive)
        stepProgress.reset()
        createViews()
    }

    private fun createViews() {
        if (stepsCount == 0) {
            return
        }
        removeAllViews()
        for (i in 0 until stepsCount) {
            addView(textViewForStep(i, i == 0))
            if (i != stepsCount - 1) {
                addView(arcView(i))
            }
        }
    }

    //Global variables to save measuring results
    private var titleTextMaxHeight = 0
    private var titleTextMaxWidth = 0
    private var textOverflow = 0
    //flag to show if overflow mode was apply to view
    //overflow happen when requested nodes size does not fit into layout
    private var hasNodeOverflow = false

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val wMode = MeasureSpec.getMode(widthMeasureSpec)
        val hMode = MeasureSpec.getMode(heightMeasureSpec)
        val wSize = MeasureSpec.getSize(widthMeasureSpec)
        val hSize = MeasureSpec.getSize(heightMeasureSpec)
        //Calculates width where views could be actually drawn
        val w = if (wMode != MeasureSpec.EXACTLY) {
            //respect margins if measure spec is not exact
            wSize - paddingStart - paddingEnd - marginStart - marginEnd
        } else {
            wSize - paddingStart - paddingEnd
        }
        val nodeSize = getNodeSize(w, heightMeasureSpec)
        val arcWidth = if (stepsCount > 1) {
            getArcWidth(widthMeasureSpec, w, nodeSize)
        } else {
            0
        }
        children.forEach {
            when {
                //Measure titles for step (node) views
                //Text takes a width equal to node size + allowed overflow size
                it is TextView && (it.tag as String == STEP_TITLE_TAG) -> {
                    val wSpecToMeasure = MeasureSpec.makeMeasureSpec(nodeSize + textOverflow, MeasureSpec.AT_MOST)
                    val hSpecToMeasure = MeasureSpec.makeMeasureSpec(hSize, MeasureSpec.AT_MOST)
                    it.measure(wSpecToMeasure, hSpecToMeasure)
                    //save measuring results to use them onLayout
                    val titleTextWidth = it.measuredWidth
                    if (titleTextWidth > titleTextMaxWidth) {
                        titleTextMaxWidth = titleTextWidth
                    }
                }
                else -> {
                    //If children is not a text view (text view is used for nodes and titles)
                    //than it is a view for arc
                    //
                    //measures arc view
                    val arcWSpec = MeasureSpec.makeMeasureSpec(arcWidth, MeasureSpec.EXACTLY)
                    val arcHSpec = MeasureSpec.makeMeasureSpec(arcHeight.toInt(), MeasureSpec.EXACTLY)
                    if (!hasNodeOverflow) {
                        (it.layoutParams as LinearLayout.LayoutParams).setMargins(
                            arcPadding.toInt(), 0, arcPadding.toInt(), 0
                        )
                    } else {
                        //remove margin if view is in overflow mode to escape case when arc is smaller than its margins
                        (it.layoutParams as LinearLayout.LayoutParams).setMargins(0)
                    }
                    it.measure(arcWSpec, arcHSpec)
                }
            }
        }
        //Measure node (step) views. Text view is uses to draw nodes
        children.filter { it is TextView && (it.tag as String != STEP_TITLE_TAG) }.forEach {
            //Resolve margins to fit node view with text vertically
            val nodeActualSize = if (hMode != MeasureSpec.EXACTLY) {
                (it.layoutParams as LinearLayout.LayoutParams).setMargins(
                    0, titleTextMaxHeight, 0, 0
                )
                nodeSize
            } else {
                (it.layoutParams as LinearLayout.LayoutParams).setMargins(
                    titleTextMaxHeight / 2, titleTextMaxHeight, titleTextMaxHeight / 2, 0
                )
                nodeSize - titleTextMaxHeight
            }
            val nodeViewSizeSpec = MeasureSpec.makeMeasureSpec(nodeActualSize, MeasureSpec.EXACTLY)
            it.measure(nodeViewSizeSpec, nodeViewSizeSpec)
        }
        //Resolve desired width and height that step progress want to take to fit all views
        //If width or height mode are exact, parameters from specs are used
        val desiredH = if (hMode != MeasureSpec.EXACTLY) {
            nodeSize + paddingTop + paddingBottom + titleTextMaxHeight
        } else {
            hSize
        }
        val desiredW = if (wMode != MeasureSpec.EXACTLY) {
            nodeSize * stepsCount + arcWidth * (stepsCount - 1) + paddingStart + paddingEnd + textOverflow
        } else {
            wSize
        }
        setMeasuredDimension(desiredW, desiredH)
    }


    //Calculate optimal node size to fit view width.
    //If node with default or exact size does not fit layout, it scales down to fit available width
    private fun getNodeSize(width: Int, heightMeasureSpec: Int): Int {
        val hMode = MeasureSpec.getMode(heightMeasureSpec)
        val hSize = MeasureSpec.getSize(heightMeasureSpec)
        return if (hMode == MeasureSpec.AT_MOST || hMode == MeasureSpec.UNSPECIFIED) {
            val nodeDefaultSize = if (nodeHeight != -1f) {
                nodeHeight.toInt()
            } else {
                (width * nodeDefaultRatio).toInt()
            }
            //If node fits take it default size
            //If not calculate the maximal size that the node could take respecting view width
            if (nodeSizeFits(width, nodeDefaultSize)) {
                hasNodeOverflow = false
                nodeDefaultSize
            } else {
                hasNodeOverflow = true
                maximalNodeSize(width)
            }
        } else {
            hSize
        }
    }

    //Calculate optimal arcs size for steps
    //Arcs take all remaining space that is not taken by node or margins
    //If WRAP_CONTENT width is set for layout arc size could be reduced
    //to respect optimal proportions for nodes and arcs
    private fun getArcWidth(widthMeasureSpec: Int, width: Int, nodeSize: Int): Int {
        val wMode = MeasureSpec.getMode(widthMeasureSpec)
        val arcsCount = stepsCount - 1
        val allArcsWidth = (width - stepsCount * nodeSize)
        val isArcsWidthAppropriate = allArcsWidth <= width * arcsMaxRatio
        return if (isArcsWidthAppropriate || (!isArcsWidthAppropriate && wMode == MeasureSpec.EXACTLY)) {
            allArcsWidth / arcsCount
        } else {
            (width * arcsMaxRatio).toInt() / arcsCount
        }
    }

    private fun nodeSizeFits(width: Int, desiredSize: Int): Boolean {
        return (width - desiredSize * stepsCount) >= minSpacingLength * (stepsCount - 1)
    }

    private fun maximalNodeSize(width: Int): Int {
        return (width - minSpacingLength * (stepsCount - 1)) / stepsCount
    }

    //Arranges all created views in a proper order from left to right
    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        //calculate insets
        var left = paddingStart + textOverflow / 2
        val top = paddingTop
        val step: View
        try {
            //Take a node view to calculate it margins
            step = children.first {
                it is TextView && it.tag as String != STEP_TITLE_TAG
            }
        } catch (e: NoSuchElementException) {
            e.printStackTrace()
            return
        }
        //Width of a node view
        val stepWidth = step.measuredWidth + step.paddingStart + step.paddingEnd
        //Layout children sequentially
        children.forEach {
            when {
                //step title
                it is TextView && (it.tag as String == STEP_TITLE_TAG) -> {
                    val centerPadding = (stepWidth - it.measuredWidth) / 2
                    it.layout(
                        left + centerPadding, top,
                        left + it.measuredWidth + centerPadding, top + it.measuredHeight
                    )
                }
                //step
                it is TextView && (it.tag as String != STEP_TITLE_TAG) -> {
                    val centerPadding = (stepWidth - it.measuredWidth) / 2
                    it.layout(
                        left + it.marginLeft + centerPadding,
                        top + it.marginTop,
                        left + it.measuredWidth + centerPadding,
                        top + it.measuredHeight + it.marginTop
                    )
                    left += it.measuredWidth + it.marginRight + it.marginLeft
                }
                //arc
                else -> {
                    val arcTop = ((b - t) - it.measuredHeight) / 2
                    it.layout(
                        left + it.marginStart,
                        arcTop + titleTextMaxHeight / 2,
                        left + it.measuredWidth - it.marginEnd,
                        arcTop + it.measuredHeight + titleTextMaxHeight / 2
                    )
                    left += it.measuredWidth
                }
            }
        }
    }

    private fun textViewForStep(stepPosition: Int, isActive: Boolean): TextView {
        return TextView(context).apply {
            text = try {
                nodeTextList[stepPosition]
            } catch (e: IndexOutOfBoundsException) {
                ""
            }
            background = if (isActive) {
                setTextColor(textNodeColor)
                ovalStrokeDrawable
            } else {
                setTextColor(colorInactive)
                ovalStrokeInactiveDrawable
            }
            gravity = Gravity.CENTER
            layoutParams = getDefaultElementLayoutParams()
            setTextSize(TypedValue.COMPLEX_UNIT_PX, textNodeSize.toFloat())
            setOnClickListener(this@StepProgressView)
            tag = NODE_TAG_PREFIX + stepPosition
        }
    }


    private fun getDefaultElementLayoutParams(
        width: Int = LayoutParams.WRAP_CONTENT,
        height: Int = LayoutParams.WRAP_CONTENT
    ): LinearLayout.LayoutParams {
        return LinearLayout.LayoutParams(width, height)
    }

    private fun arcView(position: Int): ArcView {
        return ArcView(context).apply {
            background = TransitionDrawable(arrayOf(arcInactiveDrawable, arcActiveDrawable))
            layoutParams = LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT
            )
            tag = ARC_TAG_PREFIX + position
        }
    }

    override fun onClick(v: View?) {
        if (v != null && v.tag is String) {
            val tagStr = v.tag as String
            val prefixEnd = tagStr.indexOf(NODE_TAG_PREFIX)
            if (prefixEnd != -1) {
                val numberIndex = prefixEnd + NODE_TAG_PREFIX.length
                val selectedStepIndex = tagStr.substring(numberIndex).toInt()
                stepProgress.selectStep(selectedStepIndex)
                onStepSelected?.invoke(selectedStepIndex)
            }
        }
    }

    private fun changeStepStateView(stepNumber: Int, newState: StepState) {
        val view = findViewWithTag<TextView>(NODE_TAG_PREFIX + stepNumber)
        view?.let {
            when (newState) {
                StepState.DONE -> {
                    it.background = checkedDrawable
                    it.textSize = 0f
                    it.setTextColor(textNodeColor)
                }
                StepState.SELECTED_DONE -> {
                    it.background = ovalDrawable
                    it.setTextColor(ContextCompat.getColor(context, R.color.colorWhite))
                }
                StepState.SELECTED_UNDONE -> {
                    it.background = ovalStrokeDrawable
                    it.setTextColor(textNodeColor)
                }
                else -> {
                    it.background = ovalStrokeInactiveDrawable
                    it.setTextColor(colorInactive)
                }
            }
        }
    }

    private fun animateProgressArc(arcNumber: Int, activated: Boolean) {
        val view = findViewWithTag<ArcView>(
            ARC_TAG_PREFIX + arcNumber
        )
        if (activated) {
            if (!view.isHighlighted) {
                view.isHighlighted = true
                (view.background as TransitionDrawable).startTransition(arcTransitionDuration)
            }
        } else {
            if (view.isHighlighted) {
                view.isHighlighted = false
                (view.background as TransitionDrawable).resetTransition()
            }
        }
    }

    /**
     * Set number of steps for progress
     * @param stepsCount steps number
     */
    @Throws(IllegalStateException::class)
    fun setStepsCount(stepsCount: Int, startFrom: Int) {
        check(!(stepsCount < 0 || stepsCount > 3 || startFrom > 3)) { "Steps count can't be a negative number or more that 3" }
        this.stepsCount = stepsCount
        this.startFrom = startFrom
        for (i in 0 until startFrom) {
            nodeTextList.add(nodeTextList.removeAt(0))
        }

        while (nodeTextList.lastIndex >= stepsCount){
            nodeTextList.removeAt(nodeTextList.lastIndex)
        }
        resetView()
        invalidate()
    }

    /**
     * Go to next step
     * @param isCurrentDone if true marks current selected step as done
     * @return true if all steps are finished
     */
    fun nextStep(isCurrentDone: Boolean): Boolean {
        return stepProgress.nextStep(isCurrentDone) == 1
    }

    fun getCurrentStep(): Int {
        return stepProgress.currentStep
    }

    private fun resetView() {
        stepProgress.reset()
        createViews()
    }

    private inner class StepProgress {

        var currentStep = 0

        private var stepsStates: Array<StepState> = getInitialStepProgress()

        fun reset() {
            currentStep = 0
            stepsStates = getInitialStepProgress()
        }

        private fun getInitialStepProgress(): Array<StepState> {
            return Array(stepsCount) {
                if (it == 0) {
                    StepState.SELECTED_UNDONE
                } else {
                    StepState.UNDONE
                }
            }
        }

        fun selectStep(stepNumber: Int) {
            if (stepNumber == currentStep) {
                return
            }
            val oldStep = currentStep
            currentStep = stepNumber
            val currentStepState = stepsStates[currentStep]
            val newOldStepState = if (isStepDone(oldStep)) StepState.DONE else StepState.UNDONE
            val newCurrentStepState = if (currentStepState == StepState.DONE) StepState.SELECTED_DONE else StepState.SELECTED_UNDONE
            changeStepState(oldStep, newOldStepState)
            changeStepState(currentStep, newCurrentStepState)
        }

        fun nextStep(isCurrentDone: Boolean): Int {
            if (isAllDone()) {
                return 1
            }
            val nextStep = currentStep + 1
            if (isCurrentDone) {
                changeStepState(currentStep, StepState.DONE)
            }
            if (nextStep < stepsCount) {
                val nextStepState = stepsStates[nextStep]
                val newNextStepState = if (nextStepState == StepState.DONE) StepState.SELECTED_DONE else StepState.SELECTED_UNDONE
                changeStepState(nextStep, newNextStepState)
                currentStep = nextStep
            }
            return if (isAllDone()) 1 else 0
        }

        fun markCurrentStepDone() {
            if (isStepDone()) {
                return
            }
            changeStepState(
                currentStep,
                StepState.SELECTED_DONE
            )
        }

        fun markCurrentStepUndone() {
            if (isStepUndone()) {
                return
            }
            changeStepState(
                currentStep,
                StepState.SELECTED_UNDONE
            )
        }

        private fun updateArcsState() {
            var allDone = true
            for (i in 0..stepsStates.size - 2) {
                if (isStepDone(i) && allDone) {
                    animateProgressArc(i, true)
                } else {
                    allDone = false
                    animateProgressArc(i, false)
                }
            }
        }

        fun isStepDone(stepNumber: Int = -1): Boolean {
            val stepState = if (stepNumber == -1) {
                stepsStates[currentStep]
            } else {
                stepsStates[stepNumber]
            }
            return stepState == StepState.DONE || stepState == StepState.SELECTED_DONE
        }

        private fun isStepUndone(stepNumber: Int = -1): Boolean {
            val stepState = if (stepNumber == -1) {
                stepsStates[currentStep]
            } else {
                stepsStates[stepNumber]
            }
            return stepState == StepState.UNDONE || stepState == StepState.SELECTED_UNDONE
        }

        private fun changeStepState(stepNumber: Int, newState: StepState) {
            stepsStates[stepNumber] = newState
            changeStepStateView(stepNumber, newState)
            updateArcsState()
        }

        fun isAllDone(): Boolean {
            stepsStates.forEach {
                if (it == StepState.UNDONE || it == StepState.SELECTED_UNDONE) {
                    return false
                }
            }
            return true
        }

    }

    companion object {

        private const val NODE_TAG_PREFIX = "stn_"

        private const val ARC_TAG_PREFIX = "atn_"

        private const val STEP_TITLE_TAG = "step_title"

    }

}