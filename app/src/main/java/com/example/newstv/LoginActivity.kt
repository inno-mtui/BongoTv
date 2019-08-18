package com.example.newstv

//import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    private var buttonLogin: Button? = null
    private var editTextEmail: EditText? = null
    private var editTextPassword: EditText? = null
    private var mAuth: FirebaseAuth? = null
   // private var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mAuth = FirebaseAuth.getInstance()
        if (mAuth!!.currentUser != null) {
            // Send to registration activity
            finish()
            startActivity(Intent(applicationContext, PostActivity::class.java))
        }
        editTextEmail = findViewById(R.id.email)
        editTextPassword = findViewById(R.id.password)
        buttonLogin = findViewById(R.id.btn_login)

      //  progressDialog = ProgressDialog(this)

        buttonLogin!!.setOnClickListener(this)


    }

    private fun userLogIn() {
        // Getting the typed email and password to string form
        val email = editTextEmail!!.text.toString().trim { it <= ' ' }
        val password = editTextPassword!!.text.toString().trim { it <= ' ' }
        //check if the text for email and password are empty
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter Email", Toast.LENGTH_SHORT).show()
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter Password", Toast.LENGTH_SHORT).show()
        }
        //progressDialog!!.setMessage("Login in please wait.....")
        //progressDialog!!.show()
        mAuth!!.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        //Send to registration Activity
                        finish()
                        startActivity(Intent(applicationContext, PostActivity::class.java))
                        //progressDialog!!.dismiss()
                    } else {
                        Toast.makeText(this@LoginActivity, "Login Error", Toast.LENGTH_SHORT).show()

                    }
                }
    }

    override fun onClick(v: View) {
        if (v === buttonLogin) {
            userLogIn()
        }
    }
}
