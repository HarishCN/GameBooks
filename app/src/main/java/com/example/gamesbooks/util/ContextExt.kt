package com.example.gamesbooks.util

import android.content.Context
import android.widget.Toast

fun Context.toastMessage(text: String?, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, text, duration).show()
}