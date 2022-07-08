package com.design.chili.extensions

import android.view.View
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.design.chili.R
import com.design.chili.view.snackbar.ActionInfo
import com.design.chili.view.snackbar.ChiliSnackBar
import com.design.chili.view.snackbar.TimerInfo
import com.google.android.material.snackbar.BaseTransientBottomBar

fun AppCompatActivity.showSimpleSnackbar(view: View, message: String) {
    ChiliSnackBar.Builder(view)
        .setSnackbarMessage(message)
        .build()
        .show()
}


fun AppCompatActivity.showInfinitiveLoaderSnackbar(view: View, message: String) {
    ChiliSnackBar.Builder(view)
        .setSnackbarMessage(message)
        .setIsInfiniteLoaderSnackbar(true)
        .build()
        .show()
}

fun AppCompatActivity.showTimerActionBeforeSuccessCnackbar(
    view: View,
    timerMessage: String,
    successMessage: String,
    actionText: String,
    actionClick: (ChiliSnackBar) -> Unit,
    onTimerExpire: () -> Unit,
    timerDurationMills: Long
) {
    ChiliSnackBar.Builder(view)
        .setSnackbarDurationMills(timerDurationMills + 2000)
        .setSnackbarMessage(timerMessage)
        .setSnackbarTimerInfo(TimerInfo(timerDurationMills, {
            it.setSnackbarIcon(R.drawable.ic_success)
            it.setMessage(successMessage)
            onTimerExpire()
        }))
        .setSnackbarActionInfo(ActionInfo(actionText, actionClick))
        .build()
        .show()
}