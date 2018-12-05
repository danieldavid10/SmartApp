package com.infoarch.smartrestoadminapp.components

import android.app.Activity
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.widget.EditText
import android.widget.Toast
import java.util.regex.Pattern

// Show a message on toast.
fun Activity.toastMessage(message: CharSequence, duration: Int = Toast.LENGTH_SHORT) =
    Toast.makeText(this, message, duration).show()

// Navigate between activities.
inline fun <reified T : Activity> Activity.goToActivity(noinline init: Intent.() -> Unit = {}) {
    val intent = Intent(this, T::class.java)
    intent.init()
    startActivity(intent)
}

// Show validation message in real time while the user is typing.
fun EditText.realTimeValidate(validation: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            validation(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    })
}

// Validation email with patterns
fun Activity.isValidEmail(email: String): Boolean {
    val pattern = Patterns.EMAIL_ADDRESS
    return pattern.matcher(email).matches()
}

// Validation password with patterns
fun Activity.isValidPassword(password: String): Boolean {
    // Needs to contain = "(1 number)(1 lowercase)(1 uppercase)(1 especialCharacter)(\\).{Min. characters}"
    //val passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$"
    val passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=\\S+$).{4,}$"
    val pattern = Pattern.compile(passwordPattern)
    return pattern.matcher(password).matches()
}

// Validation of equal passwords
fun Activity.isValidConfirmPassword(password: String, confirmPassword: String): Boolean {
    return password == confirmPassword
}
