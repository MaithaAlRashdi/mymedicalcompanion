package com.example.mymedicalcompanion

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_doctoractivity.*

class doctorsactivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctoractivity)
        btnshowpatients.setOnClickListener {
            startActivity(Intent(this,retrevingpatients::class.java))
        }
        btnSignOutDoctor.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this,doctorlogin::class.java))
        }
    }
  /*  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.optionchoose,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId)
        {
            R.id.btnsignout->{
                    FirebaseAuth.getInstance().signOut()
                    val intent= Intent(this,doctorsignup::class.java )
                    intent.flags= Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }

            }


        return super.onOptionsItemSelected(item)
    }*/

    override fun onStart() {
        super.onStart()
        if (FirebaseAuth.getInstance().currentUser==null){
            startActivity(Intent(this,doctorlogin::class.java))
        }
    }
}