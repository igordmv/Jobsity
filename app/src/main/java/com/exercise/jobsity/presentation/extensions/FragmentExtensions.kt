package com.exercise.jobsity.presentation.extensions

import androidx.fragment.app.Fragment

fun Fragment.runOnUI(action: () -> Unit) {
    activity?.runOnUI(action)
}
