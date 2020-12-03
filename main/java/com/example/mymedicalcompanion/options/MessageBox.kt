package com.example.mymedicalcompanion.options

import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.mymedicalcompanion.R

class MessageBox() {
    fun show(message: String, title: String, context: Context) {
        val builder = AlertDialog.Builder(context)
        //set title for alert dialog
        builder.setTitle(title)
        //set message for alert dialog
        builder.setMessage(message)

        //performing positive action
        builder.setPositiveButton("OK"){dialogInterface, which ->

        }

        // Create the AlertDialog
        val alertDialog: AlertDialog = builder.create()
        // Set other dialog properties
        alertDialog.setCancelable(false)
        alertDialog.show()

    }
}