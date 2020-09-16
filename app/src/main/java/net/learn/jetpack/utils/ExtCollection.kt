package net.learn.jetpack.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.makeVisible() {
    visibility = View.VISIBLE
}

fun View.makeGone() {
    visibility = View.GONE
}

fun showSnackBar(view: View, message: String) {
    Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
}
