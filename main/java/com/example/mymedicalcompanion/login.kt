package com.example.mymedicalcompanion

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mymedicalcompanion.options.MessageBox
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*


class login : AppCompatActivity() {
    val message=MessageBox()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.title = "Patient LogIn"
        val firebaseAuth = FirebaseAuth.getInstance()
        btnlogin1.setOnClickListener {
            if (emailvalidation()) {
                if (passwordvalidation()) {
                    val email = tilemail4.text.toString().trim()
                    val password = tilpassword4.text.toString().trim()
                    firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, OnCompleteListener { task ->
                            if (task.isSuccessful) {
                                startActivity(Intent(this, patientscreen::class.java))
                            } else {

                                message.show("Incorrect password or email","Failed",this)
                            }
                        })
                }
            }
        }

        btnsignuppatientlogin.setOnClickListener{

            startActivity(Intent(this, patientsignup::class.java))
        }
        btnforgotpassword1.setOnClickListener {
            if (emailvalidation()) {

                var mAuth1 = FirebaseAuth.getInstance()
                val email1: String = tilemail4.text.toString()
                mAuth1.sendPasswordResetEmail(email1).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        message.show("EMAIL SENT CHECK YOUR EMAIL","Success",this)
                    } else {
                        message.show("Issue In Email","Failed",this)
                    }
                }
            }
        }

    }

    fun emailvalidation(): Boolean {
        val email: String = tilemail4.text.toString()
        if (TextUtils.isEmpty(email)) {
            tilemail1.error = "you cant leave this empty"
            return false
        } else {
            return true
        }
    }

    fun passwordvalidation(): Boolean {
        val password: String = tilpassword4.text.toString().trim()
        if (TextUtils.isEmpty(password)) {
            tilpassword4.error = "you cant leave this empty"
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