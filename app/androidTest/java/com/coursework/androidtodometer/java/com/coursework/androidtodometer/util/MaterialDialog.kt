package com.coursework.androidtodometer.util

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.coursework.androidtodometer.R

/**
 * MaterialDialog Util class to create a Material Dialog.
 */
class MaterialDialog {
    companion object {
        inline fun createDialog(
            context: Context,
            dialogBuilder: AlertDialog.Builder.() -> Unit
        ): AlertDialog {
            val builder = MaterialAlertDialogBuilder(
                context,
                R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog_Centered
            ).setTitle(" ")
            builder.dialogBuilder()
            return builder.create()
        }

        fun AlertDialog.Builder.positiveButton(
            text: String,
            handleClick: (which: Int) -> Unit = {}
        ): AlertDialog.Builder =
            this.setPositiveButton(text) { _, which -> handleClick(which) }

        fun AlertDialog.Builder.positiveButton(
            @StringRes resId: Int,
            handleClick: (which: Int) -> Unit = {}
        ): AlertDialog.Builder =
            this.setPositiveButton(resId) { _, which -> handleClick(which) }

        fun AlertDialog.Builder.negativeButton(
            text: String,
            handleClick: (which: Int) -> Unit = {}
        ): AlertDialog.Builder =
            this.setNegativeButton(text) { _, which -> handleClick(which) }

        fun AlertDialog.Builder.icon(@DrawableRes iconId: Int): AlertDialog.Builder =
            this.setIcon(iconId)

        fun AlertDialog.Builder.message(message: CharSequence): AlertDialog.Builder =
            this.setMessage(message)

        fun AlertDialog.Builder.message(@StringRes resId: Int): AlertDialog.Builder =
            this.setMessage(context.getString(resId))

        fun AlertDialog.Builder.singleChoiceItems(
            items: Array<CharSequence>,
            checkedItem: Int,
            handleClick: (which: Int) -> Unit = {}
        ): AlertDialog.Builder =
            this.setSingleChoiceItems(items, checkedItem) { _, which -> handleClick(which) }

        fun AlertDialog.Builder.title(title: String): AlertDialog.Builder =
            this.setTitle(title)

        fun AlertDialog.Builder.title(@StringRes titleId: Int): AlertDialog.Builder =
            this.setTitle(titleId)
    }
}
