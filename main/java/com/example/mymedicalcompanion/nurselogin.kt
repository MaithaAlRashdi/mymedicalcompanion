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
import kotlinx.android.synthetic.main.activity_nurselogin.*

class nurselogin : AppCompatActivity() {
    val message = MessageBox()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nurselogin)
        supportActionBar?.title = "Nurse LogIn"
        val firebaseAuth = FirebaseAuth.getInstance()
        btnnursesignup1.setOnClickListener {
            startActivity(Intent(this, nursesignup::class.java))
        }
        btnnurselogin1.setOnClickListener {
            if (this.emailvalidation() && this.passwordvalidation()) {
                val email = tilnurseemail4.text.toString().trim()
                val password = tilnursepassword4.text.toString().trim()
                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this,
                    OnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Logged in", Toast.LENGTH_SHORT)
                                .show()
                            startActivity(Intent(this, nursescreen::class.java))
                        } else {
                            message.show("Incorrect password or email","Failed",this)

                        }
                    })

            }else{
                Toast.makeText(this, "incorrect password or email", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        btnnurseforgotpassword1.setOnClickListener {
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
        val email: String = tilnurseemail4.text.toString().trim()
        if (TextUtils.isEmpty(email)) {
            tilnurseemail4.error = "you cant leave this empty"
            return false
        } else {
            return true
        }
    }

    fun passwordvalidation(): Boolean {
        val password: String = tilnursepassword4.text.toString().trim()
        if (TextUtils.isEmpty(password)) {
            tilnursepassword4.error = "you cant leave this empty"
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