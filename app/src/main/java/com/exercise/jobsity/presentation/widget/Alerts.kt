package com.exercise.jobsity.presentation.widget

import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.exercise.jobsity.R
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar

/**
 * Snack Alert used to show Errors
 */
class Alerts {

    companion object {

        fun snackInfo(viewScreen: View, text: String, duration: Int = 2000, callback: () -> Unit = {}) {
            Snackbar.make(viewScreen, text, duration).apply {
                view.setBackgroundColor(ContextCompat.getColor(view.context, R.color.app_green))
                setActionTextColor(colorText())
                addCallback(object : BaseTransientBottomBar.BaseCallback<Snackbar>() {
                    override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                        super.onDismissed(transientBottomBar, event)
                        callback()
                    }
                })
                view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text).apply {
                    maxLines = 10
                }

                show()
            }
        }

        private fun colorText(): Int {
            return Color.parseColor("#FFFFEE19")
        }
    }
}