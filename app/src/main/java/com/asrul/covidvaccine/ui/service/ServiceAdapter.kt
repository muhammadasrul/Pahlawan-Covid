package com.asrul.covidvaccine.ui.service

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.asrul.covidvaccine.R
import com.asrul.covidvaccine.data.api.response.ServiceItem
import com.asrul.covidvaccine.databinding.ServiceItemBinding
import com.bumptech.glide.Glide

class ServiceAdapter: RecyclerView.Adapter<ServiceAdapter.ServiceViewModel>() {

    private val listService = ArrayList<ServiceItem?>()
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setServiceList(service: List<ServiceItem?>) {
        this.listService.clear()
        this.listService.addAll(service)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceViewModel {
        val binding = ServiceItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ServiceViewModel(binding)
    }

    override fun onBindViewHolder(holder: ServiceViewModel, position: Int) {
        val service = listService[position]
        holder.bind(service)
    }

    override fun getItemCount(): Int = listService.size

    inner class ServiceViewModel(private val binding: ServiceItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(service: ServiceItem?) {
            with(binding) {
                tvName.text = service?.name
                tvAddress.text = service?.address
                tvService.text = service?.service
                tvPrice.text = service?.price

                Glide.with(itemView.context)
                        .load(service?.logoPath)
                        .placeholder(R.drawable.ic_baseline_refresh_24)
                        .error(R.drawable.ic_baseline_broken_image_24)
                        .into(imgRs)

                itemView.setOnClickListener {
                    onItemClickCallback?.onItemClicked(service)
                }
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(service: ServiceItem?)
    }
}