package com.asrul.covidvaccine.ui.service

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.asrul.covidvaccine.data.api.response.DataItem
import com.asrul.covidvaccine.data.api.response.ProvinsiItem
import com.asrul.covidvaccine.databinding.ItemListBinding

class ProvinsiListAdapter: RecyclerView.Adapter<ProvinsiListAdapter.ProvinsiListViewHolder>() {

    private val listProvinsi = ArrayList<ProvinsiItem?>()

    private var onItemClickCallbak: OnItemClickCallbak? = null

    fun setOnItemClickCallback(onItemClickCallbak: OnItemClickCallbak) {
        this.onItemClickCallbak = onItemClickCallbak
    }

    fun setProvinsiList(provinsi: List<ProvinsiItem?>) {
        this.listProvinsi.clear()
        this.listProvinsi.addAll(provinsi)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProvinsiListViewHolder {
        val binding = ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProvinsiListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProvinsiListViewHolder, position: Int) {
        val provinsi = listProvinsi[position]
        holder.bind(provinsi)
    }

    override fun getItemCount(): Int = listProvinsi.size

    inner class ProvinsiListViewHolder(private val binding: ItemListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(provinsi: ProvinsiItem?) {
            with(binding) {
                tvTitle.text = provinsi?.nama
                itemView.setOnClickListener {
                    onItemClickCallbak?.onItemClicked(provinsi)
                }
            }
        }
    }

    interface OnItemClickCallbak {
        fun onItemClicked(provinsi: ProvinsiItem?)
    }
}