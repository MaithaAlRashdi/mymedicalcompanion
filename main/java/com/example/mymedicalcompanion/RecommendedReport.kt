package com.example.mymedicalcompanion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_recommended_report.*
import kotlinx.android.synthetic.main.activity_retrevingtests.*

class RecommendedReport : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recommended_report)
        val referenceReport = FirebaseDatabase.getInstance().getReference("patients")
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
        referenceReport.addValueEventListener(postListenerMeds)
    }
}
