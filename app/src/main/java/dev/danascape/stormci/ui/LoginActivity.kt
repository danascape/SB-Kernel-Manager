package dev.danascape.stormci.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import dev.danascape.stormci.R
import dev.danascape.stormci.util.NetworkUtils.isOnline
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = FirebaseAuth.getInstance()

        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val btnGuest = findViewById<Button>(R.id.btnGuest)
        val etEmailLogin = findViewById<EditText>(R.id.etEmailLogin)
        val etPasswordLogin = findViewById<EditText>(R.id.etPasswordLogin)
        btnLogin.setOnClickListener {
            if (!isOnline(this)) {
                Toast.makeText(this, "No internet Connection", Toast.LENGTH_SHORT).show()
            } else {
                loginUser(etEmailLogin.text.toString(), etPasswordLogin.text.toString())
            }
        }
        btnGuest.setOnClickListener {
            var loggedInState = checkLoggedInState().toString()
            Intent(this@LoginActivity, MainActivity::class.java).also {
                it.putExtra("AUTH_USER", loggedInState)
                it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(it)
                finish()
            }
        }
    }

    @SuppressLint("CutPasteId")
    private fun loginUser(email: String, password: String) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    auth.signInWithEmailAndPassword(email, password).await()
                    withContext(Dispatchers.Main) {
                        checkLoggedInState()
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@LoginActivity, e.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    fun checkLoggedInState(): Int {
        if (auth.currentUser == null) { // not logged in
            return 1
        } else {
            Intent(this@LoginActivity, MainActivity::class.java).also {
                it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(it)
                finish()
            }
            return 0
        }
    }

    override fun onStart() {
        super.onStart()
        checkLoggedInState()
    }
}