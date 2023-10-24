package id.anantyan.synrgychapter4.common

import android.content.DialogInterface
import com.google.android.material.dialog.MaterialAlertDialogBuilder

fun MaterialAlertDialogBuilder.createDialog(
    title: String,
    message: String,
    negativeButton: String? = "NO!",
    positiveButton: String? = "YES",
    negativeAction: ((dialog: DialogInterface) -> Unit)? = null,
    positiveAction: ((dialog: DialogInterface) -> Unit)? = null
) {
    val dialog = this
    dialog.setTitle(title)
    dialog.setMessage(message)
    dialog.setCancelable(false)
    dialog.setNegativeButton(negativeButton) { dialog, _ ->
        negativeAction?.invoke(dialog)
    }
    dialog.setPositiveButton(positiveButton) { dialog, _ ->
        positiveAction?.invoke(dialog)
    }
    dialog.show()
}