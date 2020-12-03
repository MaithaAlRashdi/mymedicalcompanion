package com.example.mymedicalcompanion

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_retrevingpatients.*


class retrevingpatients : AppCompatActivity() {
    val patientarraylist = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        val patientarrayadapter =
            ArrayAdapter<String>(
                this,
                R.layout.patientslistadapter,
                R.id.tvemailDoctor,
                patientarraylist
            )


        //var uuid:String=""
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retrevingpatients)


        val dbpatientreference = FirebaseDatabase.getInstance().getReference("patients")
        val listView: ListView = findViewById(R.id.lvpatientsdetails)
        listView.setAdapter(patientarrayadapter)

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (DataSnapshot in dataSnapshot.children) {
                    val userinfo = DataSnapshot.getValue(patients::class.java)
                    val name = userinfo?.name.toString()

                    patientarraylist.add(name)

                    listView.setOnItemClickListener { parent, view, position, id ->
                        val intent =
                            Intent(this@retrevingpatients, writingmedicinesforpatient::class.java);
                        val email = lvpatientsdetails.getItemAtPosition(position).toString()
                        val reference = FirebaseDatabase.getInstance().getReference("patients")
                        reference.orderByChild("useremail").equalTo(email)
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
                        intent.putExtra("email", email)
                        startActivity(intent);
                    }
                }
                lvpatientsdetails.adapter = patientarrayadapter

                /*listView.setOnItemClickListener { parent, view, position, id ->
                    val intent = Intent(this@retrevingpatients, writingmedicinesforpatient::class.java);
                    val email = lvpatientsdetails.getItemAtPosition(position).toString()
                    intent.putExtra("email",email)
                    intent.putExtra("uuid",uuid)
                    startActivity(intent);
                }*/


            }


            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@retrevingpatients, "cancelled", Toast.LENGTH_SHORT).show()
            }
        }
        dbpatientreference.addValueEventListener(postListener)
        tvSearchBar.addTextChangedListener(textWatcher)

    }

    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            filter(s.toString())
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {


        }

    }

    private fun filter(input: String) {
        var filteredList = ArrayList<String>()
        for (item in patientarraylist) {

        }
        var i = 0
        for (item in patientarraylist) {
            if (item.toLowerCase().contains(input.toLowerCase())) {
                filteredList.add(item)
                i++
            }
        }
        val patientFilteredarrayadapter =
            ArrayAdapter<String>(
                this,
                R.layout.patientslistadapter,
                R.id.tvemailDoctor,
                filteredList
            )
        lvpatientsdetails.adapter=patientFilteredarrayadapter

    }

}


