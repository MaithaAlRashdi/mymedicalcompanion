package com.example.mymedicalcompanion

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_patientscreen.*

class patientscreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patientscreen)

        supportActionBar?.title = "Patient"

        btncontactdoctor.setOnClickListener {
            startActivity(Intent(this, doctorspecialization::class.java))
        }
        btnmedication.setOnClickListener {
            startActivity(Intent(this, retrevingmeds::class.java))
        }
        btnrecomendedtests.setOnClickListener {
            startActivity(Intent(this, retrevingtests::class.java))
        }
        btnReportsGenerated.setOnClickListener {
            startActivity(Intent(this, RecommendedReport::class.java))
        }
        btnusefullinks.setOnClickListener {
            startActivity(Intent(this, usefullinks::class.java))
        }
        btnemergency.setOnClickListener {
            startActivity(Intent(this, emergency::class.java))
        }
        btnSignOutPatient.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this, login::class.java))
        }

    }

    override fun onStart() {
        super.onStart()
       // var flag: Boolean = true

    }

    private fun checkSession() {
        intent = Intent(this, login::class.java)
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                if (FirebaseAuth.getInstance().currentUser != null) {

                    /*  flag =
                          dataSnapshot.hasChild(FirebaseAuth.getInstance().getCurrentUser()!!.getUid() + "")
                    */ /*  Toast.makeText(
                         this@patientscreen,
                         FirebaseAuth.getInstance().getCurrentUser()!!.getUid() + "" + flag,
                         Toast.LENGTH_SHORT
                     ).show()*/
                    if (!dataSnapshot.hasChild(FirebaseAuth.getInstance().getCurrentUser()!!.getUid() + "")) {
                        // FirebaseAuth.getInstance().signOut()
                        finish()
                        startActivity(intent)
                    }else{
                          }
                }else{
                    startActivity(intent)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        }
        FirebaseDatabase.getInstance().getReference().child("patients")
            .addValueEventListener(postListener)

    }
}