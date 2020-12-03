package com.example.mymedicalcompanion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_retrevingmeds.*
import kotlinx.android.synthetic.main.activity_retrevingtests.*

class retrevingtests : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retrevingtests)
        val uuid= FirebaseAuth.getInstance().getCurrentUser()!!.getUid()
        //val dbref = FirebaseDatabase.getInstance().getReference("patients").child(uuid)
        val reference = FirebaseDatabase.getInstance().getReference("patients")

       /* reference.orderByChild("useremail").equalTo(FirebaseAuth.getInstance().currentUser!!.email)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (datas in dataSnapshot.children) {
                        val tests = datas.child("tests").value.toString()
                        patienttests.setText(tests)
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {}
            })*/
       /* val referenceReport = FirebaseDatabase.getInstance().getReference("patients")
        val postListenerMeds = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                if (dataSnapshot.hasChild(FirebaseAuth.getInstance().getCurrentUser()!!.getUid() + "")) {
                    var report: String =
                        dataSnapshot.child(FirebaseAuth.getInstance().getCurrentUser()!!.getUid())
                            .child("reports").getValue().toString()
                    patientReports.setText(report)
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
    }
}
