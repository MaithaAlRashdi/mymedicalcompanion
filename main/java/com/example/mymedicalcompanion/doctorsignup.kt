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
import kotlinx.android.synthetic.main.activity_patientsignup.*
import java.util.*

class doctorsignup : AppCompatActivity() {
    val specializations =
        arrayOf<String>("Cardiology", "Dermatology", "FamilyDoctor", "Neurology", "Psychiatry")
    val gender = arrayOf<String>("Male", "Female")
    // val specializations = resources.getStringArray(R.array.Spcializations)
    var tilspecialization3: String = specializations[0]
    var docGender: String = gender[0]
    var dob: String = "01/01/1998"
    var nic: String = ""

    lateinit var nicNumber: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.title = "Doctor SignUp"
        setContentView(R.layout.activity_doctorsignup)
        /*btndoctorlogin2.setOnClickListener {
            startActivity(Intent(this, doctorlogin::class.java))
        }*/
        btndoctorsignup2.setOnClickListener {
            signupusers()
        }

        tvDoctorContact.setText("+")
        val spinner = findViewById<Spinner>(R.id.spinner)
        if (spinner != null) {
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, specializations
            )
            spinner.adapter = adapter

            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {
                    tilspecialization3 = specializations[position]
                    Toast.makeText(
                        this@doctorsignup,
                        "" + specializations[position], Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        }
        val spDob = findViewById<Spinner>(R.id.spDOBdoc)
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
                        this@doctorsignup,
                        "" + gender[position], Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        }
        val btnDob = findViewById<Button>(R.id.btnDOBdoc)
        // val textView     = findViewById<TextView>(R.id.dateTv)
        nicNumber = findViewById(R.id.tvDocID)
        nicNumber.addTextChangedListener(textWatcher)
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH) + 1
        val day = c.get(Calendar.DAY_OF_MONTH)

        btnDob.setOnClickListener {

            val dpd = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    // Display Selected date in TextView
                    btnDob.setText("" + dayOfMonth + "/" + monthOfYear + "/" + year)
                    dob = "" + dayOfMonth + "/" + monthOfYear + "/" + year
                },
                year,
                month,
                day
            )
            dpd.show()

        }

    }
    fun nicValidation(): Boolean {
        val nicCard: String = tvDocID.text.toString().trim()
        if (TextUtils.isEmpty(nicCard)) {
            tvDocID.error = "you cant leave this empty"
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
        val email: String = tilemail3.text.toString().trim()
        val password: String = tilpassword3.text.toString().trim()
        val mAuth = FirebaseAuth.getInstance()
        btndoctorsignup2.setOnClickListener {
            //&& otherValidation()
            if (emailvalidation() && passwordvalidation()&& nicValidation()) {
                val dialog = ACProgressFlower.Builder(this).direction(
                    ACProgressConstant.DIRECT_CLOCKWISE
                )
                    .themeColor(Color.WHITE)
                    .text("Registering ..")
                    .textSize(15)
                    .isTextExpandWidth(true)
                    .fadeColor(Color.DKGRAY).build()
                dialog.show()
                val message = MessageBox()

                mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, OnCompleteListener<AuthResult> { task ->
                        if (task.isSuccessful) {
                            sendinginfotodatabase()
                            dialog.dismiss()
                            startActivity(Intent(this, doctoractivity::class.java))

                        } else {
                            message.show("Sign Up failed try different Email", "Failed", this)
                            dialog.dismiss()
                        }
                    })
            }
        }
    }

    fun emailvalidation(): Boolean {
        val email: String = tilemail3.text.toString().trim()
        if (TextUtils.isEmpty(email)) {
            tilemail2.error = "you cant leave this empty"
            return false
        } else {
            tilemail2.isErrorEnabled = false
            return true
        }
    }

    fun otherValidation(): Boolean {
        val specialization: String = tilspecialization3.toString().trim()
        val contact: String = tvDoctorContact.text.toString().trim()
        if (TextUtils.isEmpty(contact)) {
            tvDoctorContact.error = "you cant leave this empty"
            return true
        } else {
            return false
        }

    }

    fun nameValidation(): Boolean {
        val name: String = tvdocName.toString().trim()
        if (TextUtils.isEmpty(name)) {
            tvdocName.error = "you cant leave this empty"
            return true
        } else {
            return false
        }

    }


    fun passwordvalidation(): Boolean {
        val password: String = tilpassword3.text.toString().trim()
        val confirmpassword = tilconfirmpassword3.text.toString().trim()
        if (TextUtils.isEmpty(password)) {
            tilpassword2.error = "you cant leave this empty"
            return false
        }
        //else if (password.length <= 8) {
        // tilpassword2.error = "password can't be less then 8 characters"
        //return false }
        else if (password != confirmpassword) {
            tilconfirmpassword2.error = "Passwords are not same"
            return false
        }  else {
            tilpassword2.isErrorEnabled = false
            tilconfirmpassword2.isErrorEnabled = false
            if (password.length<6 && confirmpassword.length<6) {
                tilpassword3.error = "must be atleast 6 characters"
                return false
            }else{
                return true
            }

        }
    }

    fun sendinginfotodatabase(): Boolean {
        val nicCard: String = tvDocID.text.toString().trim()

        val password: String = tilpassword3.text.toString().trim()
        val email: String = tilemail3.text.toString().trim()
        val name: String = tvdocName.text.toString().trim()
        val specialization = tilspecialization3.toString().trim()
        val id = FirebaseAuth.getInstance().getCurrentUser()!!.getUid()
        val contact = tvDoctorContact.text.toString().trim()
        val databaseref =
            FirebaseDatabase.getInstance().getReference("Doctor").child(specialization)
                .child(id)
        val userprofileinformation =
            doctorinfo(email, password, id, contact, specialization, name, dob, docGender,nicCard)
        databaseref.setValue(userprofileinformation)
        saveInAllDoctors()
        return true
    }

    fun saveInAllDoctors(): Boolean {
        val id = FirebaseAuth.getInstance().getCurrentUser()!!.getUid()
        val contact = tvDoctorContact.text.toString().trim()
        val databaseref = FirebaseDatabase.getInstance().getReference("AllDoctors").child(id)
        databaseref.setValue(contact)
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        this.finish()
        startActivity(Intent(this, MainActivity::class.java))
    }
}

