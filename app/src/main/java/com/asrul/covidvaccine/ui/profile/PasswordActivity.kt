package com.asrul.covidvaccine.ui.profile

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.asrul.covidvaccine.data.api.ApiConfig
import com.asrul.covidvaccine.data.api.response.UserResponse
import com.asrul.covidvaccine.databinding.ActivityPasswordBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PasswordActivity : AppCompatActivity() {

    companion object {
        const val ID = "id"
    }

    private lateinit var binding: ActivityPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnChangePassword.setOnClickListener {
            changePassword()
        }
    }

    private fun changePassword() {
        val password = binding.etPassword.text.toString().trim()
        val rePassword = binding.etRePassword.text.toString().trim()

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
            binding.etRePassword.error = "Password do not match!"
            binding.etRePassword.requestFocus()
            return
        }

        val id = intent.getStringExtra(ID)!!

        ApiConfig.getApiUser().getPassword(id, password)
            .enqueue(object : Callback<UserResponse> {
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    Toast.makeText(this@PasswordActivity, response.body()!!.message, Toast.LENGTH_SHORT).show()
                    finish()
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    Toast.makeText(this@PasswordActivity, t.localizedMessage, Toast.LENGTH_SHORT).show()
                }

            })
    }
}