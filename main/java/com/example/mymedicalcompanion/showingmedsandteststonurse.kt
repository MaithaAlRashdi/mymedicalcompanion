package com.example.mymedicalcompanion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.mymedicalcompanion.options.MessageBox
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_retrevingmeds.*
import kotlinx.android.synthetic.main.activity_retrevingtests.*
import kotlinx.android.synthetic.main.activity_showingmedsandteststonurse.*
import kotlinx.android.synthetic.main.activity_writingmedicinesforpatient.*

class showingmedsandteststonurse : AppCompatActivity() {
    val message = MessageBox()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_showingmedsandteststonurse)
        val uuid= FirebaseAuth.getInstance().getCurrentUser()!!.getUid()
        val email=intent.getStringExtra("email")
        var patientId=intent.getStringExtra("uuid").toString()
       // Toast.makeText(this@showingmedsandteststonurse,"email=$email ",Toast.LENGTH_SHORT).show()
        //val dbref = FirebaseDatabase.getInstance().getReference("patients").child(uuid)
        val reference = FirebaseDatabase.getInstance().getReference("patients")

        reference.orderByChild("name").equalTo(email)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (datas in dataSnapshot.children) {
                        val meds = datas.child("medicines").value.toString()
                        val tests=datas.child("tests").value.toString()
                        val reports=datas.child("reports").value.toString()
                        val id=datas.child("useruuid").value.toString()
                        patientmedsfornurse.setText(meds)
                        patienttestsfornurse.setText(tests)
                        patientReportsNurseView.setText(reports)
                        patientId=id
                        Toast.makeText(this@showingmedsandteststonurse,patientId+" ",Toast.LENGTH_SHORT).show()


                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {}
            })

      /*  val referenceReport = FirebaseDatabase.getInstance().getReference("patients")
        val postListenerMeds = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                if (dataSnapshot.hasChild(FirebaseAuth.getInstance().getCurrentUser()!!.getUid() + "")) {
                    var report: String =
                        dataSnapshot.child(FirebaseAuth.getInstance().getCurrentUser()!!.getUid())
                            .child("reports").getValue().toString()
                    patientReportsNurseView.setText(report)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        }
        referenceReport.addValueEventListener(postListenerMeds)*/
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                if (dataSnapshot.hasChild(FirebaseAuth.getInstance().getCurrentUser()!!.getUid() + "")) {
                    val test:String= dataSnapshot.child(FirebaseAuth.getInstance().getCurrentUser()!!.getUid()).child("tests").getValue().toString()
                    if(test!=null){
                        patienttests.setText(test+"")
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        }
        reference.addValueEventListener(postListener)

        btnCreateReport.setOnClickListener {
            val createReport = patientReportsNurseView.text.toString()



            val dbrefmeds = FirebaseDatabase.getInstance().getReference("patients").child(patientId)
                .child("reports")
            dbrefmeds.setValue(createReport)


            message.show("Report is Created for user $email", "Message", this)
        }
    }
}