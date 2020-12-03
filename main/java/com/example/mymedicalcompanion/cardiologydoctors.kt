package com.example.mymedicalcompanion

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_cardiologydoctors.*
import kotlinx.android.synthetic.main.activity_retrevingpatients.*


class cardiologydoctors : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        val usersarraylist=ArrayList<String> ()
        val usersarrayadapter=
            ArrayAdapter<String>(this, R.layout.bookinfoadapter, R.id.booksrow, usersarraylist)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cardiologydoctors)
        val doctortype:String=intent.getStringExtra("doctype").toString()
        supportActionBar?.title=doctortype
        val dbreference= FirebaseDatabase.getInstance().getReference("Doctor").child(doctortype)

            val listView: ListView = findViewById(R.id.lvusersdetails)
            listView.setAdapter(usersarrayadapter)
            val postListener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (DataSnapshot in dataSnapshot.children)
                    {
                        val userinfo=DataSnapshot.getValue(User::class.java)
                        usersarraylist.add(  userinfo?.name.toString())
                      //  usersarraylist.add(  userinfo?.userContact.toString())

                        listView.setOnItemClickListener { parent, view, position, id ->
                            val pm = packageManager
                            val uri: Uri = Uri.parse(userinfo?.userContact.toString())
                            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + Uri.encode(userinfo?.userContact.toString().trim())))
                            startActivity(intent)
                            Toast.makeText(applicationContext,userinfo?.userContact.toString().trim()+"",Toast.LENGTH_LONG).show()
                           // val intent = Intent(
                               // this@cardiologydoctors,
                              //  writingmedicinesforpatient::class.java
                          //  );
                           /* val email = lvpatientsdetails.getItemAtPosition(position).toString()
                            intent?.putExtra("email", email)
                            startActivity(intent);*/
                        }

                    }
                    lvusersdetails.adapter=usersarrayadapter


                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Toast.makeText(this@cardiologydoctors, "cancelled", Toast.LENGTH_SHORT).show()
                }
            }
            dbreference.addValueEventListener(postListener)
        }
    }

