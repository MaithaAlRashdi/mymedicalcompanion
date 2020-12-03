package com.example.mymedicalcompanion

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mymedicalcompanion.options.recommendTest
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_doctoractivity.*

class doctoractivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctoractivity)

        btnshowpatients.setOnClickListener {
            startActivity(Intent(this, retrevingpatients::class.java))
        }
        btnSignOutDoctor.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
           startActivity(Intent(this, doctorlogin::class.java))
        }
        /*btnRecommendTestDoc.setOnClickListener{
           // startActivity(Intent(this, recommendTest::class.java))
        }*/
       /* btnmedicationDoc.setOnClickListener{
            startActivity(Intent(this, retrevingpatients::class.java))
        }*/

    }

    private fun checkSession() {
        intent = Intent(this, doctorlogin::class.java);
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                if (FirebaseAuth.getInstance().currentUser != null) {


                    if (!dataSnapshot.hasChild(FirebaseAuth.getInstance().getCurrentUser()!!.getUid() + "")) {
                        //FirebaseAuth.getInstance().signOut()
                        finish()
                        startActivity(intent)
                    }else{
                                    }
                } else {
                    startActivity(intent)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        }
        FirebaseDatabase.getInstance().getReference().child("AllDoctors")
            .addValueEventListener(postListener)

    }

    override fun onStart() {
        super.onStart()
      //  var flag: Boolean = true

    }
}
