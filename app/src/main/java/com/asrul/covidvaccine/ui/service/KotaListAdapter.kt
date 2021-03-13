package com.asrul.covidvaccine.ui.service

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.asrul.covidvaccine.data.api.response.KotaKabupatenItem
import com.asrul.covidvaccine.databinding.ItemListBinding

class KotaListAdapter: RecyclerView.Adapter<KotaListAdapter.KotaListViewHolder>() {

    private val listKota = ArrayList<KotaKabupatenItem?>()
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setKotaList(kota: List<KotaKabupatenItem?>) {
        this.listKota.clear()
        this.listKota.addAll(kota)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KotaListViewHolder {
        val binding = ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return KotaListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: KotaListViewHolder, position: Int) {
        val kota = listKota[position]
        holder.bind(kota)
    }

    override fun getItemCount(): Int = listKota.size

    inner class KotaListViewHolder(private val binding: ItemListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(kota: KotaKabupatenItem?) {
            with(binding) {
                tvTitle.text = kota?.nama
                itemView.setOnClickListener {
                    onItemClickCallback?.onItemClicked(kota)
                }
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(kota: KotaKabupatenItem?)
    }
}