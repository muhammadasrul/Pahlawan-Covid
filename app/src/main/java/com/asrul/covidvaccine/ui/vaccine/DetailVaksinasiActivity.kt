package com.asrul.covidvaccine.ui.vaccine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.asrul.covidvaccine.R
import com.asrul.covidvaccine.databinding.ActivityDetailVaksinasiBinding

class DetailVaksinasiActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailVaksinasiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailVaksinasiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Progress Vaksinasi"

        val viewModel = VaccineViewmodel()
        viewModel.getVaccineStats()
        viewModel.vaccineList.observe(this, { vaksin ->
            binding.tvSemua1.text = vaksin[0]?.semua
            binding.tvSemua2.text = vaksin[1]?.semua
            binding.tvDariSemua1.text = getString(R.string.semua_dosis, vaksin[0]?.tSemua)
            binding.tvDariSemua2.text = getString(R.string.semua_dosis, vaksin[1]?.tSemua)

            binding.tvTenagaKesehatan1.text = vaksin[0]?.tenagaKesehatan
            binding.tvTenagaKesehatan2.text = vaksin[1]?.tenagaKesehatan
            binding.tvDariTenagaKesehatan1.text = getString(R.string.semua_dosis,vaksin[0]?.tTenagaKesehatan)
            binding.tvDariTenagaKesehatan2.text = getString(R.string.semua_dosis,vaksin[1]?.tTenagaKesehatan)

            binding.tvPetugasPublik1.text = vaksin[0]?.petugasPublik
            binding.tvPetugasPublik2.text = vaksin[1]?.petugasPublik
            binding.tvDariPetugasPublik1.text = getString(R.string.semua_dosis,vaksin[0]?.tPetugasPublik)
            binding.tvDariPetugasPublik2.text = getString(R.string.semua_dosis,vaksin[1]?.tPetugasPublik)

            binding.tvLansia1.text = vaksin[0]?.lansia
            binding.tvLansia2.text = vaksin[1]?.lansia
            binding.tvDariLansia1.text = getString(R.string.semua_dosis,vaksin[0]?.tLansia)
            binding.tvDariLansia2.text = getString(R.string.semua_dosis,vaksin[1]?.tLansia)
        })

        viewModel.isLoading.observe(this, {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        })
    }
}