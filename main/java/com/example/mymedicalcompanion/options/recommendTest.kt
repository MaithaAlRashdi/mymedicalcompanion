package com.example.mymedicalcompanion.options

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.mymedicalcompanion.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_recommend_test.*
import kotlinx.android.synthetic.main.activity_writingmedicinesforpatient.*

class recommendTest : AppCompatActivity() {
    lateinit var uuid: String
    val message=MessageBox()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recommend_test)
        val email = intent.getStringExtra("email")

        val reference = FirebaseDatabase.getInstance().getReference("patients")
        reference.orderByChild("useremail").equalTo(email)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (datas in dataSnapshot.children) {
                        uuid = datas.child("useruuid").value.toString()
                        Toast.makeText(
                            this@recommendTest,
                            "Id is=$uuid",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {}
            })

        tvpateintEmailRecom.setText(email)

        val referenceTest = FirebaseDatabase.getInstance().getReference("patients")
        val postListenerTest = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                if (dataSnapshot.hasChild(FirebaseAuth.getInstance().getCurrentUser()!!.getUid() + "")) {
                    val test:String= dataSnapshot.child(FirebaseAuth.getInstance().getCurrentUser()!!.getUid()).child("tests").getValue().toString()
                    ettestsRecom.setText(test+"")

                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        }
        referenceTest.addValueEventListener(postListenerTest)

        btnrecommendRecom.setOnClickListener {
            val recommendedtestspatient = ettestsRecom.text.toString()
            val dbreftests =
                FirebaseDatabase.getInstance().getReference("patients").child(uuid).child("tests")
            dbreftests.setValue(recommendedtestspatient)

            message.show("Tests are recommended to user $email","Message",this)
        }
    }
}
