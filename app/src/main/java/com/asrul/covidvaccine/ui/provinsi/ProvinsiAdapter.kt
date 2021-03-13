package com.asrul.covidvaccine.ui.provinsi

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.asrul.covidvaccine.data.api.response.DataItem
import com.asrul.covidvaccine.databinding.CovidItemBinding

class ProvinsiAdapter: RecyclerView.Adapter<ProvinsiAdapter.ProvinsiViewHolder>() {

    val listProvinsi = ArrayList<DataItem?>()

    fun setProvinsi(provinsi: List<DataItem?>) {
        this.listProvinsi.clear()
        this.listProvinsi.addAll(provinsi)
    }

    class ProvinsiViewHolder(private val binding: CovidItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(provinsi: DataItem?) {
            with(binding) {
                txtProvinsi.text = provinsi?.provinsi
                txtPositif.text = provinsi?.kasusPosi.toString()
                txtMeninggal.text = provinsi?.kasusMeni.toString()
                txtSembuh.text = provinsi?.kasusSemb.toString()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProvinsiAdapter.ProvinsiViewHolder {
        val binding = CovidItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProvinsiViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProvinsiAdapter.ProvinsiViewHolder, position: Int) {
        val provinsi = listProvinsi[position]
        holder.bind(provinsi)
    }

    override fun getItemCount(): Int = listProvinsi.size
}