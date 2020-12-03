package com.example.mymedicalcompanion

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_retrevingmeds.*


class
retrevingmeds : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retrevingmeds)
        val uuid = FirebaseAuth.getInstance().getCurrentUser()!!.getUid()
        //val dbref = FirebaseDatabase.getInstance().getReference("patients").child(uuid)
        val reference = FirebaseDatabase.getInstance().getReference("patients")

        /* reference.orderByChild("useremail").equalTo(FirebaseAuth.getInstance().currentUser!!.email)
             .addListenerForSingleValueEvent(object : ValueEventListener {
                 override fun onDataChange(dataSnapshot: DataSnapshot) {
                     for (datas in dataSnapshot.children) {
                         val meds = datas.child("medicines").value.toString()
                         patientmeds.setText(meds)
                     }
                 }

                 override fun onCancelled(databaseError: DatabaseError) {}
             })*/
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                if (dataSnapshot.hasChild(FirebaseAuth.getInstance().getCurrentUser()!!.getUid() + "")) {
                      var medicine:String= dataSnapshot.child(FirebaseAuth.getInstance().getCurrentUser()!!.getUid()).child("medicines").getValue().toString()
                    patientmeds.setText(medicine)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        }
        reference.addValueEventListener(postListener)
    }
}