package com.asrul.covidvaccine.ui.provinsi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.asrul.covidvaccine.databinding.ActivityProvinsiBinding

class ProvinsiActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProvinsiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProvinsiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Statistik Provinsi"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val viewModel = ProvinsiViewModel()

        viewModel.getProvinsi()
        viewModel.provinsi.observe(this, { provinsi->

            val provinsiAdapter = ProvinsiAdapter()
            provinsiAdapter.setProvinsi(provinsi)
            provinsiAdapter.notifyDataSetChanged()

            binding.rvProvinsi.layoutManager = LinearLayoutManager(this)
            binding.rvProvinsi.setHasFixedSize(true)
            binding.rvProvinsi.adapter = provinsiAdapter
        })

        viewModel.isLoading.observe(this, {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        })
    }
}