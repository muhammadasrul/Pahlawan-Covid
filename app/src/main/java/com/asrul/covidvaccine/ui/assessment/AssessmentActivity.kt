package com.asrul.covidvaccine.ui.assessment

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.asrul.covidvaccine.ui.MainActivity
import com.asrul.covidvaccine.R
import com.asrul.covidvaccine.data.api.ApiConfig
import com.asrul.covidvaccine.data.api.response.AssessmentItem
import com.asrul.covidvaccine.data.api.response.HistoryResponse
import com.asrul.covidvaccine.databinding.ActivityAssessmentBinding
import com.asrul.covidvaccine.databinding.ResultDialogBinding
import com.asrul.covidvaccine.utils.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class AssessmentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAssessmentBinding

    private var updatedScore = 0
    private var lastScore: Int? = 0
    private var status = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAssessmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Self Assessment"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val sessionManager = SessionManager(this)
        val user_id: String = sessionManager.getUserDetail()[SessionManager.ID].toString()

        val viewModel = AssessmentViewModel()

        viewModel.getAssessment()
        viewModel.assessment.observe(this, { assessment ->

            val assessmentAdapter = AssessmentAdapter()
            assessmentAdapter.setAssessment(assessment)
            assessmentAdapter.notifyDataSetChanged()

            binding.rvAssessment.layoutManager = LinearLayoutManager(this)
            binding.rvAssessment.setHasFixedSize(true)
            binding.rvAssessment.adapter = assessmentAdapter

            assessmentAdapter.setOnItemSelectCallback(object : AssessmentAdapter.OnItemSelectCallback {
                override fun onItemSelected(assessment: AssessmentItem?, lastChecked: Int) {
                    Log.e("AssessmentActivity", "pos: ${lastChecked}, id: ${assessment?.checkedId} ,score: ${assessment?.score}")

                    if (assessment?.checkedId == R.id.rb_yes) {
                        val score: Int = assessment.score!!.toInt()

                        if (assessment.checkedId != -1) {
                            updatedScore = lastScore?.plus(score) ?: 0
                            lastScore = updatedScore
                            Log.e("AssessmentActivity", "isAnswered: ${assessment.checkedId} lastScore: $lastScore")
                        }
                    }
                }

            })
        })

        viewModel.isLoading.observe(this, {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
            binding.tvAssesment.visibility = if (it) View.GONE else View.VISIBLE
            binding.btnAssessment.visibility = if (it) View.GONE else View.VISIBLE
        })

        binding.btnAssessment.setOnClickListener {
            val sdf = SimpleDateFormat("dd/MM/yyy hh:mm:ss", Locale.getDefault())
            val date = sdf.format(Date())

            if (lastScore == 0) {
                status = "Resiko Kecil"
            } else if (lastScore!! in 1..4) {
                status = "Resiko Sedang"
            } else {
                status = "Resiko Besar"
            }

            ApiConfig.getApiUser().postHistory(date, user_id, status)
                    .enqueue(object : Callback<HistoryResponse> {
                        override fun onResponse(call: Call<HistoryResponse>, response: Response<HistoryResponse>) {
                            if (response.isSuccessful && response.body() != null) {
                                Log.e("History", "onResponse: ${response.body()}")
                            }
                        }

                        override fun onFailure(call: Call<HistoryResponse>, t: Throwable) {
                            t.printStackTrace()
                        }

                    })

            showResult(status)
        }
    }

    private fun showResult(status: String) {
        val dialogBinding = ResultDialogBinding.inflate(LayoutInflater.from(this))

        val alertBuilder = AlertDialog.Builder(this)
                .setView(dialogBinding.root)
                .setCancelable(false)

        dialogBinding.tvStatus.text = status

        when (status) {
            "Resiko Kecil" -> {
                dialogBinding.tvStatus.setTextColor(ContextCompat.getColor(this, R.color.greenEnd))
                dialogBinding.tvDescStatus.text = resources.getString(R.string.resiko_kecil)
            }
            "Resiko Sedang" -> {
                dialogBinding.tvStatus.setTextColor(ContextCompat.getColor(this, R.color.yellowEnd))
                dialogBinding.tvDescStatus.text = resources.getString(R.string.resiko_sedang)
            }
            "Resiko Besar" -> {
                dialogBinding.tvStatus.setTextColor(ContextCompat.getColor(this, R.color.redEnd))
                dialogBinding.tvDescStatus.text = resources.getString(R.string.resiko_besar)
            }
        }

        dialogBinding.btnSelesai.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        alertBuilder.show()
    }
}