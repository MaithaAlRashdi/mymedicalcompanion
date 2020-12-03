package com.example.mymedicalcompanion

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import cc.cloudist.acplibrary.ACProgressConstant
import cc.cloudist.acplibrary.ACProgressFlower
import com.example.mymedicalcompanion.options.MessageBox
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_doctorsignup.*
import kotlinx.android.synthetic.main.activity_nursesignup.*
import java.util.*

class nursesignup : AppCompatActivity() {
    val message = MessageBox()
    val gender = arrayOf<String>("Male", "Female")
    var docGender: String = gender[0]
    var dob: String = "01/01/1998"
    var nic: String = ""

    lateinit var nicNumber: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nursesignup)
        supportActionBar?.title = "Nurse SignUp"
        /*btnnurselogin2.setOnClickListener {
            startActivity(Intent(this, nurselogin::class.java))
        }*/
        btnnursesignup2.setOnClickListener {
            signupusers()
        }
        nicNumber = findViewById(R.id.tvNurseId)
        nicNumber.addTextChangedListener(textWatcher)
        val spDob = findViewById<Spinner>(R.id.spDOBnur)
        if (spDob != null) {
            val adapterDob = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, gender
            )
            spDob.adapter = adapterDob

            spDob.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {
                    docGender = gender[position]
                    Toast.makeText(
                        applicationContext,
                        "" + gender[position], Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        }
        val btnDob = findViewById<Button>(R.id.btnDOBnur)
        // val textView     = findViewById<TextView>(R.id.dateTv)

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)+1
        val day = c.get(Calendar.DAY_OF_MONTH)

        btnDob.setOnClickListener {

            val dpd = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    // Display Selected date in TextView
                    btnDob.setText("" + dayOfMonth + "/" + monthOfYear + "/" + year)
                    dob="" + dayOfMonth + "/" + monthOfYear + "/" + year

                },
                year,
                month,
                day
            )
            dpd.show()

        }


    }
    fun nicValidation(): Boolean {
        val nicCard: String = tvNurseId.text.toString().trim()
        if (TextUtils.isEmpty(nicCard)) {
            tvNurseId.error = "you cant leave this empty"
            return false
        } else {
            // tilpatientemail2.isErrorEnabled = false
            return true
        }
    }
    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            if (count == 3 || count == 8 || count == 16) {

                nicNumber.append("-")
            }
            if (count == 18) {
                nic = nicNumber.text.toString()
            }
        }

    }


    fun signupusers() {
        val email: String = tilnurseemail3.text.toString().trim()
        val password: String = tilnursepassword3.text.toString().trim()
        val mAuth = FirebaseAuth.getInstance()
        btnnursesignup2.setOnClickListener {

            if (emailvalidation() && passwordvalidation() && nameValidation()) {
                val dialog = ACProgressFlower.Builder(this).direction(
                    ACProgressConstant.DIRECT_CLOCKWISE
                )
                    .themeColor(Color.WHITE)
                    .text("Registering user please wait..")
                    .textSize(15)
                    .isTextExpandWidth(true)
                    .fadeColor(Color.DKGRAY).build()
                dialog.show()
                mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, OnCompleteListener<AuthResult> { task ->
                        if (task.isSuccessful) {
                            sendinginfotodatabase()
                            dialog.dismiss()
                            Toast.makeText(this, "success", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, nursescreen::class.java))

                        } else {
                            message.show("Sign Up failed try different Email", "Failed", this)

                        }
                    })
            }
        }
    }

    fun emailvalidation(): Boolean {
        val email: String = tilnurseemail3.text.toString().trim()
        if (TextUtils.isEmpty(email)) {
            tilnurseemail2.error = "you cant leave this empty"
            return false

        } else {
            tilnurseemail2.isErrorEnabled = false
            return true
        }
    }

    fun nameValidation(): Boolean {
        val name: String = tvNurseName.text.toString().trim()
        if (TextUtils.isEmpty(name)) {
            tvNurseName.error = "you cant leave this empty"
            return false

        } else {
            return true
        }
    }

    fun passwordvalidation(): Boolean {
        val password: String = tilnursepassword3.text.toString().trim()
        val confirmpassword = tilnurseconfirmpassword3.text.toString().trim()
        if (TextUtils.isEmpty(password)) {
            tilnursepassword2.error = "you cant leave this empty"
            return false
        } else if (password != confirmpassword) {
            tilnurseconfirmpassword2.error = "Passwords are not same"
            return false
        }
        //else if (password.length <= 8) {
        //tilpassword2.error = "password can't be less then 8 characters"
        // false }
        else {
            tilnursepassword2.isErrorEnabled = false
            tilnurseconfirmpassword2.isErrorEnabled = false
            if (password.length<6 && confirmpassword.length<6) {
                tilnursepassword3.error = "must be atleast 6 characters"
                return false
            }else{
                return true
            }        }
    }

    fun sendinginfotodatabase() {

        val nicCard: String = tvNurseId.text.toString().trim()
        val name: String = tvNurseName.text.toString().trim()
        val password: String = tilnursepassword3.text.toString().trim()
        val email: String = tilnurseemail3.text.toString().trim()
        val id = FirebaseAuth.getInstance().getCurrentUser()!!.getUid()
        val databaseref = FirebaseDatabase.getInstance().getReference("Nurse").child(id)
        val userprofileinformation = nurseinfo(email, password, id, name,dob,docGender,nicCard)
        databaseref.setValue(userprofileinformation)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        this.finish()
        startActivity(Intent(this, MainActivity::class.java))
    }
}



