package com.asrul.covidvaccine.ui.profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.asrul.covidvaccine.R
import com.asrul.covidvaccine.databinding.FragmentProfileBinding
import com.asrul.covidvaccine.ui.CustomBottomSheetDialog
import com.asrul.covidvaccine.ui.LoginActivity
import com.asrul.covidvaccine.utils.SessionManager
import com.bumptech.glide.Glide

class ProfileFragment : Fragment() {

    private var fragmentProfileBinding: FragmentProfileBinding? = null
    private val binding get() = fragmentProfileBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentProfileBinding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sessionManager = SessionManager(context)
        val bottomSheetDialog = CustomBottomSheetDialog()

        val name = sessionManager.getUserDetail()[SessionManager.NAME]
        val email = sessionManager.getUserDetail()[SessionManager.EMAIL]
        val photoPath = sessionManager.getUserDetail()[SessionManager.PHOTO_PATH]

        Log.e("photo", photoPath.toString())

        binding.txtName.text = name
        binding.txtEmail.text = email

        if (photoPath != null) {
            Glide.with(this)
                .load(photoPath)
                .placeholder(R.drawable.ic_baseline_refresh_24)
                .error(R.drawable.ic_baseline_broken_image_24)
                .circleCrop()
                .into(binding.imgUser)
        } else {
            Glide.with(this)
                .load(R.drawable.ic_baseline_account_circle_24)
                .circleCrop()
                .into(binding.imgUser)
        }

        binding.btnProfile.setOnClickListener {
            val intent = Intent(context, EditProfileActivity::class.java)
            startActivity(intent)
        }

        binding.btnInfo.setOnClickListener {
            bottomSheetDialog.show(childFragmentManager, CustomBottomSheetDialog.EXTRA_INFO)
        }

        binding.btnLogout.setOnClickListener {
            sessionManager.logoutSession()
            startActivity(Intent(context, LoginActivity::class.java))
            activity?.finish()
        }

        binding.btnPassword.setOnClickListener {
            val id = sessionManager.getUserDetail()[SessionManager.ID]
            val intent = Intent(context, PasswordActivity::class.java)
                .putExtra(PasswordActivity.ID, id)
            startActivity(intent)
        }

    }
}