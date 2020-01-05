package io.kamara.android.samples.fragments

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatDialog
import androidx.fragment.app.DialogFragment
import io.kamara.android.samples.R
import kotlinx.android.synthetic.main.fragment_onboarding.*
import kotlinx.android.synthetic.main.onboading_motion_layout.*


class OnboardingFragment : DialogFragment() {
    private val indicators = mutableListOf<View>()
    private var currentPosition = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_onboarding, container, false)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AppCompatDialog(context, R.style.DialogFullScreen)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setIndicators()
        updateIndicators()
        attachNextButtonListener()
        attachPreviousButtonListener()
    }

    private fun attachNextButtonListener() {
        next.setOnClickListener {
            when (currentPosition) {
                0 -> next.navigate(R.id.firstTransition, R.id.secondTransition)
                1 -> next.navigate(R.id.secondTransition, R.id.thirdTransition)
                else -> dismiss()
            }
        }
    }

    private fun attachPreviousButtonListener() {
        previous.setOnClickListener {
            when (currentPosition) {
                2 -> previous.navigate(R.id.thirdTransition, R.id.secondTransition)
                else -> previous.navigate(R.id.secondTransition, R.id.firstTransition)
            }
        }
    }

    private fun updateNavState() {
        when {
            currentPosition > 0 -> previous.visibility = View.VISIBLE
            else -> previous.visibility = View.INVISIBLE
        }
    }

    private fun Button.navigate(startId: Int, endId: Int) {
        currentPosition = when (id) {
            R.id.next -> currentPosition.inc()
            else -> currentPosition.dec()
        }
        updateNavState()
        if (endId == R.id.thirdTransition) {
            navContainer.setBackgroundColor(resources.getColor(R.color.color_onboarding_page3))
            next.setText(R.string.button_text_complete)
        } else {
            next.setText(R.string.button_text_next)
            navContainer.setBackgroundColor(resources.getColor(R.color.onboarding_background))
        }
        motionLayout.setTransition(startId, endId)
        motionLayout.transitionToEnd()
        updateIndicators()
    }

    private fun updateIndicators() {
        indicators.forEachIndexed { index, view ->
            val background = when (index) {
                currentPosition -> R.drawable.selected_dot
                else -> R.drawable.un_selected_dot
            }
            view.setBackgroundDrawable(context?.getDrawable(background))
        }
    }

    private fun setIndicators() {
        val dotRadius: Int = convertDpToPixel(12f, context)
        val margin: Int = convertDpToPixel(4f, context)
        indicators.clear()
        indicatorsContainer.removeAllViews()
        for (i in 0 until 3) {
            val view = View(context)
            view.id = View.generateViewId()
            val layoutParams = FrameLayout.LayoutParams(dotRadius * 2, dotRadius * 2)
            layoutParams.setMargins(margin, margin, margin, margin)
            view.layoutParams = layoutParams
            indicators.add(view)
            indicatorsContainer.addView(view)
        }
    }

    private fun convertDpToPixel(dp: Float, context: Context?): Int {
        if (context != null) {
            return (dp * (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)).toInt()
        }
        return 0
    }
}
