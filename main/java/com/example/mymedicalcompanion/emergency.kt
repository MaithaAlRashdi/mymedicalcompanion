package com.example.mymedicalcompanion

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_emergency.*


class emergency : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emergency)
        supportActionBar?.hide()
        btnsendsos.setOnClickListener {
            val smsNumber: String = "998"
            val smsText: String = "HELP"

           /* val uri: Uri = Uri.parse("smsto:$smsNumber")
            val intent = Intent(Intent.ACTION_SENDTO, uri)
            intent.putExtra("sms_body", smsText)
            startActivity(intent)*/
            val uri: Uri = Uri.parse(smsNumber)
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + Uri.encode(smsNumber)))
            startActivity(intent)

           /* val builder=AlertDialog.Builder(this)
            builder.setTitle("SOS MESSAGE")
            builder.setMessage("SOS MESSAGE TO 998 IS SENT SUCCESSFULLY")
            builder.setPositiveButton("OK"){ dialogInterface, which ->
                finish()
            }*/


        }
    }
}


