package com.asrul.covidvaccine.ui.service.payment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.asrul.covidvaccine.R
import com.asrul.covidvaccine.data.api.response.PaymentItem
import com.asrul.covidvaccine.databinding.PaymentListBinding
import com.bumptech.glide.Glide

class PaymentAdapter: RecyclerView.Adapter<PaymentAdapter.PaymentViewHolder>() {

    var rowIndex = -1
    private val paymentList = ArrayList<PaymentItem?>()
    
    fun setPayment(paymentItem: List<PaymentItem?>) {
        this.paymentList.clear()
        this.paymentList.addAll(paymentItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentViewHolder {
        val binding = PaymentListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PaymentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PaymentViewHolder, position: Int) {
        val payment = paymentList[position]
        holder.bind(payment)
    }

    override fun getItemCount(): Int = paymentList.size

    inner class PaymentViewHolder(private val binding: PaymentListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(paymentItem: PaymentItem?) {
            with(binding) {
                tvPayment.text = paymentItem?.name
                Glide.with(binding.root)
                        .load(paymentItem?.paymentLogo)
                        .placeholder(R.drawable.ic_baseline_refresh_24)
                        .error(R.drawable.ic_baseline_broken_image_24)
                        .into(binding.imgPayment)

                if (rowIndex == -1) {
                    rbPayment.isChecked = false
                } else {
                    rbPayment.isChecked = rowIndex == adapterPosition
                }

                itemView.setOnClickListener{
                    rbPayment.isChecked = true
                    if (rowIndex != adapterPosition) {
                        notifyItemChanged(rowIndex)
                        rowIndex = adapterPosition
                    }
                }
            }
        }
    }

    fun getSelected(): PaymentItem? {
        if (rowIndex != -1) {
            return paymentList[rowIndex]
        }
        return null
    }
}