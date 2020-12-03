package com.example.mymedicalcompanion

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.mymedicalcompanion.options.MessageBox
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_doctorlogin.*
import kotlinx.android.synthetic.main.activity_login.*

class doctorlogin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctorlogin)
        supportActionBar?.title = "Doctor LogIn"
        val firebaseAuth = FirebaseAuth.getInstance()
        val message=MessageBox()
        btndoclogin1.setOnClickListener {
            if (emailvalidation() && passwordvalidation()) {
                val email = tildocemail4.text.toString().trim()
                val password = tildocpassword4.text.toString().trim()
                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this,
                    OnCompleteListener { task ->
                        if (task.isSuccessful) {

                            startActivity(Intent(this, doctoractivity::class.java))

                        } else {
                            message.show("Incorrect password or email", "Failed", this)

                        }
                    })
            }
        }
        btndocsignup1.setOnClickListener {
            startActivity(Intent(this, doctorsignup::class.java))
        }
        btndocforgotpassword1.setOnClickListener {
            if (emailvalidation()) {
                var mAuth1 = FirebaseAuth.getInstance()
                val email1: String = tilemail4.text.toString()
                mAuth1.sendPasswordResetEmail(email1).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "EMAIL SENT CHECK YOUR EMAIL", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        Toast.makeText(this, "EMAIL NOT SENT", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    fun emailvalidation(): Boolean {
        val email: String = tildocemail4.text.toString().trim()
        if (TextUtils.isEmpty(email)) {
            tildocemail4.error = "you cant leave this empty"
            return false
        } else {
            return true
        }


    }

    fun passwordvalidation(): Boolean {
        val password: String = tildocpassword4.text.toString().trim()
        if (TextUtils.isEmpty(password)) {
            tildocpassword4.error = "you cant leave this empty"
            return false
        } else {
            return true
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        this.finish()
        startActivity(Intent(this, MainActivity::class.java))
    }


}