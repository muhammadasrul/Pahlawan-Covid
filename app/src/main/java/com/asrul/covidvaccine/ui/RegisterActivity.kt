package com.asrul.covidvaccine.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.widget.Toast
import com.asrul.covidvaccine.R
import com.asrul.covidvaccine.data.api.ApiConfig
import com.asrul.covidvaccine.data.api.response.UserResponse
import com.asrul.covidvaccine.databinding.ActivityRegisterBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        val spannableString = SpannableString("Anda sudah punya akun? Login sekarang!")
        spannableString.setSpan(
                ForegroundColorSpan(resources.getColor(R.color.endPurple)), 22, 38, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        binding.tvLogin.text = spannableString

        binding.tvLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnRegister.setOnClickListener {
            userRegister()
        }
    }

    private fun userRegister() {
        val name = binding.etName.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()
        val phone = binding.etPhone.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()
        val rePassword = binding.etRepassword.text.toString().trim()

        if (name.isEmpty()) {
            binding.etName.error = "Name is required!"
            binding.etName.requestFocus()
            return
        }
        if (email.isEmpty()) {
            binding.etEmail.error = "Email is required!"
            binding.etEmail.requestFocus()
            return
        }
        if (phone.isEmpty()) {
            binding.etPhone.error = "Phone is required!"
            binding.etPhone.requestFocus()
            return
        }
        if (password.isEmpty()) {
            binding.etPassword.error = "Password is required!"
            binding.etPassword.requestFocus()
            return
        }
        if (password.length < 8) {
            binding.etPassword.error = "Password must have at least 8 characters!"
            binding.etPassword.requestFocus()
            return
        }
        if (password != rePassword) {
            binding.etRepassword.error = "Password do not match!"
            binding.etRepassword.requestFocus()
            return
        }

        binding.btnRegister.text = "Registering..."
        ApiConfig.getApiUser().getRegister(name, email, phone, password)
                .enqueue(object : Callback<UserResponse> {
                    override fun onResponse(
                        call: Call<UserResponse>,
                        response: Response<UserResponse>
                    ) {
                        binding.btnRegister.text = "Register"
                        if (response.isSuccessful) {
                            if (response.body() != null) {
                                val user = response.body()?.data?.email
                                Toast.makeText(
                                    this@RegisterActivity,
                                    "$user telah terdaftar!",
                                    Toast.LENGTH_SHORT
                                ).show()
                                val intent =
                                    Intent(this@RegisterActivity, LoginActivity::class.java)
                                startActivity(intent)
                            }
                        } else {
                            Toast.makeText(
                                this@RegisterActivity,
                                response.message(),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                        binding.btnRegister.text = "Register"
                        Toast.makeText(this@RegisterActivity, t.localizedMessage, Toast.LENGTH_SHORT).show()
                    }

                })
    }
}