package io.kamara.android.samples.views

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import io.kamara.android.samples.R
import kotlinx.android.synthetic.main.layout_bubble_item.view.*


class OnboardingBubbleItem
@kotlin.jvm.JvmOverloads
constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    FrameLayout(context, attrs, defStyleAttr) {

    init {
        initView(attrs)
    }

    private fun initView(attrs: AttributeSet?) {
        View.inflate(context, R.layout.layout_bubble_item, this)

        val ta = context.obtainStyledAttributes(attrs, R.styleable.OnboardingBubbleItem)
        val textColor = ta.getColor(R.styleable.OnboardingBubbleItem_bl_text_color, Color.BLACK)
        val gravity = ta.getInt(R.styleable.OnboardingBubbleItem_bl_alignment, 0)
        val marginTop =
            ta.getDimensionPixelSize(R.styleable.OnboardingBubbleItem_bl_margin_top, 16)
        val marginStart =
            ta.getDimensionPixelSize(R.styleable.OnboardingBubbleItem_bl_margin_start, 16)
        val marginEnd =
            ta.getDimensionPixelSize(R.styleable.OnboardingBubbleItem_bl_margin_end, 16)
        val marginBottom =
            ta.getDimensionPixelSize(R.styleable.OnboardingBubbleItem_bl_margin_bottom, 16)
        val text = ta.getString(R.styleable.OnboardingBubbleItem_bl_text)

        (bubbleItemTitle.layoutParams as ConstraintLayout.LayoutParams)
            .setMargins(marginStart, marginTop, marginEnd, marginBottom)
        bubbleItemTitle.text = text
        bubbleItemTitle.setTextColor(textColor)
        bubbleItemTitle.setTextGravity(gravity)

        (bubbleItemDescription.layoutParams as ConstraintLayout.LayoutParams)
            .setMargins(marginStart, 16, marginEnd, marginBottom)

        val description = ta.getString(R.styleable.OnboardingBubbleItem_bl_description)
        bubbleItemDescription.text = description
        bubbleItemDescription.setTextColor(textColor)
        bubbleItemDescription.setTextGravity(gravity)
        ta.recycle()
    }

    private fun TextView.setTextGravity(value: Int) {
        gravity = when (value) {
            0 -> Gravity.START
            1 -> Gravity.CENTER
            else -> Gravity.END
        }
    }

    private fun showDetails() {
        bubbleItemContainer
            .animate()
            .alpha(1f).setDuration(500)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    bubbleItemTitle.visibility = View.VISIBLE
                    bubbleItemDescription.visibility = View.VISIBLE
                    bubbleItemIcons.visibility = View.VISIBLE
                }
            })
            .start()
    }

    private fun hideDetails() {
        bubbleItemContainer
            .animate()
            .alpha(0f).setDuration(500)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    bubbleItemTitle.visibility = View.INVISIBLE
                    bubbleItemDescription.visibility = View.INVISIBLE
                    bubbleItemIcons.visibility = View.INVISIBLE
                }
            })
            .start()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (w >= 200.toPixels()) {
            showDetails()
        } else {
            hideDetails()
        }
    }

    private fun Int.toPixels(): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            this.toFloat(), Resources.getSystem().displayMetrics
        ).toInt()
    }
}