package com.coursework.androidtodometer.extensions

import com.google.android.material.textfield.TextInputLayout

/**
 * Clear the error state. In addition, set isErrorEnabled to false in order
 * to avoid leaving the space reserved for error text empty.
 */
fun TextInputLayout.clearError() {
    error = null
    isErrorEnabled = false
}
