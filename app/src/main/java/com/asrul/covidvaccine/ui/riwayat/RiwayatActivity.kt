package com.asrul.covidvaccine.ui.riwayat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.asrul.covidvaccine.R
import com.asrul.covidvaccine.data.api.response.ServiceRegistItem
import com.asrul.covidvaccine.databinding.ActivityRiwayatBinding
import com.asrul.covidvaccine.databinding.QrDialogBinding
import com.asrul.covidvaccine.ui.service.payment.DetailPaymentActivity
import com.asrul.covidvaccine.utils.SessionManager
import com.bumptech.glide.Glide

class RiwayatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRiwayatBinding
    private lateinit var sessionManager: SessionManager
    private val viewModel = RiwayatViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRiwayatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Riwayat"
        sessionManager = SessionManager(this)

        observeData()
        refreshApp()

    }

    private fun showQR(qrCode: String) {
        val binding: QrDialogBinding = QrDialogBinding.inflate(layoutInflater)
        val layout: View = binding.root

        Glide.with(layout)
            .load(qrCode)
            .placeholder(R.drawable.ic_baseline_refresh_24)
            .error(R.drawable.ic_baseline_broken_image_24)
            .into(binding.imgQr)

        AlertDialog.Builder(this)
            .setTitle("QR Code")
            .setView(layout)
            .show()
    }

    private fun refreshApp() {
        binding.swipeRefresh.setOnRefreshListener {
            observeData()
        }
    }

    private fun observeData() {
        val userId = sessionManager.getUserDetail()[SessionManager.ID]
        viewModel.getRiwayat(userId)
        viewModel.isLoading.observe(this, {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
            binding.swipeRefresh.isRefreshing = it
        })
        viewModel.riwayat.observe(this, { riwayat ->
            val adapter = RiwayatAdapter()
            adapter.setRiwayatList(riwayat)

            binding.rvRiwayat.layoutManager = LinearLayoutManager(this)
            binding.rvRiwayat.adapter = adapter

            adapter.setOnItemClickCallback(object: RiwayatAdapter.OnItemClickCallback {
                override fun onItemClicked(service: ServiceRegistItem?, payment: Boolean, confirm: Boolean) {
                    if (payment && confirm && service?.qrCode != null) {
                        showQR(service.qrCode)
                    } else {
                        val intent = Intent(this@RiwayatActivity, DetailPaymentActivity::class.java)
                            .putExtra(DetailPaymentActivity.EXTRA_DETAIL, service)
                            .putExtra(DetailPaymentActivity.REGIST_ID, service?.registId)
                            .putExtra(DetailPaymentActivity.SERVICE_DATE, service?.serviceDate)
                            .putExtra(DetailPaymentActivity.PAYMENT, service?.paymentMethod)
                            .putExtra(DetailPaymentActivity.TOTAL, service?.totalPayment)
                        startActivity(intent)
                    }
                }

            })
        })
        viewModel.isDataNotEmpty.observe(this, {
            binding.tvDataEmpty.visibility = if (it) View.GONE else View.VISIBLE
        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}