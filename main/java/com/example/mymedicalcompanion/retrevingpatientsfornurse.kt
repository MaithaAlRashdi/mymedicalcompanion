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
import kotlinx.android.synthetic.main.activity_retrevingpatients.*
import kotlinx.android.synthetic.main.activity_retrevingpatientsfornurse.*

class retrevingpatientsfornurse : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val patientarraylist=ArrayList<String> ()
        val patientarrayadapter=
            ArrayAdapter<String>(this, R.layout.patientslistadapter, R.id.tvemailDoctor, patientarraylist)
        var uuid:String
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retrevingpatientsfornurse)


        val dbpatientreference= FirebaseDatabase.getInstance().getReference("patients")
        val listView1: ListView = findViewById(R.id.lvpatientsdetailsfornurse)
        listView1.setAdapter(patientarrayadapter)

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (DataSnapshot in dataSnapshot.children) {
                    val userinfo = DataSnapshot.getValue(patients::class.java)
                    patientarraylist.add(userinfo?.name.toString())

                    listView1.setOnItemClickListener { parent, view, position, id ->
                        val intent = Intent(this@retrevingpatientsfornurse, showingmedsandteststonurse::class.java);
                        val email = lvpatientsdetailsfornurse.getItemAtPosition(position).toString()
                        val reference = FirebaseDatabase.getInstance().getReference("patients")
                        reference.orderByChild("name").equalTo(email)
                            .addListenerForSingleValueEvent(object : ValueEventListener {
                                override fun onDataChange(dataSnapshot: DataSnapshot) {
                                    for (datas in dataSnapshot.children) {
                                        val uuid = datas.child("useruuid").value.toString()
                                        intent.putExtra("uuid", uuid)
                                        Toast.makeText(
                                            applicationContext,
                                            uuid + "",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                }

                                override fun onCancelled(databaseError: DatabaseError) {}
                            })

                        intent.putExtra("email",email)
                        startActivity(intent);
                    }
                }
                lvpatientsdetailsfornurse.adapter = patientarrayadapter
                /*listView.setOnItemClickListener { parent, view, position, id ->
                    val intent = Intent(this@retrevingpatients, writingmedicinesforpatient::class.java);
                    val email = lvpatientsdetails.getItemAtPosition(position).toString()
                    intent.putExtra("email",email)
                    intent.putExtra("uuid",uuid)
                    startActivity(intent);
                }*/


            }


            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@retrevingpatientsfornurse, "cancelled", Toast.LENGTH_SHORT).show()
            }
        }
        dbpatientreference.addValueEventListener(postListener)
    }
}