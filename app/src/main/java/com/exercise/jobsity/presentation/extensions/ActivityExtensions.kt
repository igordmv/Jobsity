package com.exercise.jobsity.presentation.extensions

import android.app.Activity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

fun Activity.runOnUI(action: () -> Unit) {
    GlobalScope.launch(Dispatchers.Main) {
        if (!isFinishing) action.invoke()
    }
}