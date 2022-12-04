package com.meetozan.caloriecounter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.meetozan.caloriecounter.databinding.ActivityLoginBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {

    private val db = Firebase.firestore.collection("users")
    private val auth = Firebase.auth
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        binding.tvRegister.setOnClickListener {
            val dialog = BottomSheetDialog(this@LoginActivity)
            val view = layoutInflater.inflate(R.layout.bottom_sheet_layout, null)

            dialog.setContentView(view)
            val btnRegister = view.findViewById<Button>(R.id.btnRegister)

            btnRegister.setOnClickListener {
                val etName =
                    view.findViewById<TextInputLayout>(R.id.etName).editText?.text.toString()
                val etSurname =
                    view.findViewById<TextInputLayout>(R.id.etSurname).editText?.text.toString()
                val etMail =
                    view.findViewById<TextInputLayout>(R.id.etRegisterEmail).editText?.text.toString()
                val etPassword =
                    view.findViewById<TextInputLayout>(R.id.etRegisterPassword).editText?.text.toString()
                val etHeight =
                    view.findViewById<TextInputLayout>(R.id.etHeight).editText?.text.toString()
                val etWeight =
                    view.findViewById<TextInputLayout>(R.id.etWeight).editText?.text.toString()

                val user = User(etName, etSurname, etMail, etHeight, etWeight)

                registerUser(etMail, etPassword, user)
            }
            dialog.show()
        }
    }

    private fun registerUser(email: String, password: String, user: User) {
        if(email.isNotEmpty()&&password.isNotEmpty()){
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnSuccessListener {
                            checkLogged()
                            Toast.makeText(this@LoginActivity,"Welcome",Toast.LENGTH_SHORT).show()
                        }.await()
                } catch (e: java.lang.Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@LoginActivity, e.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun checkLogged() {
        if (auth.currentUser != null) {
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            finish()
        } else {
            auth.signOut()
        }
    }
}
