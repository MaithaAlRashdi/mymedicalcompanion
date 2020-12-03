package com.example.mymedicalcompanion

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        loadLocate()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        btnsignupdoctor.setOnClickListener {
            loadingPanel.setVisibility(View.VISIBLE)
           // mainLyout.setVisibility(View.GONE)

            intent = Intent(this, doctorlogin::class.java)
            val doctorIntent = Intent(this,doctoractivity::class.java)
            val postListener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    // Get Post object and use the values to update the UI
                    if (FirebaseAuth.getInstance().currentUser != null) {


                        if (!dataSnapshot.hasChild(FirebaseAuth.getInstance().getCurrentUser()!!.getUid() + "")) {
                            //FirebaseAuth.getInstance().signOut()
                            //finish()
                            startActivity(intent)
                            loadingPanel.setVisibility(View.GONE)
                            //mainLyout.setVisibility(View.VISIBLE)
                        }else{
                            startActivity(doctorIntent)
                            loadingPanel.setVisibility(View.GONE)
                           // mainLyout.setVisibility(View.VISIBLE)
                        }
                    } else {
                        startActivity(intent)
                        loadingPanel.setVisibility(View.GONE)
                       // mainLyout.setVisibility(View.VISIBLE)
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                }
            }
            FirebaseDatabase.getInstance().getReference().child("AllDoctors")
                .addValueEventListener(postListener)


        }
        btnsignuppatient.setOnClickListener {
            loadingPanel.setVisibility(View.VISIBLE)
           // mainLyout.setVisibility(View.GONE)
            intent = Intent(this, login::class.java)
            val patientintent= Intent(this,patientscreen::class.java)
            val postListener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    // Get Post object and use the values to update the UI
                    if (FirebaseAuth.getInstance().currentUser != null) {


                        if (!dataSnapshot.hasChild(FirebaseAuth.getInstance().getCurrentUser()!!.getUid() + "")) {
                            //FirebaseAuth.getInstance().signOut()
                            //finish()
                            startActivity(intent)
                            loadingPanel.setVisibility(View.GONE)
                            //mainLyout.setVisibility(View.VISIBLE)
                        }else{
                            startActivity(patientintent)
                            loadingPanel.setVisibility(View.GONE)
                           // mainLyout.setVisibility(View.VISIBLE)
                        }
                    } else {
                        startActivity(intent)
                        loadingPanel.setVisibility(View.GONE)
                        //mainLyout.setVisibility(View.VISIBLE)
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                }
            }
            FirebaseDatabase.getInstance().getReference().child("patients")
                .addValueEventListener(postListener)


        }
        btnsignupnurse.setOnClickListener {
            loadingPanel.setVisibility(View.VISIBLE)
            //mainLyout.setVisibility(View.GONE)
            intent = Intent(this, nurselogin::class.java)
            val nurseintent= Intent(this,nursescreen::class.java)
            val postListener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    // Get Post object and use the values to update the UI
                    if (FirebaseAuth.getInstance().currentUser != null) {


                        if (!dataSnapshot.hasChild(FirebaseAuth.getInstance().getCurrentUser()!!.getUid() + "")) {
                            //FirebaseAuth.getInstance().signOut()
                            //finish()
                            startActivity(intent)
                            loadingPanel.setVisibility(View.GONE)
                           // mainLyout.setVisibility(View.VISIBLE)
                        }else{
                            startActivity(nurseintent)
                            loadingPanel.setVisibility(View.GONE)
                           // mainLyout.setVisibility(View.VISIBLE)
                        }
                    } else {
                        startActivity(intent)
                        loadingPanel.setVisibility(View.GONE)
                       // mainLyout.setVisibility(View.VISIBLE)
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                }
            }
            FirebaseDatabase.getInstance().getReference().child("Nurse")
                .addValueEventListener(postListener)

           // startActivity(Intent(this,nursescreen::class.java))
        }

        btnEnglish.setOnClickListener{
            setLocate("en")
            recreate()
            btnEnglish.setBackgroundColor(R.color.colorPrimaryDark)
            btnEnglish.setTextColor(R.color.colorWhite)
            btnArabic.setBackgroundColor(R.color.colorWhite)
            btnArabic.setTextColor(R.color.colorPrimaryDark)
        }
        btnArabic.setOnClickListener{
            setLocate("ar")
            recreate()
            btnEnglish.setBackgroundColor(R.color.colorWhite)
            btnEnglish.setTextColor(R.color.colorPrimaryDark)
            btnArabic.setBackgroundColor(R.color.colorPrimaryDark)
            btnArabic.setTextColor(R.color.colorWhite)
        }
    }
    private fun setLocate(Lang: String) {

        val locale = Locale(Lang)

        Locale.setDefault(locale)

        val config = Configuration()

        config.locale = locale
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)

        val editor = getSharedPreferences("Settings", Context.MODE_PRIVATE).edit()
        editor.putString("My_Lang", Lang)
        editor.apply()
    }

    private fun loadLocate() {
        val sharedPreferences = getSharedPreferences("Settings", Activity.MODE_PRIVATE)
        val language = sharedPreferences.getString("My_Lang", "en").toString()
        setLocate(language)
    }

}