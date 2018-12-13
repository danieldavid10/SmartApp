package com.infoarch.smartrestoadminapp

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.TargetApi
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.infoarch.smartrestoadminapp.components.goToActivity
import com.infoarch.smartrestoadminapp.components.toastMessage
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private val mAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        buttonLogin.setOnClickListener { attemptLogin(emailEditText.text.toString(), passwordEditText.text.toString()) }

        textViewForgotPassword.setOnClickListener{goToActivity<ForgotPasswordActivity>()}
    }

    private fun signIn(email: String, password: String) {
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (!task.isSuccessful) {
                    toastMessage("An unexpected error occurred, please try again")
                    showProgress(false)
                } else {
                    loginIsSuccessful()
                }
            }
    }

    private fun loginIsSuccessful() {
        if (mAuth.currentUser != null) {
            toastMessage("Welcome..!!")
            goMainActivity(false)
        }
    }

    private fun goMainActivity(start: Boolean) {
        val intent = Intent(this, MainActivity::class.java)
        if (!start) {
            showProgress(false)
        }
        startActivity(intent)
        this.finish()
    }

    private fun attemptLogin(email: String, password: String) {

        var cancel = false
        var focusView: View? = null

        // Check for a valid email address and password.
        if (TextUtils.isEmpty(email)) {
            emailEditText.error = "This field is required"
            focusView = emailEditText
            cancel = true
        } else if (!isEmailValid(email)) {
            emailEditText.error = "Invalid email address"
            focusView = emailEditText
            cancel = true
        } else if (TextUtils.isEmpty(password)) {
            passwordEditText.error = "This field is required"
            focusView = passwordEditText
            cancel = true
        } else if (!isPasswordValid(password)) {
            passwordEditText.error = "Password is incorrect"
            focusView = passwordEditText
            cancel = true
        }

        if (cancel) {
            focusView!!.requestFocus()
        } else {
            showProgress(true)
            signIn(email, password)
        }
    }

    private fun isEmailValid(email: String): Boolean {
        return email.contains("@")
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length > 4
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private fun showProgress(show: Boolean) {
        val shortAnimTime = resources.getInteger(android.R.integer.config_shortAnimTime)
        login_progress.visibility = if (show) View.VISIBLE else View.GONE
        login_progress.animate().setDuration(shortAnimTime.toLong()).alpha(
            (if (show) 1 else 0).toFloat()
        ).setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                login_progress.visibility = if (show) View.VISIBLE else View.GONE
            }
        })
    }
}


