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

class patientsignup : AppCompatActivity() {
    val message = MessageBox()
    lateinit var nicNumber: EditText
    var nic:String=""
    val gender = arrayOf<String>("Male", "Female")
    var docGender: String = gender[0]
    var dob: String = "01/01/1998"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patientsignup)
        supportActionBar?.title = "Patient SignUp"
        val myauth = FirebaseAuth.getInstance()
        /*btnlogin2.setOnClickListener {
            startActivity(Intent(this, login::class.java))
        }*/
        btnpatientsignup2.setOnClickListener {
            if (emailvalidation() && passwordvalidation() && nicValidation() && nameValidation()) {
                val dialog = ACProgressFlower.Builder(this).direction(
                    ACProgressConstant.DIRECT_CLOCKWISE
                )
                    .themeColor(Color.WHITE)
                    .text("Registering user please wait..")
                    .textSize(15)
                    .isTextExpandWidth(true)
                    .fadeColor(Color.DKGRAY).build()
                dialog.show()
                val patientemail: String = tilpatientemail3.text.toString().trim()
                val patientpassword = tilpatientpassword3.text.toString().trim()
                myauth.createUserWithEmailAndPassword(patientemail, patientpassword)
                    .addOnCompleteListener(this, OnCompleteListener<AuthResult> { task ->
                        if (task.isSuccessful) {
                            sendinginfotodatabase()
                            dialog.dismiss()
                            Toast.makeText(this, "success", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, patientscreen::class.java))

                        } else {
                            message.show("Sign Up failed try different Email", "Failed", this)
                            dialog.dismiss()

                        }
                    })
            }
        }

        nicNumber = findViewById(R.id.tvNic)
        nicNumber.addTextChangedListener(textWatcher)
        val spDob = findViewById<Spinner>(R.id.spDOBpat)
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
        val btnDob = findViewById<Button>(R.id.btnDOBpat)
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
    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
        }
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            if (count == 3 ||count == 8 || count==16) {

                nicNumber.append("-")
            }
            if (count==18){
                nic=nicNumber.text.toString()
            }
        }

    }

    fun emailvalidation(): Boolean {
        val email: String = tilpatientemail3.text.toString().trim()
        if (TextUtils.isEmpty(email)) {
            tilpatientemail2.error = "you cant leave this empty"
            return false
        } else {
            tilpatientemail2.isErrorEnabled = false
            return true
        }
    }
    fun nameValidation(): Boolean {
        val name: String = tvPatientName.text.toString().trim()
        if (TextUtils.isEmpty(name)) {
            tvPatientName.error = "you cant leave this empty"
            return false
        } else {
            return true
        }
    }
    fun nicValidation(): Boolean {
        val nicCard: String = tvNic.text.toString().trim()
        if (TextUtils.isEmpty(nicCard)) {
            tvNic.error = "you cant leave this empty"
            return false
        } else {
           // tilpatientemail2.isErrorEnabled = false
            return true
        }
    }

    fun passwordvalidation(): Boolean {
        val password: String = tilpatientpassword3.text.toString().trim()
        val confirmpassword = tilpatientconfirmpassword3.text.toString().trim()
        if (TextUtils.isEmpty(password)) {
            tilpatientpassword2.error = "you cant leave this empty"
            return false

        } else if (password != confirmpassword) {
            tilpatientconfirmpassword2.error = "Passwords are not same"
            return false
        } else {
            tilpatientpassword2.isErrorEnabled = false
            tilpatientconfirmpassword2.isErrorEnabled = false
            if (password.length<6 && confirmpassword.length<6) {
                tilpatientpassword3.error = "must be atleast 6 characters"
                return false
            }else{
                return true
            }
        }
    }

    fun sendinginfotodatabase() {

        val nicCard: String = tvNic.text.toString().trim()
        val name: String = tvPatientName.text.toString().trim()
        val password: String = tilpatientpassword3.text.toString().trim()
        val email: String = tilpatientemail3.text.toString().trim()
        val uuid = FirebaseAuth.getInstance().getCurrentUser()!!.getUid()
        val databaseref = FirebaseDatabase.getInstance().getReference("patients").child(uuid)
        val userprofileinformation = patientinfo(email, password, uuid,name,nicCard,dob,docGender)
        databaseref.setValue(userprofileinformation)

    }

    override fun onBackPressed() {
        super.onBackPressed()
        this.finish()
        startActivity(Intent(this, MainActivity::class.java))
    }
}
