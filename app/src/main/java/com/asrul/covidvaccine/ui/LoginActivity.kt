package com.asrul.covidvaccine.ui

import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.asrul.covidvaccine.R
import com.asrul.covidvaccine.data.api.ApiConfig
import com.asrul.covidvaccine.data.api.response.UserData
import com.asrul.covidvaccine.data.api.response.UserResponse
import com.asrul.covidvaccine.databinding.ActivityLoginBinding
import com.asrul.covidvaccine.utils.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val spannableString = SpannableString("Anda belum punya akun? Register sekarang!")
        spannableString.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(this, R.color.endPurple)), 22, 41, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        binding.txtRegister.text = spannableString

        binding.txtRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnLogin.setOnClickListener {
            userLogin()
        }
    }

    private fun userLogin() {
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()

        if (email.isEmpty()) {
            binding.etEmail.error = "Email is required!"
            binding.etEmail.requestFocus()
            return
        }
        if (password.isEmpty()) {
            binding.etPassword.error = "Password is required!"
            binding.etPassword.requestFocus()
            return
        }
        binding.btnLogin.text = "Authenticating..."
        ApiConfig.getApiUser().getLogin(email, password)
                .enqueue(object : Callback<UserResponse> {
                    override fun onResponse(
                        call: Call<UserResponse>,
                        response: Response<UserResponse>
                    ) {
                        binding.btnLogin.text = "Login"
                        if (response.isSuccessful && response.body() != null && response.body()!!.error != true) {
                            val sessionManager = SessionManager(applicationContext)
                            val loginData: UserData? = response.body()!!.data
                            if (loginData != null) {
                                sessionManager.createLoginSession(loginData)
                            }
                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(
                                this@LoginActivity,
                                "${response.body()?.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                        binding.btnLogin.text = "Login"
                        Toast.makeText(this@LoginActivity, t.localizedMessage, Toast.LENGTH_SHORT).show()
                    }

                })
    }
}