package com.example.mymedicalcompanion

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_usefullinks.*

class usefullinks : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_usefullinks)
        supportActionBar?.title="Useful Links"
        btnhealth.setOnClickListener {
            val openURL = Intent(Intent.ACTION_VIEW)
            openURL.data = Uri.parse("https://www.health.com/")
            startActivity(openURL)
        }
        btnmedicalnewstoday.setOnClickListener {
            val openURL = Intent(Intent.ACTION_VIEW)
            openURL.data = Uri.parse("https://www.medicalnewstoday.com/articles/150999")
            startActivity(openURL)
        }
        btnmedicinenet.setOnClickListener {
            val openURL = Intent(Intent.ACTION_VIEW)
            openURL.data = Uri.parse("https://www.medicinenet.com/health/definition.htm")
            startActivity(openURL)
        }
    }
}