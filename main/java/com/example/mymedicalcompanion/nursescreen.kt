package com.example.mymedicalcompanion

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_nursescreen.*

class nursescreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_nursescreen)

        supportActionBar?.title = "Nurse Screen"
        btnnursecontactdoctor.setOnClickListener {
            startActivity(Intent(this, doctorspecialization::class.java))
        }
        btnpatientmedicinesnurse.setOnClickListener {
            startActivity(Intent(this, retrevingpatientsfornurse::class.java))
        }
        btnSignOutNurse.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this, nurselogin::class.java))
        }


    }

    private fun checkSession() {
        intent = Intent(this, nurselogin::class.java)
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (FirebaseAuth.getInstance().currentUser != null) {


                    if (!dataSnapshot.hasChild(FirebaseAuth.getInstance().getCurrentUser()!!.getUid() + "")) {
                        //FirebaseAuth.getInstance().signOut()
                        finish()
                        startActivity(intent)
                    } else {
                    }
                } else {
                    startActivity(intent)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        }
        FirebaseDatabase.getInstance().getReference().child("Nurse")
            .addValueEventListener(postListener)
    }

    override fun onStart() {
        super.onStart()


    }
}