package com.kinglloy.iosched.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import androidx.annotation.StringRes
import com.kinglloy.iosched.R
import androidx.core.view.postDelayed

/**
 * A custom snackbar implementation allowing more control over placement and entry/exit animations.
 */
class FadingSnackbar(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {
    private val message: TextView
    private val action: Button

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.fading_snackbar_layout, this, true)
        message = view.findViewById(R.id.snackbar_text)
        action = view.findViewById(R.id.snackbar_action)
    }

    fun dismiss() {
        if (visibility == View.VISIBLE && alpha == 1f) {
            animate()
                .alpha(0f)
                .withEndAction { visibility = View.GONE }
                .duration = EXIT_DURATION
        }
    }

    fun show(
        @StringRes messageId: Int = 0,
        messageText: CharSequence? = null,
        @StringRes actionId: Int? = null,
        longDuration: Boolean = true,
        actionClick: () -> Unit = { dismiss() },
        dismissListener: () -> Unit = {}
    ) {
        message.text = messageText ?: context.getString(messageId)
        if (actionId != null) {
            action.run {
                visibility = View.VISIBLE
                text = context.getString(actionId)
                setOnClickListener { actionClick() }
            }
        } else {
            action.visibility = View.GONE
        }
        alpha = 0f
        visibility = View.VISIBLE
        animate()
            .alpha(1f)
            .duration = ENTER_DURATION
        val showDuration = ENTER_DURATION + if (longDuration) LONG_DURATION else SHORT_DURATION
        postDelayed(showDuration) {
            dismiss()
            dismissListener()
        }
    }

    companion object {
        private const val ENTER_DURATION = 300L
        private const val EXIT_DURATION = 200L
        private const val SHORT_DURATION = 1_500L
        private const val LONG_DURATION = 2_750L
    }
}