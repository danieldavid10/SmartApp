package com.infoarch.smartrestoadminapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.infoarch.smartrestoadminapp.components.goToActivity
import com.infoarch.smartrestoadminapp.components.isValidEmail
import com.infoarch.smartrestoadminapp.components.realTimeValidate
import com.infoarch.smartrestoadminapp.components.toastMessage
import kotlinx.android.synthetic.main.activity_forgot_password.*

class ForgotPasswordActivity : AppCompatActivity() {

    private val mAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        emailEditText.realTimeValidate {
            emailEditText.error = if (isValidEmail(it)) null else "Email is not valid"
        }

        buttonResetPassword.setOnClickListener {
            val email = emailEditText.text.toString()
            if (isValidEmail(email)) {
                mAuth.sendPasswordResetEmail(email).addOnCompleteListener(this) {
                    toastMessage("Email has been sent to reset your password.")
                    goToActivity<LoginActivity> {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    }
                }
            } else {
                toastMessage("Please enter a valid email address")
            }
        }

        buttonGoBack.setOnClickListener {
            goToActivity<LoginActivity> {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
        }

        btn_goTab.setOnClickListener { goToActivity<RestaurantMainActivity>() }



    }

}
