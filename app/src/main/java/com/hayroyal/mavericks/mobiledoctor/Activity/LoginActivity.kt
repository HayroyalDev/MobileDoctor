package com.hayroyal.mavericks.mobiledoctor.Activity

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Base64
import android.view.View
import com.google.gson.Gson
import com.hayroyal.mavericks.mobiledoctor.Helper.AppPreference
import com.hayroyal.mavericks.mobiledoctor.Models.User
import com.hayroyal.mavericks.mobiledoctor.R
import com.hayroyalconsult.mavericks.mavex.Converter
import kotlinx.android.synthetic.main.activity_login.*
import java.util.*


class LoginActivity : AppCompatActivity() {
    var users: ArrayList<User>? = null
    var appPreference: AppPreference? = null
    var pb: ProgressDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        appPreference = AppPreference(this)
        pb?.setMessage("Please Wait...")
        pb?.setCanceledOnTouchOutside(false)
        //appPreference!!.clearPreference()
        users = appPreference!!.getUsers()
        if (users == null) {
            users = ArrayList()
        }
        email_register_button.setOnClickListener {
            showRegister()
        }

        link_login.setOnClickListener {
            showLogin()
        }
        email_sign_in_button.setOnClickListener {
            pb?.show()
            doLogin()
        }

        btn_signup.setOnClickListener {
            pb?.show()
            doValidation()
        }

    }

    private fun doLogin() {
        val em = email.text.toString()
        val pass = password.text.toString()
        val encoded = Base64.encodeToString(password.text.toString().toByteArray(), Base64.NO_WRAP).toString()
        if (users!!.size > 0) {
            var iterator = users!!.iterator()
            var chk = false
            for (u in users!!) {
                if (em == u.email && encoded == u.password) {
                    startActivity(Intent(this, MainActivity::class.java).apply {
                        putExtra("user", Gson().toJson(u))
                    })
                    chk = true
                    break
                }
            }
            if (!chk) {
                pb?.dismiss()
                email.error = "Invalid Credentials"
                password.error = "Invalid Credentials"
            }
        } else {
            pb?.dismiss()
            showRegister()
            Snackbar.make(container, "User not Found. Please register.", Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun doValidation() {
        if (input_name.text.isEmpty()) {
            input_name.error = "Invalid Input"
        } else if (input_email.text.isEmpty()) {
            input_email.error = "Invalid Input"
        } else if (input_password.text.isEmpty()) {
            input_password.error = "Invalid Input"
        } else {
            var user = User()
            user.fullname = input_name.text.toString()
            user.email = input_email.text.toString()
            user.password = Base64.encodeToString(input_password.text.toString().toByteArray(), Base64.NO_WRAP).toString()
            user._id = users!!.size + 1
            var check = false
            for (u in users!!) {
                if (user.email == u.email) {
                    Snackbar.make(container, "User Already Exist!!!", Snackbar.LENGTH_SHORT).show()
                    check = true
                    break
                }
            }
            if (!check) {
                users!!.add(user)
                appPreference!!.setUsers(users!!)
                startActivity(Intent(this, MainActivity::class.java).apply {
                    putExtra("user", Gson().toJson(user))
                })
                finish()
            }
        }
    }

    fun showLogin() {
        email_login_form.visibility = View.VISIBLE
        email_register_form.visibility = View.GONE
    }

    fun showRegister() {
        email_login_form.visibility = View.GONE
        email_register_form.visibility = View.VISIBLE
    }

    override fun onBackPressed() {
        if (email_register_form.visibility == View.VISIBLE) {
            showLogin()
        } else {
            super.onBackPressed()
        }
    }

}
