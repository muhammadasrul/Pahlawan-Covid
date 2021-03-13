package com.asrul.covidvaccine.ui.service

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.asrul.covidvaccine.R
import com.asrul.covidvaccine.data.api.ApiConfig
import com.asrul.covidvaccine.data.api.response.ServiceItem
import com.asrul.covidvaccine.data.api.response.ServiceRegistResponse
import com.asrul.covidvaccine.databinding.ActivityServiceRegistBinding
import com.asrul.covidvaccine.ui.service.payment.DetailPaymentActivity
import com.asrul.covidvaccine.ui.service.payment.PaymentAdapter
import com.asrul.covidvaccine.ui.service.payment.PaymentViewModel
import com.asrul.covidvaccine.utils.SessionManager
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class ServiceRegistActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_SERVICE = "extra_service"
    }

    private lateinit var binding: ActivityServiceRegistBinding
    private var calendar = Calendar.getInstance()
    private val adapter = PaymentAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityServiceRegistBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val extraService: ServiceItem? = intent.getParcelableExtra(EXTRA_SERVICE)

        val sessionManager = SessionManager(this)
        val userName = sessionManager.getUserDetail()[SessionManager.NAME] ?: ""

        binding.edtName.setText(userName)

        if (extraService != null) {
            binding.tvName.text = extraService.name
            binding.tvAddress.text = extraService.address
            binding.tvService.text = extraService.service
            binding.tvPrice.text = getString(R.string.price, extraService.price)

            Glide.with(this)
                .load(extraService.logoPath)
                .placeholder(R.drawable.ic_baseline_refresh_24)
                .error(R.drawable.ic_baseline_broken_image_24)
                .into(binding.imgRs)
        }

        val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val serviceDate = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, monthOfYear)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            binding.edtServiceDate.setText(sdf.format(calendar.time))
        }

        val birthday = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, monthOfYear)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            binding.edtBirthday.setText(sdf.format(calendar.time))
        }

        binding.edtServiceDate.setOnClickListener {
            DatePickerDialog(
                this, serviceDate,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        binding.edtBirthday.setOnClickListener {
            DatePickerDialog(
                this, birthday,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        ArrayAdapter.createFromResource(
            this,
            R.array.gender_list,
            android.R.layout.simple_spinner_dropdown_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.genderSpinner.adapter = adapter
        }

        val viewModel = PaymentViewModel()
        viewModel.getPaymentList()
        viewModel.paymentList.observe(this, { payment ->
            adapter.setPayment(payment)
            adapter.notifyDataSetChanged()

            binding.rvPayment.layoutManager = LinearLayoutManager(this)
            binding.rvPayment.setHasFixedSize(true)
            binding.rvPayment.adapter = adapter
        })

        binding.btnDaftar.setOnClickListener {
            if (binding.genderSpinner.selectedItemPosition == 0) {
                showMessage()
                return@setOnClickListener
            }

            if (binding.edtServiceDate.text.isNullOrEmpty()) {
                binding.edtServiceDate.error = "Pilih tanggal pelayanan!"
                binding.edtServiceDate.requestFocus()
                showMessage()
                return@setOnClickListener
            }
            if (binding.edtName.text.isNullOrEmpty()) {
                binding.edtName.error = "Nama tidak boleh kosong!"
                binding.edtName.requestFocus()
                showMessage()
                return@setOnClickListener
            }
            if (binding.edtNik.text.isNullOrEmpty()) {
                binding.edtNik.error = "NIK tidak boleh kosong!"
                binding.edtName.requestFocus()
                showMessage()
                return@setOnClickListener
            }
            if (binding.edtNik.text!!.length != 16) {
                binding.edtNik.error = "NIK tidak valid"
                binding.edtNik.requestFocus()
                return@setOnClickListener
            }
            if (binding.edtBirthday.text.isNullOrEmpty()) {
                binding.edtBirthday.error = "Tanggal lahir tidak boleh kosong!"
                binding.edtName.requestFocus()
                showMessage()
                return@setOnClickListener
            }

            val user_id = sessionManager.getUserDetail()[SessionManager.ID]
            val hospital_id = extraService?.hospitalId
            val service = extraService?.service
            val total_payment = extraService?.price
            val full_name = binding.edtName.text.toString()
            val nik = binding.edtNik.text.toString()
            val regist_date = sdf.format(Date())
            val service_date = binding.edtServiceDate.text.toString()
            val _birthday = binding.edtBirthday.text.toString()
            val gender = binding.genderSpinner.selectedItem.toString()
            val regist_id = (1..Int.MAX_VALUE).random().toString()

            if (adapter.getSelected() != null) {
                val payment_method = adapter.getSelected()!!.name

                ApiConfig.getApiUser().postServiceRegist(regist_id, user_id, hospital_id, service, total_payment, payment_method, full_name, nik, regist_date, service_date, _birthday, gender)
                    .enqueue(object : Callback<List<ServiceRegistResponse>> {
                        override fun onResponse(
                            call: Call<List<ServiceRegistResponse>>,
                            response: Response<List<ServiceRegistResponse>>
                        ) {
                            if (response.isSuccessful && response.body() != null) {
                                Log.e("ServiceRegistActivity", "onResponse: ${response.message()}")
                            }
                        }

                        override fun onFailure(
                            call: Call<List<ServiceRegistResponse>>,
                            t: Throwable
                        ) {
                            t.printStackTrace()
                        }

                    })

                val intent = Intent(this, DetailPaymentActivity::class.java)
                    .putExtra(EXTRA_SERVICE, extraService)
                    .putExtra(DetailPaymentActivity.REGIST_ID, regist_id)
                    .putExtra(DetailPaymentActivity.SERVICE_DATE, service_date)
                    .putExtra(DetailPaymentActivity.PAYMENT, payment_method)
                    .putExtra(DetailPaymentActivity.TOTAL, total_payment)
                    .putExtra(DetailPaymentActivity.KEY, EXTRA_SERVICE)
                startActivity(intent)
                finish()

            } else {
                Toast.makeText(this, "Pilih metode pembayaran!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showMessage() {
        Toast.makeText(this, "Lengkapi form pendaftaran!", Toast.LENGTH_SHORT).show()
    }
}