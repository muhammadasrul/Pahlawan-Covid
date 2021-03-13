package com.asrul.covidvaccine.ui.service

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.asrul.covidvaccine.R
import com.asrul.covidvaccine.data.api.response.KotaKabupatenItem
import com.asrul.covidvaccine.data.api.response.ProvinsiItem
import com.asrul.covidvaccine.data.api.response.ServiceItem
import com.asrul.covidvaccine.databinding.ActivityServiceBinding

class ServiceActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        const val VACCINE = "vaccine"
        const val RAPID = "rapid"
        const val EXTRA_VACCINE = "extra_vaccine"
    }

    private lateinit var binding: ActivityServiceBinding
    private lateinit var alertBuilder: AlertDialog.Builder
    private lateinit var alertDialog: AlertDialog
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar:ProgressBar
    private val wilayahViewModel = WilayahViewModel()
    private val serviceViewModel = ServiceViewModel()
    private val serviceAdapter = ServiceAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityServiceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvService.layoutManager = LinearLayoutManager(this)
        binding.rvService.setHasFixedSize(true)

        binding.edtProvince.setOnClickListener(this)
        binding.edtCity.setOnClickListener(this)

        val extra = intent.getStringExtra(EXTRA_VACCINE)

        if (extra == VACCINE) {
            serviceViewModel.getVaccine()
            supportActionBar?.title = "Layanan Vaksinasi"
        } else {
            serviceViewModel.getRapid()
            supportActionBar?.title = "Layanan Rapid Test/PCR"
        }

        serviceObserver()
        onItemClicked()
    }

    override fun onClick(view: View?) {
        when(view?.id) {
            R.id.edt_province -> popUpProvince()
            R.id.edt_city -> {
                if (binding.edtProvince.text.isNullOrEmpty()) {
                    binding.edtProvince.error = "Pilih provinsi"
                    return
                }
                val id_provinsi = binding.edtProvince.tag.toString()
                popUpCity(id_provinsi)
            }
        }
    }

     private fun popUpProvince() {
        val alertLayout: View = layoutInflater.inflate(R.layout.custom_dialog, null)
        recyclerView = alertLayout.findViewById(R.id.rv_custom_dialog)
         progressBar = alertLayout.findViewById(R.id.progress_bar)
        alertBuilder = AlertDialog.Builder(this@ServiceActivity)
                .setTitle("Provinsi")
                .setView(alertLayout)
                .setCancelable(true)

        alertDialog = alertBuilder.show()

         wilayahViewModel.isLoading.observe(this, {
             progressBar.visibility = if (it) View.VISIBLE else View.GONE
         })
        wilayahViewModel.getProvinsiList()
        wilayahViewModel.provinsiList.observe(this, { provinsi ->
            val adapter = ProvinsiListAdapter()
            adapter.setProvinsiList(provinsi)
            adapter.notifyDataSetChanged()

            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.adapter = adapter

            adapter.setOnItemClickCallback(object: ProvinsiListAdapter.OnItemClickCallbak {
                override fun onItemClicked(provinsi: ProvinsiItem?) {
                    binding.edtProvince.setText(provinsi?.nama)
                    binding.edtProvince.tag = provinsi?.id
                    binding.edtProvince.error = null
                    binding.edtCity.setText("")
                    alertDialog.dismiss()

                    val extra = intent.getStringExtra(EXTRA_VACCINE)
                    val province = binding.edtProvince.text.toString()

                    if (extra == VACCINE) {
                        serviceViewModel.getVaccineByProvince(province)
                    } else {
                        serviceViewModel.getRapidByProvince(province)
                    }
                }
            })
        })
    }

    private fun popUpCity(provinsi_id: String) {
        val alertLayout: View = layoutInflater.inflate(R.layout.custom_dialog, null)
        recyclerView = alertLayout.findViewById(R.id.rv_custom_dialog)
        progressBar = alertLayout.findViewById(R.id.progress_bar)
        alertBuilder = AlertDialog.Builder(this)
                .setTitle("Kota/Kabupaten")
                .setView(alertLayout)
                .setCancelable(true)

        alertDialog = alertBuilder.show()

        wilayahViewModel.isLoading.observe(this, {
            progressBar.visibility = if (it) View.VISIBLE else View.GONE
        })
        wilayahViewModel.getKotaList(provinsi_id)
        wilayahViewModel.kotaList.observe(this, { kota ->
            val adapter = KotaListAdapter()
            adapter.setKotaList(kota)
            adapter.notifyDataSetChanged()

            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.adapter = adapter

            adapter.setOnItemClickCallback(object: KotaListAdapter.OnItemClickCallback {
                override fun onItemClicked(kota: KotaKabupatenItem?) {
                    binding.edtCity.setText(kota?.nama)
                    alertDialog.dismiss()

                    val extra = intent.getStringExtra(EXTRA_VACCINE)
                    val city = binding.edtCity.text.toString()

                    if (extra == VACCINE) {
                        serviceViewModel.getVaccineByCity(city)
                    } else {
                        serviceViewModel.getRapidByCity(city)
                    }
                }
            })
        })
    }

    private fun serviceObserver() {
        serviceViewModel.listService.observe(this, { service ->
            serviceAdapter.setServiceList(service)
            serviceAdapter.notifyDataSetChanged()

            binding.rvService.adapter = serviceAdapter
        })

        serviceViewModel.isLoading.observe(this, {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        })

        serviceViewModel.isDataNotEmpty.observe(this, {
            binding.tvDataEmpty.visibility = if (it) View.GONE else View.VISIBLE
        })
    }

    private fun onItemClicked() {
        serviceAdapter.setOnItemClickCallback(object: ServiceAdapter.OnItemClickCallback {
            override fun onItemClicked(service: ServiceItem?) {
                val intent = Intent(this@ServiceActivity, ServiceRegistActivity::class.java)
                intent.putExtra(ServiceRegistActivity.EXTRA_SERVICE, service)
                startActivity(intent)
            }
        })
    }
}