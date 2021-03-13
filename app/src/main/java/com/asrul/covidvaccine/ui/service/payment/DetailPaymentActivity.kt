package com.asrul.covidvaccine.ui.service.payment

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.asrul.covidvaccine.R
import com.asrul.covidvaccine.data.api.response.ServiceItem
import com.asrul.covidvaccine.data.api.response.ServiceRegistItem
import com.asrul.covidvaccine.databinding.ActivityDetailPaymentBinding
import com.asrul.covidvaccine.ui.CustomBottomSheetDialog
import com.asrul.covidvaccine.ui.service.ServiceRegistActivity
import com.asrul.covidvaccine.ui.riwayat.RiwayatActivity
import com.asrul.covidvaccine.ui.service.ServiceActivity
import com.bumptech.glide.Glide

class DetailPaymentActivity : AppCompatActivity() {

    companion object {
        const val REGIST_ID = "regist_id"
        const val SERVICE_DATE = "service_date"
        const val PAYMENT = "payment_method"
        const val TOTAL = "total_payment"
        const val EXTRA_DETAIL = "extra_detail"
        const val KEY = "key_detail"
    }

    private lateinit var binding: ActivityDetailPaymentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomSheetDialog = CustomBottomSheetDialog()

        val fromRegist: ServiceItem? = intent.getParcelableExtra(ServiceRegistActivity.EXTRA_SERVICE)
        val fromRiwayat: ServiceRegistItem? = intent.getParcelableExtra(EXTRA_DETAIL)
        val key = intent.getStringExtra(KEY)

        if (key == ServiceRegistActivity.EXTRA_SERVICE) {
            Glide.with(this)
                .load(fromRegist?.logoPath)
                .placeholder(ContextCompat.getDrawable(this, R.drawable.ic_baseline_refresh_24))
                .error(ContextCompat.getDrawable(this, R.drawable.ic_baseline_broken_image_24))
                .into(binding.imgRs)

            binding.tvName.text = fromRegist?.name
            binding.tvAddress.text = fromRegist?.address
            binding.tvService.text = fromRegist?.service
            binding.tvPrice.text = getString(R.string.price, fromRegist?.price)
        } else {
            Glide.with(this)
                .load(fromRiwayat?.logoPath)
                .placeholder(ContextCompat.getDrawable(this, R.drawable.ic_baseline_refresh_24))
                .error(ContextCompat.getDrawable(this, R.drawable.ic_baseline_broken_image_24))
                .into(binding.imgRs)

            binding.tvName.text = fromRiwayat?.name
            binding.tvAddress.text = fromRiwayat?.address
            binding.tvService.text = fromRiwayat?.service
            binding.tvPrice.text = getString(R.string.price, fromRiwayat?.totalPayment)
        }


        binding.tvServiceDate.text = intent.getStringExtra(SERVICE_DATE)
        binding.tvOrderId.text = intent.getStringExtra(REGIST_ID)
        binding.tvMetode.text = intent.getStringExtra(PAYMENT)
        binding.tvTotal.text = getString(R.string.price, intent.getStringExtra(TOTAL))

        binding.tvAtm.setOnClickListener {
            bottomSheetDialog.show(supportFragmentManager, CustomBottomSheetDialog.EXTRA_ATM)
        }

        binding.tvMobile.setOnClickListener {
            bottomSheetDialog.show(supportFragmentManager, CustomBottomSheetDialog.EXTRA_MOBILE)
        }

        binding.tvInternet.setOnClickListener {
            bottomSheetDialog.show(supportFragmentManager, CustomBottomSheetDialog.EXTRA_INTERNET)
        }

        binding.btnDaftar.setOnClickListener {
            if (key == ServiceRegistActivity.EXTRA_SERVICE) startActivity(Intent(this, RiwayatActivity::class.java))
            finish()
        }
    }
}