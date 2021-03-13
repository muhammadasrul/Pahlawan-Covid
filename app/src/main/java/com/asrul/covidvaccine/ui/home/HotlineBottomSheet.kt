package com.asrul.covidvaccine.ui.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.asrul.covidvaccine.databinding.HotlineModalLayoutBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class HotlineBottomSheet: BottomSheetDialogFragment() {

    companion object {
        const val hotline = "tel:119"
        const val wa = "https://api.whatsapp.com/send?phone=6281133399000&text=hi"
        const val bansos ="https://api.whatsapp.com/send?phone=628111022210&text=hi"
        const val kreatif = "https://api.whatsapp.com/send?phone=628118956767&text=hi"
    }

    private var _binding: HotlineModalLayoutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = HotlineModalLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.wa.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(wa))
            startActivity(intent)
        }

        binding.hotline.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse(hotline))
            startActivity(intent)
        }

        binding.bansos.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(bansos))
            startActivity(intent)
        }

        binding.kreatif.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(kreatif))
            startActivity(intent)
        }
    }
}