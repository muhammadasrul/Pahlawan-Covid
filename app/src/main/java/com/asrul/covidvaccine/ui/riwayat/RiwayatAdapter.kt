package com.asrul.covidvaccine.ui.riwayat

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.asrul.covidvaccine.R
import com.asrul.covidvaccine.data.api.response.ServiceRegistItem
import com.asrul.covidvaccine.databinding.RiwayatItemBinding
import com.bumptech.glide.Glide

class RiwayatAdapter: RecyclerView.Adapter<RiwayatAdapter.RiwayatViewHolder>() {

    private var riwayatList= ArrayList<ServiceRegistItem?>()
    private var onItemClickCallback: OnItemClickCallback? = null
    private var payment = false
    private var confirm = false


    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setRiwayatList(riwayatList: List<ServiceRegistItem?>) {
        this.riwayatList.clear()
        this.riwayatList.addAll(riwayatList)
    }

    override fun onCreateViewHolder( parent: ViewGroup, viewType: Int): RiwayatAdapter.RiwayatViewHolder {
        val binding = RiwayatItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RiwayatViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RiwayatAdapter.RiwayatViewHolder, position: Int) {
        val riwayat = riwayatList[position]
        holder.bind(riwayat)
    }

    override fun getItemCount(): Int = riwayatList.size

    inner class RiwayatViewHolder(private val binding: RiwayatItemBinding): RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("UseCompatLoadingForDrawables")
        fun bind(service: ServiceRegistItem?) {
            with(binding) {
                tvName.text = service?.name
                tvAddress.text = service?.address
                tvService.text = service?.service
                tvPrice.text = service?.totalPayment

                if (service?.paymentStatus == "done") {
                    tvPayment.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_check_box_24, 0, 0, 0)
                    payment = true
                } else {
                    tvPayment.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_check_box_outline_blank_24, 0, 0, 0)
                }

                if (service?.confirmStatus == "confirmed") {
                    tvConfirm.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_check_box_24, 0, 0, 0)
                    confirm = true
                } else {
                    tvConfirm.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_check_box_outline_blank_24, 0, 0, 0)
                }

                Glide.with(itemView.context)
                    .load(service?.logoPath)
                    .placeholder(R.drawable.ic_baseline_refresh_24)
                    .error(R.drawable.ic_baseline_broken_image_24)
                    .into(imgRs)

                itemView.setOnClickListener {
                    onItemClickCallback?.onItemClicked(service, payment, confirm)
                }
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(service: ServiceRegistItem?, payment: Boolean, confirm: Boolean)
    }
}