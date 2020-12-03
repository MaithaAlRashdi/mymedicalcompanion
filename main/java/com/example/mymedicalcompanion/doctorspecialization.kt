package com.example.mymedicalcompanion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_cardiologydoctors.*
import kotlinx.android.synthetic.main.activity_doctorspecialization.*

class doctorspecialization : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctorspecialization)
        supportActionBar?.title = "Choose Specialization"
        btncardiology.setOnClickListener {
            val intent=Intent(this,cardiologydoctors::class.java)
            intent.putExtra("doctype","Cardiology")
            startActivity(intent)
        }
        btndermatology.setOnClickListener {
            val intent=Intent(this,cardiologydoctors::class.java)
            intent.putExtra("doctype","Dermatology")
            startActivity(intent)
        }
        btnfamilydoc.setOnClickListener {
            val intent=Intent(this,cardiologydoctors::class.java)
            intent.putExtra("doctype","FamilyDoctor")
            startActivity(intent)
        }
        btnNeurodoc.setOnClickListener {
            val intent=Intent(this,cardiologydoctors::class.java)
            intent.putExtra("doctype","Neurology")
            startActivity(intent)
        }
        btnPsycoDoc.setOnClickListener {
            val intent=Intent(this,cardiologydoctors::class.java)
            intent.putExtra("doctype","Psychiatry")
            startActivity(intent)
        }

    }
    }
