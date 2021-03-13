package com.asrul.covidvaccine.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.asrul.covidvaccine.R
import com.asrul.covidvaccine.databinding.ModalLayoutBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CustomBottomSheetDialog: BottomSheetDialogFragment() {

    companion object {
        const val EXTRA_ATM = "extra_atm"
        const val EXTRA_MOBILE = "extra_mobile"
        const val EXTRA_INTERNET = "extra_internet"
        const val EXTRA_INFO = "info"
    }

    private var _binding: ModalLayoutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ModalLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        when(tag) {
            EXTRA_ATM -> {
                binding.tvTitle.text = getString(R.string.atm_title)
                binding.tvPayment.text = getString(R.string.atm)
            }
            EXTRA_MOBILE -> {
                binding.tvTitle.text = getString(R.string.mobile_title)
                binding.tvPayment.text = getString(R.string.mobile)
            }
            EXTRA_INTERNET -> {
                binding.tvTitle.text = getString(R.string.internet_title)
                binding.tvPayment.text = getString(R.string.internet)
            }
            EXTRA_INFO -> {
                binding.tvTitle.text = "About Developer"
                binding.tvPayment.text = "1. Muhammad Asrul Aji Pangestu\n   asrulajipangestu@gmail.com\n \n2. Eko Prasetyo\n    masukpak33@gmail.com\n"
            }
        }
    }
}