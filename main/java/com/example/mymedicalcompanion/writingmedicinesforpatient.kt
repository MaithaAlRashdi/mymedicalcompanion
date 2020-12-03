package com.example.mymedicalcompanion

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mymedicalcompanion.options.MessageBox
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_writingmedicinesforpatient.*

class writingmedicinesforpatient : AppCompatActivity() {
    lateinit var uuid: String
    val message = MessageBox()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_writingmedicinesforpatient)
        val email = intent.getStringExtra("email")
        uuid = intent.getStringExtra("uuid").toString()
        // showtests()
        val referenceMeds = FirebaseDatabase.getInstance().getReference("patients")
        val postListenerMeds = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                if (dataSnapshot.hasChild(FirebaseAuth.getInstance().getCurrentUser()!!.getUid() + "")) {
                    var medicine: String =
                        dataSnapshot.child(FirebaseAuth.getInstance().getCurrentUser()!!.getUid())
                            .child("medicines").getValue().toString()
                    etmedicines.setText(medicine)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        }
        referenceMeds.addValueEventListener(postListenerMeds)
        val referenceTest = FirebaseDatabase.getInstance().getReference("patients")
        val postListenerTest = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                if (dataSnapshot.hasChild(FirebaseAuth.getInstance().getCurrentUser()!!.getUid() + "")) {
                    val test: String =
                        dataSnapshot.child(FirebaseAuth.getInstance().getCurrentUser()!!.getUid())
                            .child("tests").getValue().toString()
                    ettests.setText(test + "")

                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        }
        referenceTest.addValueEventListener(postListenerTest)
        val reference = FirebaseDatabase.getInstance().getReference("patients")
        reference.orderByChild("name").equalTo(email)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (datas in dataSnapshot.children) {
                        uuid = datas.child("useruuid").value.toString()
                        Toast.makeText(
                            this@writingmedicinesforpatient,
                            "Id is=$uuid",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {}
            })

        tvemailofselectedpatient.setText(email)
        btnrecommend.setOnClickListener {
            val recommendedmeds = etmedicines.text.toString()
            val recommendedtestspatient = ettests.text.toString()


            val dbrefmeds = FirebaseDatabase.getInstance().getReference("patients").child(uuid)
                .child("medicines")
            dbrefmeds.setValue(recommendedmeds)
            val dbreftests =
                FirebaseDatabase.getInstance().getReference("patients").child(uuid).child("tests")
            dbreftests.setValue(recommendedtestspatient)

            message.show("Medication and Tests are recommended to user $email", "Message", this)
        }
    }

    private fun showMeds() {
        val reference = FirebaseDatabase.getInstance().getReference("patients")
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                if (dataSnapshot.hasChild(FirebaseAuth.getInstance().getCurrentUser()!!.getUid() + "")) {
                    var medicine: String =
                        dataSnapshot.child(FirebaseAuth.getInstance().getCurrentUser()!!.getUid())
                            .child("medicines").getValue().toString()
                    etmedicines.setText(medicine)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        }
        reference.addValueEventListener(postListener)
    }

    private fun showtests() {
        val referenceTest = FirebaseDatabase.getInstance().getReference("patients")
        val postListenerTest = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                if (dataSnapshot.hasChild(FirebaseAuth.getInstance().getCurrentUser()!!.getUid() + "")) {
                    val test: String =
                        dataSnapshot.child(FirebaseAuth.getInstance().getCurrentUser()!!.getUid())
                            .child("tests").getValue().toString()
                    if (test != null) {
                        ettests.setText(test + "")
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        }
        referenceTest.addValueEventListener(postListenerTest)
    }
}