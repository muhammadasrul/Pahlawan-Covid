package com.asrul.covidvaccine.ui.home

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.asrul.covidvaccine.R
import com.asrul.covidvaccine.data.api.ApiConfig
import com.asrul.covidvaccine.data.api.response.StatusItem
import com.asrul.covidvaccine.data.api.response.StatusResponse
import com.asrul.covidvaccine.databinding.CarouselLayoutBinding
import com.asrul.covidvaccine.databinding.FragmentHomeBinding
import com.asrul.covidvaccine.ui.assessment.AssessmentActivity
import com.asrul.covidvaccine.ui.provinsi.ProvinsiActivity
import com.asrul.covidvaccine.utils.SessionManager
import com.synnapps.carouselview.ViewListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment(), View.OnClickListener {

    companion object {
        const val peta = "https://goo.gl/maps/y3WZ9SeLujiKaoFY7"
    }

    private var fragmentHomeBinding: FragmentHomeBinding? = null
    private val binding get() = fragmentHomeBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.carouselView.setViewListener(viewListener)
        binding.carouselView.pageCount = newNormalText.size

        val viewModel = HomeViewModel()

        viewModel.getCovidIndonesia()
        viewModel.confirmed.observe(viewLifecycleOwner, {
            binding.txtPositif.text = it.value.toString()
        })
        viewModel.recovered.observe(viewLifecycleOwner, {
            binding.txtSembuh.text = it.value.toString()
        })
        viewModel.deaths.observe(viewLifecycleOwner, {
            binding.txtMeninggal.text = it.value.toString()
        })

        viewModel.isLoading.observe(viewLifecycleOwner, {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE

            if (it) {
                binding.relativeSembuh.visibility = View.GONE
                binding.relativeMeninggal.visibility = View.GONE
                binding.relativePositif.visibility = View.GONE
            } else {
                binding.relativeSembuh.visibility = View.VISIBLE
                binding.relativeMeninggal.visibility = View.VISIBLE
                binding.relativePositif.visibility = View.VISIBLE
            }
        })

        binding.detailBtn.setOnClickListener(this)
        binding.btnAssessment.setOnClickListener(this)
        binding.btnBannerAssessment.setOnClickListener(this)
        binding.btnPeta.setOnClickListener(this)
        binding.btnKontak.setOnClickListener(this)

        val sessionManager = SessionManager(context)
        val userId = sessionManager.getUserDetail()[SessionManager.ID]
        lastStatus(userId)
    }

    private fun lastStatus(user_id: String?) {
        ApiConfig.getApiUser().getStatus(user_id)
                .enqueue(object: Callback<StatusResponse> {
                    override fun onResponse(call: Call<StatusResponse>, response: Response<StatusResponse>) {
                        if (response.body()?.data?.size != 0) {
                            val data: StatusItem? = response.body()?.data?.get(0)
                            binding.tvStatus.text = String.format(
                                resources.getString(R.string.status_terakhir),
                                data?.status
                            )
                            when (data?.status) {
                                "Resiko Besar" -> {
                                    binding.tvStatus.setTextColor(ContextCompat.getColor(activity!!, R.color.redEnd))
                                    binding.cardView2.setCardBackgroundColor(ContextCompat.getColor(activity!!, R.color.redStart))
                                }
                                "Resiko Sedang" -> {
                                    binding.tvStatus.setTextColor(ContextCompat.getColor(activity!!, R.color.yellowEnd))
                                    binding.cardView2.setCardBackgroundColor(ContextCompat.getColor(activity!!, R.color.yellowStart))
                                }
                                "Resiko Kecil" -> {
                                    binding.tvStatus.setTextColor(ContextCompat.getColor(activity!!, R.color.greenEnd))
                                    binding.cardView2.setCardBackgroundColor(ContextCompat.getColor(activity!!, R.color.greenStart))
                                }
                                else -> binding.tvStatus.text = getString(R.string.belum_asesmen)
                            }
                        }
                    }

                    override fun onFailure(call: Call<StatusResponse>, t: Throwable) {
                        binding.tvStatus.text = resources.getString(R.string.belum_asesmen)
                        t.printStackTrace()
                    }
                })
    }

    override fun onClick(view: View?) {
        when(view?.id) {
            R.id.btn_banner_assessment, R.id.btn_assessment-> {
                val intent = Intent(activity, AssessmentActivity::class.java)
                startActivity(intent)
            }
            R.id.detail_btn -> {
                val intent = Intent(activity, ProvinsiActivity::class.java)
                startActivity(intent)
            }
            R.id.btn_peta -> {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(peta))
                startActivity(intent)
            }
            R.id.btn_kontak -> {
                val hotlineBottomSheet = HotlineBottomSheet()
                hotlineBottomSheet.show(childFragmentManager, "hotline")
            }
        }
    }

    private val newNormalText = arrayOf(
        "Kebiasaan Baru #1",
        "Kebiasaan Baru #2",
        "Kebiasaan Baru #3",
        "Kebiasaan Baru #4"
    )

    private val newNormalDesc = arrayOf(
        "Selalu gunakan masker saat keluar rumah. Kenapa? Karena kita mungkin membawa virus tapi tidak memiliki gejala atau hanya gejala ringan, sehingga bisa menularkan ke orang lain.",
        "Hindari menyentuh mata, hidung, dan mulut. Saat menyentuh benda-benda yang sering disentuh orang lain seperti pegangan pintu, uang, meja makan, tangan Anda bisa terpapar virus.",
        "Selalu ambil jarak lebih dari 1 meter dari orang-orang saat berada di luar rumah",
        "Sering cuci tangan dengan sabun. Kita sudah sering mendengar hal ini. Tapi pastikan kita melakukannya dengan tepat, selama minimal 20 detik dan selalu lakukan saat tiba di rumah atau di tempat tujuan."
    )

    private val viewListener = ViewListener {position ->
        val carouselBinding:CarouselLayoutBinding = CarouselLayoutBinding.inflate(layoutInflater)
        val carouselView: View = carouselBinding.root

        carouselBinding.tvNewNormal.text = newNormalText[position]
        carouselBinding.tvNewNormalDesc.text = newNormalDesc[position]

        return@ViewListener carouselView
    }
}