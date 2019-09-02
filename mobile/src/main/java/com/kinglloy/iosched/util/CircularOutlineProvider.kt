package com.kinglloy.iosched.util

import android.graphics.Outline
import android.view.View
import android.view.ViewOutlineProvider

/**
 * Yalin on 2019-09-02
 */
object CircularOutlineProvider : ViewOutlineProvider() {
    override fun getOutline(view: View, outline: Outline) {
        outline.setOval(
            view.paddingLeft,
            view.paddingTop,
            view.width - view.paddingLeft,
            view.height - view.paddingBottom
        )
    }
}