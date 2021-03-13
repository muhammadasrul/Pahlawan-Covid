package com.asrul.covidvaccine.ui.vaccine

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.asrul.covidvaccine.R
import com.asrul.covidvaccine.data.api.response.VaksinItem
import com.asrul.covidvaccine.databinding.FragmentVaccineBinding
import com.asrul.covidvaccine.ui.riwayat.RiwayatActivity
import com.asrul.covidvaccine.ui.service.ServiceActivity

class VaccineFragment : Fragment(), View.OnClickListener {

    private var fragmentVaccineBinding: FragmentVaccineBinding? = null
    private val binding get() = fragmentVaccineBinding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        fragmentVaccineBinding = FragmentVaccineBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.layananVaksinasiBtn.setOnClickListener(this)
        binding.layananRapidBtn.setOnClickListener(this)
        binding.historyBtn.setOnClickListener(this)
        binding.btnBannerVaksinasi.setOnClickListener(this)

        val viewModel = VaccineViewmodel()
        viewModel.getVaccineStats()
        viewModel.vaccineList.observe(viewLifecycleOwner, { vaksinasi ->
            binding.tvStats1.text = vaksinasi[0]?.semua
            binding.tvStats2.text = vaksinasi[1]?.semua
            binding.tvUpdate.text = getString(R.string.pembaruan_terhakhir, vaksinasi[0]?.pembaruanTerakhir)
        })
        viewModel.isLoading.observe( viewLifecycleOwner, {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
            if (it) {
                binding.tahap1.visibility = View.GONE
                binding.tahap2.visibility = View.GONE
            } else {
                binding.tahap1.visibility = View.VISIBLE
                binding.tahap2.visibility = View.VISIBLE
            }
        })

        binding.detailBtn.setOnClickListener {
            val intent = Intent(context, DetailVaksinasiActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onClick(view: View?) {
        when(view?.id) {
            R.id.layanan_vaksinasi_btn -> {
                val intent = Intent(context, ServiceActivity::class.java )
                intent.putExtra(ServiceActivity.EXTRA_VACCINE, ServiceActivity.VACCINE)
                startActivity(intent)
            }
            R.id.layanan_rapid_btn -> {
                val intent = Intent(context, ServiceActivity::class.java)
                intent.putExtra(ServiceActivity.EXTRA_VACCINE, ServiceActivity.RAPID)
                startActivity(intent)
            }
            R.id.history_btn -> startActivity(Intent(context, RiwayatActivity::class.java))
            R.id.btn_banner_vaksinasi -> {
                val intent = Intent(context, ServiceActivity::class.java )
                intent.putExtra(ServiceActivity.EXTRA_VACCINE, ServiceActivity.VACCINE)
                startActivity(intent)
            }
        }
    }
}